package com.xwguan.autofund.dao.stock;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.stock.MA;
import com.xwguan.autofund.enums.MAEnum;

/**
 * 股票数据dao, 提供对MA表的CRUD操作
 * 
 * @author XWGuan
 * @version 1.0.1
 * @date 2017-10-21
 */
public interface MADao {
    /**
     * 查询所有股票MA数据, 根据股票代码和日期排序
     * 
     * @return 根据股票代码和日期排序的所有股票MA数据
     */
    List<MA> listAllMA();

    /**
     * 查询某只股票的所有数据, 根据日期排序
     * 
     * @param stockId 股票id
     * @param page 分页对象, 值为null时不进行分页
     * @return 根据日期排序的某只股票的 所有数据
     */
    List<MA> listMAByStockId(@Param("stockId") Integer stockId, @Param("page") Page page);

    /**
     * 查询某只股票的在某一时间范围的所有MA数据, 根据日期排序
     * 
     * @param stockId 股票id
     * @param start 开始时间
     * @param end 结束时间
     * @param page 分页对象, 值为null时不进行分页
     * @return 某只股票的在某一时间范围的所有数据
     */
    List<MA> listMAByDateRange(@Param("stockId") Integer stockId, @Param("start") LocalDate start,
        @Param("end") LocalDate end, @Param("page") Page page);

    /**
     * 查询某只股票的在某一时间范围的某日数MA数据, 根据日期排序
     * 
     * @param stockId 股票id
     * @param start 开始时间
     * @param end 结束时间
     * @param nDayMA n日均线
     * @param page 分页对象, 值为null时不进行分页
     * @return 某只股票的在某一时间范围的所有数据
     */
    List<MA> listNDayMAByDateRange(@Param("stockId") Integer stockId, @Param("start") LocalDate start,
        @Param("end") LocalDate end, @Param("nDayMA") MAEnum nDayMA, @Param("page") Page page);

    /**
     * 查询某只股票某日的ma数据
     * 
     * @param stockId 股票id
     * @param date 日期
     * @param nDayMA n日均线, 值为null时获取所有日数均线
     * @return 某只股票最新日期的ma
     */
    MA getMAByStockIdAndDate(@Param("stockId") Integer stockId, @Param("date") LocalDate date,
        @Param("nDayMA") MAEnum nDayMA);

    /**
     * 查询某只股票最新的ma数据
     * 
     * @param stockId 股票id
     * @param nDayMA n日均线, 值为null时获取所有日数均线
     * @return 某只股票最新日期的ma
     */
    MA getLatestMA(@Param("stockId") Integer stockId, @Param("nDayMA") MAEnum nDayMA);

    /**
     * 根据stock_id查询数据库中最新的股票数据日期
     * 
     * @param stockId 股票id
     * @return 最新的股票数据日期
     */
    LocalDate getLatestDate(@Param("stockId") Integer stockId);

    /**
     * 将传入的maList中的所有对象插入到表中
     * 
     * @param maList 将要插入的MA对象列表
     * @return 成功插入数量
     */
    long insertMA(List<MA> maList);

    /**
     * 更新与传入MA对象对应的股票数据
     * 
     * @param ma 将要更新的MA对象
     * @return 成功更新数量
     */
    long updateMA(MA ma);

    /**
     * 根据股票id和日期删除单条股票MA数据
     * 
     * @param stockDataId
     * @return 成功删除数量
     */
    long deleteMAByStockIdAndDate(@Param("stockId") Integer stockId, @Param("date") LocalDate date);

    /**
     * 根据股票id删除所有对应股票MA数据
     * 
     * @param stockId 股票id
     * @return 成功删除数量
     */
    long deleteMAByStockId(@Param("stockId") Integer stockId);

    /**
     * 根据股票idList批量删除所有对应的股票MA数据
     * 
     * @param idList 将要删除的股票数据的stockId列表
     * @return 成功删除数量
     */
    long deleteMABatchByStockId(List<Integer> stockIdList);

    /**
     * 清空ma表, <b>谨慎使用</b>
     * 
     * @return 成功删除数量
     */
    long deleteAll();
}
