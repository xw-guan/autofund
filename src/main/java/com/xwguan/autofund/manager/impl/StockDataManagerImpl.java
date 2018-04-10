package com.xwguan.autofund.manager.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBeanBuilder;
import com.xwguan.autofund.entity.stock.RealTimeStockData;
import com.xwguan.autofund.entity.stock.Stock;
import com.xwguan.autofund.entity.stock.StockData;
import com.xwguan.autofund.enums.StockExchangeEnum;
import com.xwguan.autofund.enums.SymbolFormatEnum;
import com.xwguan.autofund.exception.io.EmptyInputException;
import com.xwguan.autofund.exception.io.InvalidDateException;
import com.xwguan.autofund.exception.io.InvalidParamException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;
import com.xwguan.autofund.manager.api.StockDataManager;
import com.xwguan.autofund.manager.converter.ConverterStringToDoubleOrNull;
import com.xwguan.autofund.service.mapper.stock.NeteaseStockMapper;
import com.xwguan.autofund.util.CodeUtils;

/**
 * 股票数据获取, 数据源是网易财经和东方财富网
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-19
 */
@Component
public class StockDataManagerImpl implements StockDataManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String BASE_HISTORY_URL = "http://quotes.money.163.com/service/chddata.html";

    private static final String HISTORY_FIELDS = "TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";

    private static final String BASE_REAL_TIME_URL = "http://img1.money.126.net/data/hs/time/today/";

    private static final String SUFFIX_REAL_TIME_URL = ".json";

    private static final String STOCK_URL_1 = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=";

    private static final String STOCK_URL_2 = "&sty=";

    private static final String STOCK_URL_3 = "&sortType=(Code)&sortRule=1&page=1&pageSize=";

    private static final String STOCK_URL_4 = "&js={%22rank%22:[(x)],%22pages%22:(pc),%22total%22:(tot)}&token=7bc05d0d4c3c22ef9fca8c2a912d779c";

    private static final DateTimeFormatter HISTORY_DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

    private static final DateTimeFormatter REAL_TIME_DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    private static final String CSV_CHARSET = "GB2312";

    /*
     * cmd取值: C.1上证系列指数, C.2上证A股, C.5深证系列指数, C._SZAME深证A股, C._HKS港股, R.HKI|HKIN|HS港股指数, C._UNS美股,
     * C._UI_MAP_USOA美股指数, C.__285002封闭基金
     * sty取值: FCOIATA A股, FC20RF港股, FC2DPFD美股, FCRH美股指数
     */
    private static final String CMD_INDEX_SH = "C.1";

    private static final String CMD_STOCK_SH = "C.2";

    private static final String CMD_INDEX_SZ = "C.5";

    private static final String CMD_STOCK_SZ = "C._SZAME";

    private static final String STY_A_SHARE = "FCOIATA";

    @Autowired
    private NeteaseStockMapper mapper;

    @Autowired
    @Qualifier("jsonMapper")
    private ObjectMapper jsonMapper;

    @Override
    public List<Stock> listAShare() throws IOException {
        List<Stock> stocks = new ArrayList<>();
        List<Stock> stocksSH = listStock(CMD_STOCK_SH, STY_A_SHARE, StockExchangeEnum.SH, SymbolFormatEnum.AUTOFUND,
            false);
        List<Stock> stocksSZ = listStock(CMD_STOCK_SZ, STY_A_SHARE, StockExchangeEnum.SZ, SymbolFormatEnum.AUTOFUND,
            false);
        stocks.addAll(stocksSH);
        stocks.addAll(stocksSZ);
        return stocks;
    }

    @Override
    public List<Stock> listAShareIndex() throws IOException {
        List<Stock> stocks = new ArrayList<>();
        List<Stock> stocksSH = listStock(CMD_INDEX_SH, STY_A_SHARE, StockExchangeEnum.SH, SymbolFormatEnum.AUTOFUND,
            true);
        List<Stock> stocksSZ = listStock(CMD_INDEX_SZ, STY_A_SHARE, StockExchangeEnum.SZ, SymbolFormatEnum.AUTOFUND,
            true);
        stocks.addAll(stocksSH);
        stocks.addAll(stocksSZ);
        return stocks;
    }

    @Override
    public List<Stock> listAShareAll() throws IOException {
        List<Stock> stocks = listAShare();
        stocks.addAll(listAShareIndex());
        return stocks;
    }

    /**
     * <p>API示例:
     * http://quotes.money.163.com/service/chddata.html?code=0000001&start=20171010&end=20171012
     * &fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;VOTURNOVER;VATURNOVER
     * <p>其中, <ul>
     * <li>code为7位股票代码, 在正常6位股票代码前增加一个数字表示证券交易所, 0为上交所, 1为深交所, 如上证指数表示为0000001;</li>
     * <li>start和end为开始和结束日期; </li>
     * <li>fields为需要的字段, 与{@link StockData}对应. </li>
     * </ul>
     * <p>该API返回数据格式为CSV, 字符集为GB2312.
     * <p><b>部分没有值的数据在CSV文件中为None, 如第一天的数据pclose等值为None,
     * 部分日期voturnover和vaturnover没有值,
     * 此时需要用{@link ConverterStringToDoubleOrNull}进行转化</b>
     */
    @Override
    public Stock getStock(String symbol, LocalDate startDate, LocalDate endDate) throws IOException {
        logger.info(symbol);
        URL url = generateHistoryUrl(symbol, startDate, endDate);
        InputStreamReader reader = new InputStreamReader(url.openStream(), CSV_CHARSET);
        return parseHistoryCsvFromReader(reader, symbol);
    }

    @Override
    public RealTimeStockData getRealTimeStockData(String symbol) throws IOException {
        URL url = generateRealTimeUrl(symbol);
        InputStreamReader reader = new InputStreamReader(url.openStream(), CSV_CHARSET);
        return parseRealTimeJsonFromReader(reader, symbol);
    }

    /**
     * 创建从网易获得股票历史数据的URL对象
     * 
     * @param symbol 股票代码, 格式形如000001.SH
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 从网易获得股票数据的URL对象, 包含开始日期和结束日期
     * @throws MalformedURLException URL字符串解析错误
     * @throws InvalidParamException 股票代码或日期无效
     */
    private URL generateHistoryUrl(String symbol, LocalDate startDate, LocalDate endDate)
        throws MalformedURLException, InvalidParamException {
        if (StringUtils.isEmpty(symbol)) {
            throw new InvalidTickerSymbolException("The symbol connot be empty");
        }
        if (startDate == null || endDate == null) {
            throw new InvalidDateException("The dates connot be empty");
        }
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateException("The start date is after the end date");
        }
        // symbol转变为7位数字格式
        String neteaseFormatSymbol = convertSymbolToNeteaseFormat(symbol);
        // 日期转变为yyyyMMdd格式
        String start = startDate.format(HISTORY_DATE_FORMATTER);
        String end = endDate.format(HISTORY_DATE_FORMATTER);
        // 创建URL对象
        StringBuilder sb = new StringBuilder(BASE_HISTORY_URL).append("?code=").append(neteaseFormatSymbol)
            .append("&start=").append(start).append("&end=").append(end).append("&fields=").append(HISTORY_FIELDS);
        logger.debug(sb.toString());
        return new URL(sb.toString());
    }

    // TODO 直接返回stockData列表对象, 用传入的stockId赋值
    /**
     * 使用opencsv将Reader获取的CSV文件解析为Stock对象
     * <p>注意此时Stock对象中的id属性和index属性值为null, StockData中的id和stock_id属性值为null
     * <p>参看<a href="http://opencsv.sourceforge.net/"><i>opencsv文档</i></a>
     * 
     * @param reader 待解析的reader对象
     * @param symbol 股票代码
     * @return 对应CSV字段的Stock对象, 当CSV文件没有值时, 返回的Stock对象不为null, 但其中各属性没有值
     * @throws IOException 解析得到的csvDataList为空
     */
    private Stock parseHistoryCsvFromReader(Reader reader, String symbol) throws IOException {
        List<NeteaseCsvStockData> csvDataList = new CsvToBeanBuilder<NeteaseCsvStockData>(reader)
            .withType(NeteaseCsvStockData.class).build().parse();
        logger.debug("Parsed csv: {}", csvDataList.toString());
        if (CollectionUtils.isEmpty(csvDataList)) {
            throw new EmptyInputException("Empty CSV Data");
        }
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setName(csvDataList.get(0).getName());
        List<StockData> stockDataList = new ArrayList<>();
        stock.setStockDataList(stockDataList);
        for (NeteaseCsvStockData data : csvDataList) {
            stockDataList.add(mapper.toStockData(data));
        }
        return stock;
    }

    /**
     * 创建从网易获得股票实时数据的URL对象
     * 
     * @param symbol 股票代码, 格式形如000001.SH
     * @return
     * @throws MalformedURLException URL字符串解析错误
     * @throws InvalidParamException 股票代码无效
     */
    private URL generateRealTimeUrl(String symbol) throws MalformedURLException, InvalidParamException {
        if (StringUtils.isEmpty(symbol)) {
            throw new InvalidTickerSymbolException("The symbol connot be empty");
        }
        String neteaseFormatSymbol = convertSymbolToNeteaseFormat(symbol);
        StringBuilder sb = new StringBuilder(BASE_REAL_TIME_URL).append(neteaseFormatSymbol)
            .append(SUFFIX_REAL_TIME_URL);
        logger.debug(sb.toString());
        return new URL(sb.toString());
    }

    /**
     * 
     * @param reader 待解析的reader对象
     * @param symbol 股票代码
     * @return
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws IOException
     */
    private RealTimeStockData parseRealTimeJsonFromReader(Reader reader, String symbol)
        throws JsonParseException, JsonMappingException, IOException {
        Map<?, ?> resultMap = new ObjectMapper().readValue(reader, Map.class);
        List<?> data = (List<?>) resultMap.get("data");
        List<?> newestData = (List<?>) data.get(data.size() - 1);
        LocalTime time = LocalTime.parse((String) newestData.get(0), REAL_TIME_DATA_TIME_FORMATTER);
        Double price = (Double) newestData.get(1);
        LocalDate date = LocalDate.parse((String) resultMap.get("date"), HISTORY_DATE_FORMATTER);
        return new RealTimeStockData(symbol, date, time, price);
    }

    private List<Stock> listStock(String cmd, String sty, StockExchangeEnum stockExchange, SymbolFormatEnum format,
        boolean isIndex) throws IOException {
        int size = getStockListSize(cmd, sty);
        URL url = generateStockListUrl(cmd, sty, size);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        List<Stock> stocks = parseStockListFromReader(reader);
        formatSymbolAndSetIndex(stocks, stockExchange, format, isIndex);
        return stocks;
    }

    private URL generateStockListUrl(String cmd, String sty, int pageSize) throws MalformedURLException {
        StringBuilder sb = new StringBuilder().append(STOCK_URL_1).append(cmd).append(STOCK_URL_2).append(sty)
            .append(STOCK_URL_3).append(pageSize).append(STOCK_URL_4);
        logger.debug(sb.toString());
        return new URL(sb.toString());
    }

    private int getStockListSize(String cmd, String sty) throws IOException {
        URL url = generateStockListUrl(cmd, sty, 1);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        Map<?, ?> resultMap = jsonMapper.readValue(reader, Map.class);
        return (int) resultMap.get("total");
    }

    /**
     * symbol是直接从json解析的, 未加上交易所代码, index字段未赋值
     */
    private List<Stock> parseStockListFromReader(Reader reader)
        throws JsonParseException, JsonMappingException, IOException {
        List<Stock> stockList = new ArrayList<>();
        Map<?, ?> resultMap = jsonMapper.readValue(reader, Map.class);
        List<?> stockStrList = (List<?>) resultMap.get("rank");
        for (Object stockStrObj : stockStrList) {
            String stockStr = (String) stockStrObj;
            String[] stockFields = stockStr.split(",", 4);
            Stock stock = new Stock(stockFields[1], stockFields[2], null);
            stockList.add(stock);
        }
        return stockList;
    }

    private void formatSymbolAndSetIndex(List<Stock> stocks, StockExchangeEnum stockExchange, SymbolFormatEnum format,
        boolean isIndex) {
        stocks.parallelStream().forEach(stock -> {
            String symbol = stock.getSymbol();
            try {
                symbol = CodeUtils.convert(symbol, stockExchange, format);
            } catch (InvalidParamException e) {
                logger.error(e.getMessage());
            }
            stock.setSymbol(symbol);
            stock.setIndex(isIndex);
        });
    }

    private String convertSymbolToNeteaseFormat(String symbol) throws InvalidParamException {
        return CodeUtils.convert(symbol, SymbolFormatEnum.AUTOFUND, SymbolFormatEnum.NETEASE);
    }

}
