package com.xwguan.autofund.dao.stock;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.stock.StockData;

/**
 * 股票数据dao, 提供对stock_data表的CRUD操作
 * 
 * @author XWGuan
 * @version 1.0.1
 * @date 2017-10-22
 */
public interface StockDataDao {
    /**
     * 查询所有股票数据, 根据股票代码和日期(DESC)排序
     * 
     * @return 根据股票代码和日期排序的所有股票数据
     */
    List<StockData> listAllStockData();
    
    /**
     * 查询某只股票的所有数据, 根据日期(DESC)排序
     * 
     * @param stockId 股票id
     * @param page 分页对象, 值为null时不进行分页
     * @return 根据日期排序的某只股票的所有数据
     */
    List<StockData> listStockDataByStockId(@Param("stockId") Integer stockId, @Param("page") Page page);

    /**
     * 查询某只股票的在某一时间范围的所有数据, 根据日期(DESC)排序
     * 
     * @param stockId 股票id
     * @param start 开始时间
     * @param end 结束时间
     * @param page 分页对象, 值为null时不进行分页
     * @return 某只股票的在某一时间范围的所有数据
     */
    List<StockData> listStockDataByDateRange(@Param("stockId") Integer stockId, @Param("start") LocalDate start,
        @Param("end") LocalDate end, @Param("page") Page page);

    /**
     * 获得某只股票给定日期前N个交易日(包含给定日期)的所有股票数据, 根据日期(DESC)排序
     * 
     * @param stockId 股票id
     * @param date 给定日期
     * @param tradeDays 前N个交易日
     * @param page 分页对象, 值为null时不进行分页
     * @return 给定日期前N个交易日(包含给定日期)的所有股票数据
     */
    List<StockData> listStockDataTradeDaysBefore(@Param("stockId") Integer stockId, @Param("date") LocalDate date,
        @Param("tradeDays") Integer tradeDays, @Param("page") Page page);

    /**
     * 获得某只股票给定日期前N个交易日(包含给定日期)的所有收盘价, 根据日期(DESC)排序
     * 
     * @param stockId 股票id
     * @param date 给定日期
     * @param tradeDays 前N个交易日
     * @param page 分页对象, 值为null时不进行分页
     * @return 给定日期前N个交易日(包含给定日期)的所有收盘价
     */
    List<Double> listCloseTradeDaysBefore(@Param("stockId") Integer stockId, @Param("date") LocalDate date,
        @Param("tradeDays") Integer tradeDays, @Param("page") Page page);

    /**
     * 查询某只股票的在某一时间范围的交易日日期, 根据日期(DESC)排序
     * 
     * @param stockId 股票id
     * @param start 开始时间
     * @param end 结束时间
     * @param page 分页对象, 值为null时不进行分页
     * @return 某只股票的在某一时间范围的交易日日期
     */
    List<LocalDate> listDateByDateRange(@Param("stockId") Integer stockId, @Param("start") LocalDate start,
        @Param("end") LocalDate end, @Param("page") Page page);

    /**
     * 根据stock_id查询数据库中最新的股票数据日期
     * 
     * @param stockId 股票id
     * @return 最新的股票数据日期
     */
    LocalDate getLatestDate(@Param("stockId") Integer stockId);

    /**
     * 查询stockId和date对应的最新股票历史数据
     * 
     * @param stockId 股票id
     * @param date 日期
     * @return stockId和date对应的基金历史数据
     */
    StockData getStockDataByStockIdAndDate(@Param("stockId") Integer stockId, @Param("date") LocalDate date);
    
    /**
     * 查询某股票在某日期前第N个交易日的基金历史数据
     * 
     * @param stockId 基金id
     * @param date 日期
     * @param tradeDays 前N个交易日
     * @return stockId对应的股票在和date前第days个交易日的历史数据
     */
    StockData getStockDataTradeDaysBefore(@Param("stockId") Integer stockId, @Param("date") LocalDate date,
        @Param("tradeDays") Integer tradeDays);
    
    /**
     * 根据stockId对应的最新股票数据
     * 
     * @param stockId 股票id
     * @return stockId对应的最新股票数据
     */
    StockData getLatestStockData(@Param("stockId") Integer stockId);
    
    /**
     * 将传入的stockDataList中的所有对象插入到表中
     * 
     * @param stockDataList 将要插入的StockData对象列表
     * @return 成功插入数量
     */
    long insertStockData(List<StockData> stockDataList);

    /**
     * 更新与传入StockData对象对应的股票数据, 特定stockId和日期
     * @
     * @param stockData 将要更新的StockData对象
     * @return 成功更新数量
     */
    long updateStockData(StockData stockData); // TODO UNIMPLEMENTED 

    /**
     * 根据股票id和日期删除单条股票数据
     * 
     * @param stockId 股票id
     * @param date 日期
     * @return 成功删除数量
     */
    long deleteStockDataByStockIdAndDate(@Param("stockId") Integer stockId, @Param("date") LocalDate date);

    /**
     * 根据股票id删除所有对应股票数据
     * 
     * @param stockId 股票id
     * @return 成功删除数量
     */
    long deleteStockDataByStockId(@Param("stockId") Integer stockId);

    /**
     * 根据股票idList批量删除所有对应的股票数据
     * 
     * @param idList 将要删除的股票数据的stockId列表
     * @return 成功删除数量
     */
    long deleteStockDataBatchByStockId(List<Integer> stockIdList);
    
    /**
     * 清空stock_data表, <b>谨慎使用</b>
     * 
     * @return 成功删除数量
     */
    long deleteAll();
    
}
