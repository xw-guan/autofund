package com.xwguan.autofund.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xwguan.autofund.constant.StockConstant;
import com.xwguan.autofund.dao.stock.MADao;
import com.xwguan.autofund.dao.stock.StockDao;
import com.xwguan.autofund.dao.stock.StockDataDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.dto.stock.StockUpdateState;
import com.xwguan.autofund.entity.stock.MA;
import com.xwguan.autofund.entity.stock.RealTimeMA;
import com.xwguan.autofund.entity.stock.RealTimeStockData;
import com.xwguan.autofund.entity.stock.Stock;
import com.xwguan.autofund.entity.stock.StockData;
import com.xwguan.autofund.enums.HistoryScopeEnum;
import com.xwguan.autofund.enums.MAEnum;
import com.xwguan.autofund.enums.RoundScaleEnum;
import com.xwguan.autofund.enums.UpdateStateEnum;
import com.xwguan.autofund.exception.FailInitializationException;
import com.xwguan.autofund.exception.io.EmptyInputException;
import com.xwguan.autofund.manager.api.StockDataManager;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.service.api.StockService;
import com.xwguan.autofund.service.mapper.stock.RealTimeMAMapper;
import com.xwguan.autofund.service.util.Predicates;
import com.xwguan.autofund.util.MathUtils;
import com.xwguan.autofund.util.StatisticsUtils;

/**
 * 更新股票数据服务
 * 
 * @author XWGuan
 * @version 1.0.1 2017-10-30
 */
@Service
public class StockServiceImpl implements StockService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile boolean updating;

    @Autowired
    private StockDataManager stockDataManager;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private StockDataDao stockDataDao;

    @Autowired
    private MADao maDao;

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private RealTimeMAMapper realTimeMAMapper;

    @Override
    public Stock getStock(Integer id) {
        return getStock(id, HistoryScopeEnum.NONE, null);
    }

    @Override
    public Stock getStock(Integer id, HistoryScopeEnum historyScope, Page page) {
        if (id == null || id <= 0) {
            return null;
        }
        return stockDao.getById(id, historyScope, page);
    }

    @Override
    public Stock getStock(String symbol, HistoryScopeEnum historyScope, Page page) {
        if (StringUtils.isEmpty(symbol)) {
            return null;
        }
        return stockDao.getBySymbol(symbol, historyScope, page);
    }

    @Override
    public List<Stock> listAllStock(HistoryScopeEnum historyScope, Page page) {
        switch (historyScope) {
        case FULL:
            return stockDao.listAllFull(page);
        case LATEST:
            return stockDao.listAll(true, page);
        default:
            return stockDao.listAll(false, page);
        }
    }

    @Override
    public List<Stock> listLatest(Page page) {
        return stockDao.listAll(true, page);
    }

    @Override
    public List<Stock> listStockBySymbolOrName(String symbolOrName, Boolean includeLatest, Page page) {
        if (StringUtils.isEmpty(symbolOrName)) {
            return new ArrayList<>();
        }
        return stockDao.listBySymbolOrName(symbolOrName, includeLatest, page);
    }

    @Override
    public int countUpdateRequired() throws IOException {
        return (int) listStocksAndInitIfNeed().parallelStream().filter(s -> {
            LocalDate latestSDDate = stockDataDao.getLatestDate(s.getId());
            LocalDate latestMADate = maDao.getLatestDate(s.getId());
            return !dateTimeService.isDataNewest(latestSDDate) || !dateTimeService.isDataNewest(latestMADate);
        }).count();
    }

    @Override
    public int countStock() {
        return (int) stockDao.count();
    }

    @Override
    // FIXME 在管理界面中多次点击更新会多次执行本方法, 现在临时用synchronized锁方法, 待修改成不锁方法而是根据updating的值判断是否直接返回, 并在管理界面上显示
    public synchronized List<StockUpdateState> updateStockData(Boolean useMultiThread) throws IOException {
        List<Stock> stockList = listStocksAndInitIfNeed();
        if (CollectionUtils.isEmpty(stockList)) {
            logger.error("待更新的股票列表不能为空", new EmptyInputException());
            return Arrays.asList(new StockUpdateState(-1, "000000.00", UpdateStateEnum.EMPTY_UPDATED_LIST, 0L,
                UpdateStateEnum.EMPTY_UPDATED_LIST, 0L));
        }
        LocalDate nowDate = LocalDate.now();
        logger.info(
            "Updating stockDatas, please wait. It may take a while. If the process is unexpected terminated, try to update again");
        boolean useMultiThreadUpdate = useMultiThread != null && useMultiThread;
        Stream<Stock> stocks = useMultiThreadUpdate ? stockList.parallelStream() : stockList.stream();
        return stocks.peek(stock -> logger.info(stock.getSymbol()))
            .map(stock -> {
                String symbol = stock.getSymbol();
                Integer stockId = stock.getId();
                StockUpdateState sdState = updateStockData(stock, nowDate);
                StockUpdateState maState = updateMA(stock, nowDate);
                return new StockUpdateState(stockId, symbol, sdState.getStockDataState(),
                    sdState.getStockDataSuccess(), maState.getMaState(), maState.getMaSuccess());
            }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public synchronized int updateStock() throws IOException {
        logger.info("Updating stocks, please wait...");
        List<Stock> stocks = stockDataManager.listAShareAll();
        int cntUpdateOrInsert = stockDao.updateOrInsertBatch(stocks);
        logger.debug("update {} stocks", cntUpdateOrInsert);
        return cntUpdateOrInsert;
    }

    @Override
    @Transactional
    public int deleteStock(List<Integer> idList) throws IOException {
        if (CollectionUtils.isEmpty(idList)) {
            throw new EmptyInputException("待删除的股票id列表为空");
        }
        List<Integer> filteredIds = idList.stream()
            .filter(Objects::nonNull)
            .filter(Predicates::greaterThanZero)
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(idList)) {
            return 0;
        }
        stockDataDao.deleteStockDataBatchByStockId(filteredIds);
        maDao.deleteMABatchByStockId(filteredIds);
        List<Long> filteredIdsLong = filteredIds.stream().map(Integer::longValue).collect(Collectors.toList());
        return stockDao.deleteByIdListBatch(filteredIdsLong);
    }

    @Override
    public RealTimeStockData getRealTimeStockData(Integer stockId) throws IOException {
        String symbol = stockDao.getSymbolById(stockId);
        if (symbol == null) {
            return null;
        }
        RealTimeStockData realTimeStockData = stockDataManager.getRealTimeStockData(symbol);
        realTimeStockData.setStockId(stockId);
        return realTimeStockData;
    }

    @Override
    public RealTimeMA getRealTimeMA(Integer stockId) throws IOException {
        RealTimeStockData realTimeStockData = getRealTimeStockData(stockId);
        if (realTimeStockData == null) {
            return null;
        }
        RealTimeMA realTimeMA = realTimeMAMapper.toRealTimeMA(realTimeStockData);
        // 实时值的日期在数据库最新值之后, 获取到的实时值才是真正的实时值, 否则只是最新时刻的值
        List<Double> priceList = stockDataDao.listCloseTradeDaysBefore(stockId, LocalDate.now(),
            MAEnum.MA250.getDayNumber(), null); // 时间逆序
        boolean useRealTimePrice = realTimeStockData.getDate().isAfter(stockDataDao.getLatestDate(stockId));
        if (useRealTimePrice) {
            Deque<Double> priceQueue = new ArrayDeque<>(priceList);
            priceQueue.pollLast(); // 移除最早一个价格(时间逆序, 队尾)
            priceQueue.offerFirst(realTimeStockData.getPrice()); // 实时值插入队首
            priceList = priceQueue.stream().collect(Collectors.toList());
        }
        // 计算NDayMAEnum所有取值对应的MA数据
        for (MAEnum nDayMA : MAEnum.values()) {
            calcMAValue(priceList, nDayMA).ifPresent(value -> realTimeMA.setNDayMA(nDayMA, value));
        }
        return realTimeMA;
    }

    // TODO 减少反复连接数据库, 将所有股票的数据一次性插入? 但这样可能要有一个庞大的List<Stock>
    @Transactional
    private StockUpdateState updateStockData(Stock oldStock, LocalDate endDate) {
        StockUpdateState result = new StockUpdateState();
        if (oldStock == null) {
            return result.stockDataUpdateState(UpdateStateEnum.EMPTY_UPDATED_LIST, 0L);
        }
        Integer stockId = oldStock.getId();
        LocalDate latestDate = stockDataDao.getLatestDate(stockId);
        if (dateTimeService.isDataNewest(latestDate)) {
            return result.stockDataUpdateState(UpdateStateEnum.ALREADY_NEWEST, 0L);
        }
        String symbol = oldStock.getSymbol();
        LocalDate startDate = getUpdateStartDate(latestDate);
        // 通过StockDataManager对象从网络中获取更新的股票数据
        Stock newStock = null;
        try {
            newStock = stockDataManager.getStock(symbol, startDate, endDate);
        } catch (IOException e) {
            logger.warn("获取Stock数据失败", e);
            return result.stockDataUpdateState(UpdateStateEnum.EMPTY_DATA, 0L);
        }
        // 从网络获取的数据为空
        if (newStock == null || CollectionUtils.isEmpty(newStock.getStockDataList())) {
            return result.stockDataUpdateState(UpdateStateEnum.EMPTY_DATA, 0L);
        }
        List<StockData> newStockDataList = newStock.getStockDataList();
        // newStockDataList中与数据库相关的stockId属性没有值, 需要设置
        newStockDataList.parallelStream().forEach(sd -> sd.setStockId(stockId));
        // 插入数据库
        long success = stockDataDao.insertStockData(newStockDataList);
        if (success > 0) {
            return result.stockDataUpdateState(UpdateStateEnum.SUCCESS, success);
        }
        return result.stockDataUpdateState(UpdateStateEnum.FAIL_TO_UPDATE_DB, 0L);
    }

    // TODO 减少反复连接数据库, 将所有股票的数据一次性插入?
    @Transactional
    private StockUpdateState updateMA(Stock oldStock, LocalDate endDate) {
        StockUpdateState result = new StockUpdateState();
        if (oldStock == null) {
            return result.stockDataUpdateState(UpdateStateEnum.EMPTY_UPDATED_LIST, 0L);
        }
        Integer stockId = oldStock.getId();
        LocalDate latestDate = maDao.getLatestDate(stockId);
        if (dateTimeService.isDataNewest(latestDate)) {
            return result.maUpdateState(UpdateStateEnum.ALREADY_NEWEST, 0L);
        }
        LocalDate startDate = getUpdateStartDate(latestDate);
        /*
         * dateList为需要计算更新MA表的日期列表
         * 这里应当用stock_data表中的数据, 因为不知道当日是否已经有数据, 直接按如下方式使用dateService获取交易日日期列表是不合适的
         * List<LocalDate> dateList = dateTimeService.listTradeDay(startDate, endDate);
         */
        List<LocalDate> dateList = stockDataDao.listDateByDateRange(stockId, startDate, endDate, null);
        List<MA> maList = dateList.parallelStream()
            .map(date -> calcMA(stockId, date).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(maList)) {
            return result.maUpdateState(UpdateStateEnum.EMPTY_DATA, 0L);
        }
        long success = maDao.insertMA(maList);
        if (success > 0) {
            return result.maUpdateState(UpdateStateEnum.SUCCESS, success);
        }
        return result.maUpdateState(UpdateStateEnum.FAIL_TO_UPDATE_DB, 0L);
    }

    /**
     * 计算某个日期的MA. stockId对应的股票不存在时, 返回Optional.Empty. 若数据不足以计算某个日数的ma, 则对应属性为null.
     * 若tradeDate在最新数据的日期之后, 则按最新数据计算MA.
     * // TODO 用前一日MA计算
     * 
     * @param stockId 股票id
     * @param tradeDate 要计算MA的交易日日期
     * @return 包含MA5至MA250值的MA对象
     */
    public Optional<MA> calcMA(Integer stockId, LocalDate tradeDate) {
        LocalDate latestDate = stockDataDao.getLatestDate(stockId);
        if (latestDate == null) {
            return Optional.empty();
        }
        // LocalDate dateOfMA = tradeDate.isAfter(latestDate) ? latestDate : tradeDate; // 这行什么意思?
        MA ma = new MA(stockId, tradeDate);
        // 计算NDayMAEnum所有取值对应的MA数据
        List<Double> priceList = stockDataDao.listCloseTradeDaysBefore(stockId, tradeDate,
            MAEnum.MA250.getDayNumber(), null); // 时间逆序
        for (MAEnum nDayMA : MAEnum.values()) {
            calcMAValue(priceList, nDayMA).ifPresent(value -> ma.setNDayMA(nDayMA, value));
        }
        return Optional.ofNullable(ma);
    }

    /**
     * 计算ma值, 数据量不足以计算ma时返回Optional.EMPTY.
     * 若不在交易时间, 则按最新数据计算ma.
     * 获取的实时值日期若不在数据库中最新股票数据时间之后, 表示调用此方法的时候不在交易时间, 实时值是没有意义的
     * 
     * @param priceList 250日的价格, 必须日期逆序
     * @param nDayMA ma日数
     * @param realTimePrice 实时值, 不为null时用实时值替换最早一个价格
     * @return
     */
    private Optional<Double> calcMAValue(List<Double> priceList, MAEnum nDayMA) {
        // 数据不足均线日数时, 不进行计算
        int tradeDays = nDayMA.getDayNumber();
        if (priceList.size() < tradeDays) {
            return Optional.empty();
        }
        if (priceList.size() > tradeDays) {
            priceList = priceList.stream().limit(tradeDays).collect(Collectors.toList());
        }
        double maValue = StatisticsUtils.average(priceList);
        maValue = MathUtils.round(maValue, RoundScaleEnum.STOCK_ROUND_SCALE);
        return Optional.of(maValue);
    }

    /**
     * 列出stock表中的所有股票基本信息(id, symbol, name)
     * <p>若stock表中没有数据, 则调用updateStock()进行初始化后重新获取stock表内容
     * 
     * @return stock表中的所有股票基本信息
     * @throws IOException
     * @exception FailInitializationException 初始化数据表失败, 可能是数据库相关原因
     */
    @Transactional
    private List<Stock> listStocksAndInitIfNeed() throws IOException {
        List<Stock> stockList = stockDao.listAll(false, null);
        if (CollectionUtils.isEmpty(stockList)) {
            // oldStockList没有数据时, 即数据库中的stock表尚未初始化, 调用updateStock()进行初始化
            updateStock();
            stockList = stockDao.listAll(false, null);
        }
        return stockList;
    }

    /**
     * 根据传入的数据库中最新更新的时间获取更新的开始时间<br>
     * 获取不到最近数据更新时间时从从DEFAULT_START_DATE开始获取, 否则从最近数据更新时间后一天开始获取
     * 
     * @param stockId
     * @param latestDate
     * @return 数据更新开始时间
     */
    private LocalDate getUpdateStartDate(LocalDate latestDate) {
        return latestDate != null ? latestDate.plusDays(1) : StockConstant.DEFAULT_DATA_START_DATE;
    }

    @Override
    public boolean isUpdating() {
        return updating;
    }

}
