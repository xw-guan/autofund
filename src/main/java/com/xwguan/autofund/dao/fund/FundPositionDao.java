package com.xwguan.autofund.dao.fund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.FundAndStockCUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.fund.AssetAllocation;
import com.xwguan.autofund.entity.fund.FundPosition;
import com.xwguan.autofund.enums.SecurityTypeEnum;

/**
 * 股票数据dao, 提供对stock_data表的CRUD操作
 * 
 * @author XWGuan
 * @version 1.0.1
 * @date 2017-10-22
 */
public interface FundPositionDao extends FundAndStockCUDBatchDao<AssetAllocation> {

    /**
     * 根据id查询基金持仓数据
     * 
     * @param id 基金持仓id
     * @return 对应id的基金持仓
     */
    FundPosition getFundPositionById(@Param("id") Integer id);

    /**
     * 查询所有基金持仓数据, 根据assetAllocationId和证券类型排序
     * 
     * @param page 分页对象, 值为null时不进行分页
     * @return 所有基金持仓数据
     */
    List<FundPosition> listAllFundPosition(@Param("page") Page page);

    /**
     * 查询对应于assetAllocationId的持仓数据, 根据和证券类型排序
     * 
     * @param assetAllocationId
     * @param page 分页对象, 值为null时不进行分页
     * @return 对应于assetAllocationId的持仓数据, 根据和证券类型排序
     */
    List<FundPosition> listFundPositionByAssetAllocationId(@Param("assetAllocationId") Integer assetAllocationId,
        @Param("page") Page page);

    /**
     * 查询对应于assetAllocationId的持仓数据, 根据和证券类型排序
     * 
     * @param assetAllocationId
     * @param securityType 证券类型
     * @param page 分页对象, 值为null时不进行分页
     * @return 对应于assetAllocationId的持仓数据, 根据和证券类型排序
     */
    List<FundPosition> listFundPositionBySecurityType(@Param("assetAllocationId") Integer assetAllocationId,
        @Param("securityType") SecurityTypeEnum securityType, @Param("page") Page page);

    /**
     * 将传入的fundPositionList中的所有对象插入到表中
     * 
     * @param fundPositionList 将要插入的FundPosition对象列表
     * @return 成功插入数量
     */
    long insertFundPosition(List<FundPosition> fundPositionList);

    /**
     * 更新与传入FundPosition对象对应的持仓数据
     * 
     * @param fundPosition 将要更新的FundPosition对象
     * @return 成功更新数量
     */
    long updateFundPosition(@Param("fundPosition") FundPosition fundPosition);

    /**
     * 根据id删除对应的持仓数据
     * 
     * @param id 持仓id
     * @return 成功删除数量
     */
    long deleteFundPositionById(@Param("id") Integer id);

    /**
     * 根据assetAllocationId删除所有对应的持仓数据
     * 
     * @param assetAllocationId 股票id
     * @return 成功删除数量
     */
    long deleteFundPositionByAssetAllocationId(@Param("assetAllocationId") Integer assetAllocationId);

    /**
     * 根据assetAllocationIdList批量删除所有对应的持仓数据
     * 
     * @param assetAllocationIdList 将要删除的持仓数据的assetAllocationId列表
     * @return 成功删除数量
     */
    long deleteFundPositionBatchByAssetAllocationId(List<Integer> assetAllocationIdList);

    /**
     * 清空stock_data表, <b>谨慎使用</b>
     * 
     * @return 成功删除数量
     */
    long deleteAll();

}
