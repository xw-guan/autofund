package com.xwguan.autofund.manager.api;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.xwguan.autofund.entity.stock.RealTimeStockData;
import com.xwguan.autofund.entity.stock.Stock;

/**
 * 从外部api获取股票数据
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-19
 */
public interface StockDataManager {

    /**
     * 获取A股股票列表(不包含指数)
     * 
     * @return
     * @throws IOException URL错误, 没有网络连接, 连接超时等; 股票代码无效; 解析得到的数据为空
     */
    List<Stock> listAShare() throws IOException;

    /**
     * 获取A股指数列表
     * 
     * @return
     * @throws IOException URL错误, 没有网络连接, 连接超时等; 股票代码无效; 解析得到的数据为空
     */
    List<Stock> listAShareIndex() throws IOException;

    /**
     * 获取A股股票和指数列表
     * 
     * @return
     * @throws IOException URL错误, 没有网络连接, 连接超时等; 股票代码无效; 解析得到的数据为空
     */
    List<Stock> listAShareAll() throws IOException;

    /**
     * 获得包含给定时间段股票数据的Stock对象
     * <p><b>注意:</b> 该方法返回的stockDataList中与数据库相关的stockId属性没有值, 需要另行赋值
     * 
     * @param symbol 股票代码, 格式如000001.SH
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 包含给定时间段股票数据的Stock对象
     * @throws IOException URL错误, 没有网络连接, 连接超时等; 股票代码无效; 解析得到的数据为空
     */
    Stock getStock(String symbol, LocalDate startDate, LocalDate endDate) throws IOException;

    /**
     * 获得实时股票数据
     * <p><b>注意:</b> 该方法返回的对象中与数据库相关的stockId属性没有值, 需要另行赋值
     * 
     * @param symbol 股票代码, 格式如000001.SH
     * @return 实时股票数据
     * @throws IOException URL错误, 没有网络连接, 连接超时等; 股票代码无效; 解析得到的数据为空
     */
    RealTimeStockData getRealTimeStockData(String symbol) throws IOException;
}
