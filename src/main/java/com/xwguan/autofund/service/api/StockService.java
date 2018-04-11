package com.xwguan.autofund.service.api;

import java.io.IOException;
import java.util.List;

import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.dto.stock.StockUpdateState;
import com.xwguan.autofund.entity.stock.RealTimeMA;
import com.xwguan.autofund.entity.stock.RealTimeStockData;
import com.xwguan.autofund.entity.stock.Stock;
import com.xwguan.autofund.enums.HistoryScopeEnum;

/**
 * 股票数据服务
 * 
 * @author XWGuan
 * @version 1.0.1
 * @date 2017-10-30
 */
public interface StockService {

    /**
     * 根据id获取不带历史信息的Stock
     * 
     * @param id
     * @return
     */
    Stock getStock(Integer id);

    /**
     * 根据id获取股票, id对应的股票不存在时, 返回null
     * 
     * @param id 股票id
     * @param historyScope 历史数据范围
     * @param page 分页对象, 分页对股票数据进行, 值为null时不进行分页
     * @return id对应的Stock对象
     */
    Stock getStock(Integer id, HistoryScopeEnum historyScope, Page page);

    /**
     * 根据股票代码获取股票, 对应的股票不存在时, 返回null
     * 
     * @param symbol 股票代码
     * @param historyScope 历史数据范围
     * @param page 分页对象, 分页对股票数据进行, 值为null时不进行分页
     * @return id对应的Stock对象
     */
    Stock getStock(String symbol, HistoryScopeEnum historyScope, Page page);

    /**
     * 列出所有股票
     * 
     * @param historyScope 历史数据范围
     * @return 所有股票的列表
     */
    List<Stock> listAllStock(HistoryScopeEnum historyScope, Page page);

    /**
     * 查询最近日期的股票数据
     * 
     * @param page 分页对象, 分页对Stock对象进行, 值为null时不进行分页
     * @return 最近日期的股票数据列表
     */
    List<Stock> listLatest(Page page);

    /**
     * 使用股票代码或名字模糊查询结果
     * 
     * @param symbolOrName 查询条件, 对应股票代码或名称
     * @param includeLatest 是否包含最近的详细数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Stock对象进行, 值为null时不进行分页
     * @return 符合条件的股票的列表
     */
    List<Stock> listStockBySymbolOrName(String symbolOrName, Boolean includeLatest, Page page);

    /**
     * 计算需要更新的股票数量
     * 
     * @return int 需要更新的股票数量
     * @throws IOException
     */
    int countUpdateRequired() throws IOException;

    /**
     * 获得数据库中的股票数量
     * 
     * @return 数据库中的股票数量
     */
    int countStock();

    /**
     * 获取数据库中已存在的stock, 从网络批量更新股票数据至最近一个交易日, 若无stock数据则调用updateStock()方法更新stock表.
     * 
     * @param useMultiThread 是否使用多线程更新, 开启多线程更新能提高效率, 但CPU和内存使用会急剧增加
     * @return 封装更新状态的StockUpdateState对象列表
     * @throws IOException
     */
    List<StockUpdateState> updateStockData(Boolean useMultiThread) throws IOException;

    /**
     * 是否正在更新
     * @return
     */
    boolean isUpdating();
    
    /**
     * 更新Stock表, 存在则更新, 不存在则插入
     * 
     * @return 插入或更新数量
     * @throws IOException 网络异常等
     */
    int updateStock() throws IOException;

    /**
     * 根据股票idList批量删除对应的股票
     * 
     * @param idList 将要删除的股票id列表
     * @return int 成功删除数量
     * @throws IOException 网络连接问题, 股票代码列表为空, 股票代码无效等
     */
    int deleteStock(List<Integer> idList) throws IOException;

    /**
     * 获取某支股票的实时数据, stockId对应的股票不存在时, 返回null
     * 
     * @param stockId 股票id
     * @return
     * @throws IOException
     */
    RealTimeStockData getRealTimeStockData(Integer stockId) throws IOException;

    /**
     * 依据实时值计算某个日期的MA. stockId对应的股票不存在时, 返回null. 若数据不足以计算某个日数的ma, 则对应属性为null.
     * 若不在交易时间, 则按最新数据计算ma
     * 
     * @param stockId 股票id
     * @return 包含MA5至MA250值的MA对象
     * @throws IOException
     */
    RealTimeMA getRealTimeMA(Integer stockId) throws IOException;

//    Optional<MA> calcMA(Integer stockId, LocalDate tradeDate);
    
    // /**
    // * 计算ma实时值.
    // *
    // * @param stockId 股票id
    // * @param tradeDate 交易日日期
    // * @param nDay ma日数
    // * @return
    // */
    // Double calcRealTimeMAValue(Integer stockId, LocalDate tradeDate, MAEnum nDay);

}
