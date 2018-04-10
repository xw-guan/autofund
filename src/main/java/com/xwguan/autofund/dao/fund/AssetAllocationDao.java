package com.xwguan.autofund.dao.fund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.FundAndStockCUDBatchDao;
import com.xwguan.autofund.entity.fund.AssetAllocation;

/**
 * 资产分配dao, 提供对asset_allocation表的CRUD操作
 * 
 * @author XWGuan
 * @version 1.0.1
 * @date 2017-10-22
 */
public interface AssetAllocationDao extends FundAndStockCUDBatchDao<AssetAllocation> {

    /**
     * 根据id查询资产分配数据, 根据持仓比例排序
     * 
     * @param id 资产分配id
     * @return 对应id的资产分配, 根据持仓比例排序
     */
    AssetAllocation getAssetAllocationById(@Param("id") Integer id);

    /**
     * 查询对应于fundId的资产分配, 根据持仓比例排序
     * 
     * @param fundId 基金id
     * @return 对应于fundId的资产分配, 根据持仓比例排序
     */
    AssetAllocation getAssetAllocationByFundId(@Param("fundId") Integer fundId);

    /**
     * 查询所有资产分配数据, 根据fundId和持仓比例排序排序
     * 
     * @return 所有资产分配数据, 根据fundId和持仓比例排序排序
     */
    List<AssetAllocation> listAllAssetAllocation();

    /**
     * 将传入的assetAllocationList中的所有对象插入到表中
     * 
     * @param assetAllocationList 将要插入的AssetAllocation对象列表
     * @return 成功插入数量
     */
    long insertAssetAllocation(List<AssetAllocation> assetAllocationList);

    /**
     * 更新与传入AssetAllocation对象对应的资产分配
     * 
     * @param assetAllocation 将要更新的AssetAllocation对象
     * @return 成功更新数量
     */
    long updateAssetAllocation(@Param("assetAllocation") AssetAllocation assetAllocation);

    /**
     * 根据id删除对应的资产分配
     * 
     * @param id 持仓id
     * @return 成功删除数量
     */
    long deleteAssetAllocationById(@Param("id") Integer id);

    /**
     * 根据基金id删除对应的资产分配
     * 
     * @param fundId 股票id
     * @return 成功删除数量
     */
    long deleteAssetAllocationByFundId(@Param("fundId") Integer fundId);

    /**
     * 根据fundIdList批量删除所有对应的资产分配
     * 
     * @param fundIdList 将要删除的资产分配的fundId列表
     * @return 成功删除数量
     */
    long deleteAssetAllocationBatchByFundId(List<Integer> fundIdList);

    /**
     * 清空asset_allocation表, <b>谨慎使用</b>
     * 
     * @return 成功删除数量
     */
    long deleteAll();

}
