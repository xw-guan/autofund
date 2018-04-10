package com.xwguan.autofund.dao.fund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.FundAndStockCUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.fund.Fund;

/**
 * 基金dao, 提供对fund表的CRUD操作
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-10-10
 */
public interface FundDao extends FundAndStockCUDBatchDao<Fund> {
    /**
     * 根据id查询基金代码
     * 
     * @param id
     * @return
     */
    String getCodeById(@Param("id") Integer id);

    /**
     * 根据基金代码查询id
     * 
     * @param code 基金代码
     * @return
     */
    Integer getIdByCode(@Param("code") String code);
    
    /**
     * 根据数据表中的id字段精确查询基金
     * 
     * @param id 基金id
     * @param includeFundInfo 是否包含基金信息. 值为true时包含, 值为false或null时不包含
     * @param includeLatestHistory 是否包含最近的历史数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Fund对象进行, 值为null时不进行分页
     * @return 与id对应的Fund对象
     */
    Fund getFundById(@Param("id") Integer id, @Param("includeFundInfo") Boolean includeFundInfo,
        @Param("includeLatestHistory") Boolean includeLatestHistory, @Param("page") Page page);

    /**
     * 根据基金代码精确查询基金
     * 
     * @param code 基金代码
     * @param includeFundInfo 是否包含基金信息. 值为true时包含, 值为false或null时不包含
     * @param includeLatestHistory 是否包含最近的历史数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Fund对象进行, 值为null时不进行分页
     * @return 基金代码为code的基金对象
     */
    Fund getFundByCode(@Param("code") String code, @Param("includeFundInfo") Boolean includeFundInfo,
        @Param("includeLatestHistory") Boolean includeLatestHistory, @Param("page") Page page);

    /**
     * 查询所有基金
     * 
     * @param includeFundInfo 是否包含基金信息. 值为true时包含, 值为false或null时不包含
     * @param includeHistory 是否包含历史数据. 值为true时包含, 值为false或null时不包含
     * @return Fund对象列表
     */
    List<Fund> listAllFund(@Param("includeFundInfo") Boolean includeFundInfo,
        @Param("includeHistory") Boolean includeHistory);
    
    /**
     * 查询所有基金代码
     * 
     * @return 所有基金代码列表
     */
    List<String> listAllFundCode();

    /**
     * 根据searchField进行查询, 返回与searchField匹配的Fund对象列表, 所有字段与searchField均为左侧匹配
     * 
     * @param searchField 查询条件
     * @param includeFundInfo 是否包含基金信息. 值为true时包含, 值为false或null时不包含
     * @param includeLatestHistory 是否包含最近的历史数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Fund对象进行, 值为null时不进行分页
     * @return searchField匹配的Fund对象列表, 其中的Fund对象包含fundInfo和最近历史记录
     */
    List<Fund> listFundBySearchField(@Param("searchField") String searchField,
        @Param("includeFundInfo") Boolean includeFundInfo, @Param("includeLatestHistory") Boolean includeLatestHistory,
        @Param("page") Page page);

    /**
     * 根据基金类型查询
     * 
     * @param type 基金类型
     * @param includeFundInfo 是否包含基金信息. 值为true时包含, 值为false或null时不包含
     * @param includeLatestHistory 是否包含最近的历史数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Fund对象进行, 值为null时不进行分页
     * @return 所有类型符合type的基金
     */
    List<Fund> listFundByType(@Param("type") String type, @Param("includeFundInfo") Boolean includeFundInfo,
        @Param("includeLatestHistory") Boolean includeLatestHistory, @Param("page") Page page);

    /**
     * 查询fund表中包含的基金数量
     * 
     * @return 基金数量
     */
    int countFund();

    /**
     * 将传入的fundList中的所有对象插入到表中
     * 
     * @param fundList 将要插入的Fund对象列表
     * @return 成功插入数量
     */
    int insertFund(List<Fund> fundList);

    /**
     * 将Fund对象插入fund表中, 并将主键值设为Fund对象的id属性值
     * 
     * @param fund 将要插入fund表的fund对象, 至少需要code, name和index字段不为空
     * @return 成功插入数量
     */
    int insertAndGetId(Fund fund);

    /**
     * 更新表中与传入Fund对象对应的基金
     * 
     * @param fund 将要更新的Fund对象
     * @return 成功更新数量
     */
    int updateFund(@Param("fund") Fund fund);

    /**
     * 根据基金id删除对应的基金
     * 
     * @param id
     * @return 成功删除数量
     */
    int deleteFundById(@Param("id") Integer id);

    /**
     * 根据基金idList批量删除对应的基金
     * 
     * @param idList 将要删除的基金id列表
     * @return 成功删除数量
     */
    int deleteFundBatchById(List<Integer> idList);

    /**
     * 根据基金代码删除对应的基金
     * 
     * @param code
     * @return 成功删除数量
     */
    int deleteFundByCode(@Param("code") String code);
}
