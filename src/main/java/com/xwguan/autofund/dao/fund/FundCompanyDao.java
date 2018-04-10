package com.xwguan.autofund.dao.fund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.FundAndStockCUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.fund.FundCompany;

/**
 * 提供对fund_company表的CRUD操作
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-10-10
 */
public interface FundCompanyDao extends FundAndStockCUDBatchDao<FundCompany> {
    /**
     * 根据id查询基金公司
     * 
     * @param id 基金公司id
     * @return 对应id的FundCompany对象
     */
    FundCompany getFundCompanyById(@Param("id") Integer id);

    /**
     * 根据数据表中的id字段精确查询基金公司
     * 
     * @param code 基金公司代码
     * @return 对应code的FundCompany对象
     */
    FundCompany getFundCompanyByCode(@Param("code") String code);

    /**
     * 查询所有基金公司
     * 
     * @param page 分页对象, 分页对基金公司数据进行, 值为null时不进行分页
     * @return FundCompany对象列表
     */
    List<FundCompany> listAllFundCompany(@Param("page") Page page);

    /**
     * 根据searchField模糊查询
     * 
     * @param searchField 查询条件
     * @param page 分页对象, 分页对FundCompany对象进行, 值为null时不进行分页
     * @return 相关基金公司代码或基金公司名称FundCompany对象列表
     */
    List<FundCompany> listFundCompanyBySearchField(@Param("searchField") String searchField, @Param("page") Page page);

    /**
     * 查询fundCompany表中包含的基金公司数量
     * 
     * @return 基金公司数量
     */
    int countFundCompany();

    /**
     * 将传入的fundCompanyList中的所有对象插入到表中
     * 
     * @param fundCompanyList 将要插入的FundCompany对象列表
     * @return 成功插入数量
     */
    int insertFundCompany(List<FundCompany> fundCompanyList);

    /**
     * 将FundCompany对象插入fundCompany表中, 并将主键值设为FundCompany对象的id属性值
     * 
     * @param fundCompany 将要插入fundCompany表的fundCompany对象, 至少需要symbol, name和index字段不为空
     * @return 成功插入数量
     */
    int insertAndGetId(FundCompany fundCompany);

    /**
     * 更新表中与传入FundCompany对象对应的基金公司
     * 
     * @param fundCompany 将要更新的FundCompany对象
     * @return 成功更新数量
     */
    int updateFundCompany(@Param("fundCompany") FundCompany fundCompany);

    /**
     * 根据基金公司id删除对应的基金公司
     * 
     * @param id
     * @return 成功删除数量
     */
    int deleteFundCompanyById(@Param("id") Integer id);

    /**
     * 根据基金公司idList批量删除对应的基金公司
     * 
     * @param idList 将要删除的基金公司id列表
     * @return 成功删除数量
     */
    int deleteFundCompanyBatchById(List<Integer> idList);
}
