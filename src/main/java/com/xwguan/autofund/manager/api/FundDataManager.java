package com.xwguan.autofund.manager.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.xwguan.autofund.entity.fund.AssetAllocation;
import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.entity.fund.FundCompany;
import com.xwguan.autofund.entity.fund.FundHistory;
import com.xwguan.autofund.entity.fund.FundInfo;
import com.xwguan.autofund.entity.fund.FundManager;
import com.xwguan.autofund.entity.fund.RealTimeFundData;

/**
 * 基金数据获取
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-24
 */
public interface FundDataManager {

    /**
     * 获取基金公司列表
     * 
     * @return
     * @throws IOException
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    List<FundCompany> listFundCompany() throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 获取基金列表
     * 
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    List<Fund> listFund() throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 获取基金经理列表
     * 
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    List<FundManager> listFundManager() throws MalformedURLException, UnsupportedEncodingException, IOException,
        InterruptedException, ExecutionException, TimeoutException;

    /**
     * 获取基金详细信息
     * <p><b>注意:</b> 返回的Fund对象中与数据库相关的属性, 如FundHistory, AssertAllocation和FundInfo的fundId没有值, 需要另行设置
     * 
     * @param code 基金代码
     * @return
     * @throws IOException
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    Fund getFundDetail(String code) throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 获取开始日期到结束日期间的基金历史数据, 开始日期为null时从基金创建日期开始获取, 结束日期为null时获取至最新数据日期
     * <p><b>注意:</b> 返回的对象中与数据库相关的属性, fundId没有值, 需要另行设置
     * 
     * @param fundCode
     * @param startDate
     * @param endDate
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    List<FundHistory> getFundHistory(String fundCode, LocalDate startDate, LocalDate endDate)
        throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 获取最新公布的资产配置, 若没有资产配置则返回null
     * <p><b>注意:</b> 返回的对象中与数据库相关的属性, fundId没有值, 需要另行设置
     * 
     * @param fundCode
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    AssetAllocation getAssetAllocation(String fundCode)
        throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 获取当前的基金信息
     * <p><b>注意:</b> 返回的对象中与数据库相关的属性, fundId没有值, 需要另行设置; 当前数据源不包含基金公司
     * 
     * @param fundCode
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    FundInfo getFundInfo(String fundCode)
        throws IOException, InterruptedException, ExecutionException, TimeoutException;

    /**
     * 获取基金实时估值
     * 
     * @param fundCode
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    RealTimeFundData getRealTimeFundData(String fundCode)
        throws IOException, InterruptedException, ExecutionException, TimeoutException;
}
