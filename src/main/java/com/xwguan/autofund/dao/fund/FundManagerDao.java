package com.xwguan.autofund.dao.fund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.FundAndStockCUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.fund.FundManager;

/**
 * 提供对fund_company表的CRUD操作
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-10-10
 */
public interface FundManagerDao extends FundAndStockCUDBatchDao<FundManager> {
    /**
     * 根据id查询基金经理
     * 
     * @param id 基金经理id
     * @return 对应id的FundManager对象
     */
    FundManager getFundManagerById(@Param("id") Integer id);

    /**
     * 根据数据表中的id字段精确查询基金经理
     * 
     * @param code 基金经理代码
     * @return 对应code的FundManager对象
     */
    FundManager getFundManagerByCode(@Param("code") String code);

    /**
     * 查询所有基金经理
     * 
     * @param page 分页对象, 分页对基金经理数据进行, 值为null时不进行分页
     * @return 所有FundManager对象列表
     */
    List<FundManager> listAllFundManager(@Param("page") Page page);

    /**
     * 根据searchField模糊查询
     * 
     * @param searchField 查询条件
     * @param page 分页对象, 分页对FundManager对象进行, 值为null时不进行分页
     * @return searchField相关的FundManager对象列表
     */
    List<FundManager> listFundManagerBySearchField(@Param("searchField") String searchField, @Param("page") Page page);

    /**
     * 列出某一基金公司的所有经理
     * 
     * @param companyCode 基金公司代码
     * @param page 分页对象, 分页对FundManager对象进行, 值为null时不进行分页
     * @return 某一基金公司的所有经理列表
     */
    List<FundManager> listFundManagerByCompanyCode(@Param("companyCode") String companyCode, @Param("page") Page page);
    
    /**
     * 查询fundManager表中包含的基金经理数量
     * 
     * @return 基金经理数量
     */
    int countFundManager();

    /**
     * 将传入的fundManagerList中的所有对象插入到表中
     * 
     * @param fundManagerList 将要插入的FundManager对象列表
     * @return 成功插入数量
     */
    int insertFundManager(List<FundManager> fundManagerList);

    /**
     * 将FundManager对象插入fundManager表中, 并将主键值设为FundManager对象的id属性值
     * 
     * @param fundManager 将要插入fundManager表的fundManager对象, 至少需要symbol, name和index字段不为空
     * @return 成功插入数量
     */
    int insertAndGetId(FundManager fundManager);

    /**
     * 更新表中与传入FundManager对象对应的基金经理
     * 
     * @param fundManager 将要更新的FundManager对象
     * @return 成功更新数量
     */
    int updateFundManager(@Param("fundManager") FundManager fundManager);

    /**
     * 根据基金经理id删除对应的基金经理
     * 
     * @param id
     * @return 成功删除数量
     */
    int deleteFundManagerById(@Param("id") Integer id);

    /**
     * 根据基金经理idList批量删除对应的基金经理
     * 
     * @param idList 将要删除的基金经理id列表
     * @return 成功删除数量
     */
    int deleteFundManagerBatchById(List<Integer> idList);
}
