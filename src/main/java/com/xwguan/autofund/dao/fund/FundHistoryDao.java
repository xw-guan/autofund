package com.xwguan.autofund.dao.fund;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.fund.FundHistory;

/**
 * 基金历史数据dao, 提供对fund_history表的CRUD操作
 * 
 * @author XWGuan
 * @version 1.0.1
 * @date 2017-10-22
 */
public interface FundHistoryDao {
    /**
     * 查询所有基金历史数据, 根据基金代码和日期排序
     * 
     * @return 根据基金代码和日期排序的所有基金历史数据
     */
    List<FundHistory> listAllFundHistory();

    /**
     * 查询某只基金的所有历史数据, 根据日期排序
     * 
     * @param fundId 基金id
     * @param page 分页对象, 值为null时不进行分页
     * @return 根据日期排序的某只基金的所有历史数据
     */
    List<FundHistory> listFundHistoryByFundId(@Param("fundId") Integer fundId, @Param("page") Page page);

    /**
     * 查询某只基金的在某一时间范围的所有历史数据, 根据日期排序
     * 
     * @param fundId 基金id
     * @param start 开始时间
     * @param end 结束时间
     * @param page 分页对象, 值为null时不进行分页
     * @return 某只基金的在某一时间范围的所有历史数据
     */
    List<FundHistory> listFundHistoryByDateRange(@Param("fundId") Integer fundId, @Param("start") LocalDate start,
        @Param("end") LocalDate end, @Param("page") Page page);

    /**
     * 获得某只基金给定日期前N个交易日(包含给定日期)的所有基金历史数据
     * 
     * @param fundId 基金id
     * @param date 给定日期
     * @param tradeDays 前N个交易日
     * @param page 分页对象, 值为null时不进行分页
     * @return 给定日期前N个交易日(包含给定日期)的所有基金历史数据
     */
    List<FundHistory> listFundHistoryDaysBefore(@Param("fundId") Integer fundId, @Param("date") LocalDate date,
        @Param("days") Integer tradeDays, @Param("page") Page page);

    /**
     * 根据fund_id查询历史库中最新的基金历史数据日期
     * 
     * @param fundId 基金id
     * @return 最新的基金历史数据日期
     */
    LocalDate getLatestDate(@Param("fundId") Integer fundId);

    /**
     * 查询fundId对应的最新基金历史数据
     * 
     * @param fundId 基金id
     * @return fundId对应的最新基金历史数据
     */
    FundHistory getLatestFundHistory(@Param("fundId") Integer fundId);

    /**
     * 查询fundId和date对应的最新基金历史数据
     * 
     * @param fundId 基金id
     * @param date 日期
     * @return fundId和date对应的基金历史数据
     */
    FundHistory getFundHistoryByFundIdAndDate(@Param("fundId") Integer fundId, @Param("date") LocalDate date);

    /**
     * 查询某基金在某日期前第N个交易日的基金历史数据
     * 
     * @param fundId 基金id
     * @param date 日期
     * @param tradeDays 前N个交易日
     * @return fundId对应的基金在和date前第days个交易日的基金历史数据
     */
    FundHistory getFundHistoryDaysBefore(@Param("fundId") Integer fundId, @Param("date") LocalDate date,
        @Param("days") Integer tradeDays);

    /**
     * 将传入的fundHistoryList中的所有对象插入到表中
     * 
     * @param fundHistoryList 将要插入的FundHistory对象列表
     * @return 成功插入数量
     */
    long insertFundHistory(List<FundHistory> fundHistoryList);

    /**
     * 更新与传入FundHistory对象对应的基金历史数据, 特定fundId和日期
     * 
     * @
     *       @param fundHistory 将要更新的FundHistory对象
     * @return 成功更新数量
     */
    long updateFundHistory(@Param("fundHistory") FundHistory fundHistory); // TODO UNIMPLEMENTED

    /**
     * 根据基金id和日期删除单条基金历史数据
     * 
     * @param fundId 基金id
     * @param date 日期
     * @return 成功删除数量
     */
    long deleteFundHistoryByFundIdAndDate(@Param("fundId") Integer fundId, @Param("date") LocalDate date);

    /**
     * 根据基金id删除所有对应基金历史数据
     * 
     * @param fundId 基金id
     * @return 成功删除数量
     */
    long deleteFundHistoryByFundId(@Param("fundId") Integer fundId);

    /**
     * 根据基金idList批量删除所有对应的基金历史数据
     * 
     * @param idList 将要删除的基金历史数据的fundId列表
     * @return 成功删除数量
     */
    long deleteFundHistoryBatchByFundId(List<Integer> fundIdList);

    /**
     * 清空fund_history表, <b>谨慎使用</b>
     * 
     * @return 成功删除数量
     */
    long deleteAll();

}
