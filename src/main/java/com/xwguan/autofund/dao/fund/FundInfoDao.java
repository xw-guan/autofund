package com.xwguan.autofund.dao.fund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.entity.fund.FundInfo;

/**
 * 基金信息dao, 提供对fund_info表的CRUD操作
 * 
 * @author XWGuan
 * @version 1.0.1
 * @date 2017-10-22
 */
public interface FundInfoDao {

    /**
     * 根据id查询基金信息数据
     * 
     * @param id 基金信息id
     * @return 对应id的基金信息
     */
    FundInfo getFundInfoById(@Param("id") Integer id);

    /**
     * 查询对应于fundId的基金信息
     * 
     * @param fundId 基金id
     * @return 对应于fundId的基金信息
     */
    FundInfo getFundInfoByFundId(@Param("fundId") Integer fundId);

    /**
     * 查询所有基金信息数据, 根据fundId排序
     * 
     * @return 所有基金信息数据
     */
    List<FundInfo> listAllFundInfo();

    /**
     * 将传入的fundInfoList中的所有对象插入到表中
     * 
     * @param fundInfoList 将要插入的FundInfo对象列表
     * @return 成功插入数量
     */
    long insertFundInfo(List<FundInfo> fundInfoList);

    /**
     * 更新与传入FundInfo对象对应的基金信息
     * 
     * @param fundInfo 将要更新的FundInfo对象
     * @return 成功更新数量
     */
    long updateFundInfo(@Param("fundInfo") FundInfo fundInfo);

    /**
     * 根据id删除对应的基金信息
     * 
     * @param id 基金信息id
     * @return 成功删除数量
     */
    long deleteFundInfoById(@Param("id") Integer id);

    /**
     * 根据基金id删除对应的基金信息
     * 
     * @param fundId 基金id
     * @return 成功删除数量
     */
    long deleteFundInfoByFundId(@Param("fundId") Integer fundId);

    /**
     * 根据fundIdList批量删除所有对应的基金信息
     * 
     * @param fundIdList 将要删除的基金信息的fundId列表
     * @return 成功删除数量
     */
    long deleteFundInfoBatchByFundId(List<Integer> fundIdList);

    /**
     * 清空fund_info表, <b>谨慎使用</b>
     * 
     * @return 成功删除数量
     */
    long deleteAll();

}
