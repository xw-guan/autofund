package com.xwguan.autofund.service.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.fund.AssetAllocation;
import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.entity.fund.RealTimeFundData;
import com.xwguan.autofund.enums.UpdateStateEnum;
import com.xwguan.autofund.exception.io.InvalidParamException;

/**
 * 基金数据更新服务
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-18
 */
public interface FundService {
    /**
     * 根据searchField检索基金, 包含FundHistory, 不含FindInfo
     * 
     * @param searchField 检索字段
     * @param page 分页对象, 分页对Fund对象进行, 值为null时不进行分页
     * @return 与searchField匹配的基金对象列表
     */
    public List<Fund> searchFund(String searchField, Page page);

    /**
     * 根据数据表中的id字段精确查询基金, 不包含FundInfo和FundHistory
     * 
     * @param id 基金id
     * @return 与id对应的Fund对象
     */
    Fund getFund(Integer id);

    /**
     * 根据数据表中的id字段精确查询基金
     * 
     * @param id 基金id
     * @param includeFundInfo 是否包含基金信息. 值为true时包含, 值为false或null时不包含
     * @param includeLatestHistory 是否包含最近的历史数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Fund对象进行, 值为null时不进行分页
     * @return 与id对应的Fund对象
     */
    Fund getFund(Integer id, Boolean includeFundInfo, Boolean includeLatestHistory, Page page);

    /**
     * 根据基金代码精确查询基金
     * 
     * @param code 基金代码
     * @param includeFundInfo 是否包含基金信息. 值为true时包含, 值为false或null时不包含
     * @param includeLatestHistory 是否包含最近的历史数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Fund对象进行, 值为null时不进行分页
     * @return 与id对应的Fund对象
     */
    Fund getFund(String code, Boolean includeFundInfo, Boolean includeLatestHistory, Page page);

    /**
     * 根据基金类型查询
     * 
     * @param type 基金类型
     * @param includeFundInfo 是否包含基金信息. 值为true时包含, 值为false或null时不包含
     * @param includeLatestHistory 是否包含最近的历史数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Fund对象进行, 值为null时不进行分页
     * @return 所有类型符合type的基金
     */
    List<Fund> listFundByType(String type, Boolean includeFundInfo, Boolean includeLatestHistory, Page page);

    /**
     * 查询对应于fundId的资产分配, 根据持仓比例排序
     * 
     * @param fundId 基金id
     * @return 对应于fundId的资产分配, 根据持仓比例排序
     * @throws InvalidParamException fundId为null时抛出
     */
    AssetAllocation getAssetAllocationByFundId(Integer fundId) throws InvalidParamException;

    /**
     * 获取基金实时估值
     * 
     * @param fundId 基金id
     * @return
     * @throws IOException
     */
    RealTimeFundData getRealTimeFundData(Integer fundId) throws IOException;

    /**
     * 查询fund表中包含的基金数量
     * 
     * @return 基金数量
     */
    int countFund();

    /**
     * 计算需要更新的基金数量, 只检查基金历史数据, 其余应采用定期更新
     * 
     * @return int 需要更新的股票数量
     * @throws TimeoutException 
     * @throws ExecutionException 
     * @throws InterruptedException 
     * @throws IOException 
     */
    int countUpdateRequired() throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 更新fund表, 若基金已存在则更新, 若不存在则插入新数据
     * 
     * @return 封装更新状态的StockUpdateState对象
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    UpdateStateEnum updateFund() throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 更新fund_company表, 若基金公司已存在则更新, 若不存在则插入新数据
     * 
     * @return 封装更新状态的StockUpdateState对象
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    UpdateStateEnum updateFundCompany() throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 更新fund_manager表, 若基金经理已存在则更新, 若不存在则插入新数据
     * 
     * @return 封装更新状态的StockUpdateState对象
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException
     */
    UpdateStateEnum updateFundManager() throws MalformedURLException, UnsupportedEncodingException, IOException,
        InterruptedException, ExecutionException, TimeoutException;

    /**
     * 更新所有基金的asset_allocation表, 若资产分配已存在则更新, 若不存在则插入新数据
     * 
     * @return 封装更新状态的StockUpdateState对象
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    UpdateStateEnum updateAssetAllocation()
        throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 更新基金历史
     * 
     * @param useMultiThread 是否使用多线程更新, 开启多线程更新能提高效率, 但CPU和内存使用会急剧增加
     * @return 基金id和更新状态的Map
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    Map<Integer, UpdateStateEnum> updateFundHistory(Boolean useMultiThread)
        throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 根据股票idList批量删除对应的基金
     * 
     * @param idList 将要删除的基金id列表
     * @return int 成功删除数量
     * @throws IOException 网络连接问题, 股票代码列表为空, 股票代码无效等
     */
    int deleteFund(List<Integer> idList) throws IOException;
}
