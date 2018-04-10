package com.xwguan.autofund.manager.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.collections.list.TreeList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.xwguan.autofund.constant.AppConst;
import com.xwguan.autofund.entity.fund.AssetAllocation;
import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.entity.fund.FundCompany;
import com.xwguan.autofund.entity.fund.FundHistory;
import com.xwguan.autofund.entity.fund.FundInfo;
import com.xwguan.autofund.entity.fund.FundManager;
import com.xwguan.autofund.entity.fund.FundPosition;
import com.xwguan.autofund.entity.fund.RealTimeFundData;
import com.xwguan.autofund.entity.fund.SimilarTypeRankingHistory;
import com.xwguan.autofund.enums.SecurityTypeEnum;
import com.xwguan.autofund.enums.SymbolFormatEnum;
import com.xwguan.autofund.exception.io.AbnormalDataSourceException;
import com.xwguan.autofund.exception.io.EmptyInputException;
import com.xwguan.autofund.exception.io.EmptyResultException;
import com.xwguan.autofund.exception.io.InvalidFundCodeException;
import com.xwguan.autofund.exception.io.InvalidParamException;
import com.xwguan.autofund.manager.converter.JsVarToJsonConverter;
import com.xwguan.autofund.manager.api.FundDataManager;
import com.xwguan.autofund.manager.converter.CharSequenceToJsonConvertor;
import com.xwguan.autofund.manager.converter.SimpleCharSequenceToJsonConvertor;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.util.CodeUtils;
import com.xwguan.autofund.util.DateUtils;
import com.xwguan.autofund.util.MathUtils;

/**
 * 天天基金网获取基金数据
 * <p>API示例:
 * <ol>
 * <li>获取基金公司列表: http://fund.eastmoney.com/api/static/FundCommpanyInfo.js
 * <li>获取基金列表: http://fund.eastmoney.com/js/fundcode_search.js
 * <li>获取基金经理列表:
 * http://fund.eastmoney.com/Data/FundDataPortfolio_Interface.aspx?dt=14&mc=returnjson&ft=all&pn=50&pi=1&sc=abbname&st=asc
 * <p>参数解析: dt=14未知, mc=returnjson返回js变量名为returnjson, ft=all未知, pn=50每页50条, pi=1第一页, sc=abbname&st=asc按名字缩写升序排列
 * <li>基金详细数据: http://fund.eastmoney.com/pingzhongdata/000001.js
 * <li>基金历史净值数据:
 * http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code=150195&page=1&per=100&sdate=20170101&edate=20171124,
 * date可以不加, 返回值为html, 处理不方便, 一般不使用
 * <li>持仓数据 (内容在html中):
 * <p>股票http://fund.eastmoney.com/f10/FundArchivesDatas.aspx?type=jjcc&code=000001&topline=10,
 * <p>债券http://fund.eastmoney.com/f10/FundArchivesDatas.aspx?type=zqcc&code=000001
 * <li>实时数据http://fundgz.1234567.com.cn/js/000001.js
 * 
 * @author XWGuan
 * @version 1.0.1
 * @date 2017-11-24
 */
@SuppressWarnings("unused")
@Component
public class EasymoneyFundDataManager implements FundDataManager {

    private static final String FUND_COMPANY_URL = "http://fund.eastmoney.com/api/static/FundCommpanyInfo.js";

    private static final String FUND_LIST_URL = "http://fund.eastmoney.com/js/fundcode_search.js";

    private static final String BASE_FUND_MANAGER_URL = "http://fund.eastmoney.com/Data/FundDataPortfolio_Interface.aspx?dt=14&mc=managers&ft=all&pn=";

    private static final String SUFFIX_FUND_MANAGER_URL = "&pi=1&sc=abbname&st=asc";

    private static final String BASE_FUND_DETAIL_URL = "http://fund.eastmoney.com/pingzhongdata/";

    private static final String SUFFIX_FUND_DETAIL_URL = ".js";

    private static final String BASE_REAL_TIME_FUND_DATA_URL = "http://fundgz.1234567.com.cn/js/";

    private static final String SUFFIX_REAL_TIME_FUND_DATA_URL = ".js";

    private static final String CHARSET = AppConst.CHARSET;

    private static final ZoneOffset ZONE_OFFSET_BEIJING = ZoneOffset.ofHours(8);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 基金细节数据中丢弃的变量
     */
    private static final String[] FUND_DETAIL_DISCARD_VARS = {
        "ishb", // unknown
        "fS_name", "fS_code", // 基金或股票信息
        "fund_sourceRate", "fund_Rate", "fund_minsg", // 费率
        "zqCodes", // 基金持仓债券代码
        "syl_1n", "syl_6y", "syl_3y", "syl_1y", // 收益率
        "Data_fundSharesPositions", // 股票仓位测算
        "Data_grandTotal", // 累计收益率走势
        "Data_rateInSimilarPersent", // 同类排名百分比
        "Data_fluctuationScale", // 规模变动
        "Data_holderStructure", // 持有人结构
        "performanceEvaluation", // 业绩评价 ['选股能力', '收益率', '抗风险', '稳定性','择时能力']
        "Data_buySedemption", // 申购赎回
        "swithSameType" // 同类型基金涨幅榜
    };

    @Autowired
    private JsVarToJsonConverter jsVarToJsonConverter;

    @Autowired
    private SimpleCharSequenceToJsonConvertor simpleCharSequenceToJsonConvertor;

    @Autowired
    private DateTimeService dateTimeService;

    /*
     * 返回的js为
     * var FundCommpanyInfos=[
     * {"_id":"80000231","COMPANYCODE":"80000231","SNAME":"融通基金","SEARCHFIELD":"80000231融通基金管理有限公司融通基金RTJJ"},
     * {"_id":"80000222","COMPANYCODE":"80000222","SNAME":"华夏基金","SEARCHFIELD":"80000222华夏基金管理有限公司华夏基金HXJJ"},
     * ...]
     */
    @Override
    public List<FundCompany> listFundCompany()
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        List<FundCompany> fundCompanyList = new ArrayList<>();
        List<?> infos = (List<?>) getParsedJsonFromUrl(FUND_COMPANY_URL, jsVarToJsonConverter).get("FundCommpanyInfos");
        for (Object info : infos) {
            Map<?, ?> infoMap = (Map<?, ?>) info;
            String code = (String) infoMap.get("COMPANYCODE");
            String sname = (String) infoMap.get("SNAME");
            String searchField = (String) infoMap.get("SEARCHFIELD");
            // parse searchField
            String fname = null;
            String pinyinSname = null;
            String abbrPinyinSname = null;
            StringBuilder regexSb = new StringBuilder("^").append(code).append("([^\\s]+)").append(sname)
                .append("([^\\s]+)$");
            Pattern pattern = Pattern.compile(regexSb.toString());
            Matcher matcher = pattern.matcher(searchField);
            if (matcher.matches()) {
                fname = matcher.group(1);
                abbrPinyinSname = matcher.group(2);
            }
            // convert sname to pinyin, using jpinyin https://github.com/stuxuhai/jpinyin
            try {
                pinyinSname = PinyinHelper.convertToPinyinString(sname, "", PinyinFormat.WITHOUT_TONE);
            } catch (PinyinException e) {
                e.printStackTrace();
            }
            if (StringUtils.isEmpty(abbrPinyinSname)) {
                try {
                    abbrPinyinSname = PinyinHelper.getShortPinyin(sname);
                } catch (PinyinException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isEmpty(fname)) {
                fname = sname;
            }
            FundCompany company = new FundCompany(code, sname, fname, pinyinSname, abbrPinyinSname);
            fundCompanyList.add(company);
        }
        logger.debug("Fund companies from {}", FUND_COMPANY_URL);
        logger.debug("Result size: {}", fundCompanyList.size());
        // logger.debug("Result list: {}", fundCompanyList);
        return fundCompanyList;
    }

    @Override
    public List<Fund> listFund() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        List<Fund> fundList = new ArrayList<>();
        List<?> funds = (List<?>) getParsedJsonFromUrl(FUND_LIST_URL, jsVarToJsonConverter).get("r");
        for (Object fund : funds) {
            List<?> fieldList = (List<?>) fund;
            String code = (String) fieldList.get(0);
            String abbrPinyinName = (String) fieldList.get(1);
            String name = (String) fieldList.get(2);
            String type = (String) fieldList.get(3);
            String pinyinName = (String) fieldList.get(4);
            Fund newFund = new Fund(code, name, pinyinName, abbrPinyinName, type);
            fundList.add(newFund);
            newFund = null;
        }
        logger.debug("Fund from {}", FUND_LIST_URL);
        logger.debug("Result size: {}", fundList.size());
        // logger.debug("Result list: {}", fundList);
        return fundList;
    }

    @Override
    public List<FundManager> listFundManager()
        throws UnsupportedEncodingException, IOException, InterruptedException, ExecutionException, TimeoutException {
        List<FundManager> managerList = new ArrayList<>();
        // 获取总的记录数
        StringBuilder sb = new StringBuilder(BASE_FUND_MANAGER_URL).append(0).append(SUFFIX_FUND_MANAGER_URL);
        Map<?, ?> managerPage = (Map<?, ?>) getParsedJsonFromUrl(sb, jsVarToJsonConverter).get("managers");
        int record = (int) managerPage.get("record");
        logger.debug("Fund manager record: {}", record);
        // 获取所有基金经理列表
        sb = new StringBuilder(BASE_FUND_MANAGER_URL).append(record).append(SUFFIX_FUND_MANAGER_URL);
        managerPage = (Map<?, ?>) getParsedJsonFromUrl(sb, jsVarToJsonConverter).get("managers");
        List<?> data = (List<?>) managerPage.get("data");
        for (Object o : data) {
            List<?> managerData = (List<?>) o;
            String code = (String) managerData.get(0);
            String name = (String) managerData.get(1);
            String companyCode = (String) managerData.get(2);
            String companyName = (String) managerData.get(3);
            String currentFundCodes = (String) managerData.get(4);
            String currentFundNames = (String) managerData.get(5);
            String careerDays = (String) managerData.get(6);
            String currentTotalAsset = (String) managerData.get(10);
            FundManager fundManager = new FundManager(code, name, companyCode, companyName, currentFundCodes,
                currentFundNames, careerDays, currentTotalAsset);
            managerList.add(fundManager);
        }
        logger.debug("Fund from {}", sb.toString());
        logger.debug("Fund manager list size: {}", managerList.size());
        // logger.debug("Fund manager list: {}", managerList);
        return managerList;
    }

    @Override
    public Fund getFundDetail(String fundCode)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        // TODO 这里有重复获取和解析json数据的问题, 后续有时间再改
        // 解析基金数据
        List<FundHistory> fundHistoryList = getFundHistory(fundCode, null, null);
        FundInfo fundInfo = getFundInfo(fundCode);
        AssetAllocation assetAllocation = getAssetAllocation(fundCode);
        Fund fund = new Fund(fundCode, fundInfo, assetAllocation, fundHistoryList);
        logger.debug("Fund: {}", fund);
        return fund;
    }

    @Override
    public List<FundHistory> getFundHistory(String fundCode, LocalDate startDate, LocalDate endDate)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        if (endDate == null) {
            endDate = LocalDate.now(); // 默认结束日期为当日
        }
        if (startDate == null) {
            startDate = LocalDate.of(1990, 1, 1); // 默认从1990年1月1日开始, 此时还没有基金成立, 故能取到所有数据
        }
        if (startDate.isAfter(endDate)) {
            throw new InvalidParamException("The start date is after the end date");
        }
        Map<?, ?> resultMap = getFundDetailResultMap(fundCode);
        // 单位净值走势, 含有所有日期的单位净值, equityReturn-净值回报(%), 已考虑分红和拆分, unitMoney-每份派送金
        List<?> netWorthTrend = (List<?>) resultMap.get("Data_netWorthTrend");
        if (CollectionUtils.isEmpty(netWorthTrend)) {
            return new ArrayList<>(); // 新成立的基金可能没有历史数据, 直接返回null是不合适的
        }
        // 累计净值走势, 含有所有日期的累计净值,
        List<?> acWorthTrend = (List<?>) resultMap.get("Data_ACWorthTrend");
        // 可能某些日期有数据缺失, 且从结果来看, 缺失的总是累积净值, 使用Map<LocalDate, FundHistory>记录数据
        Map<LocalDate, FundHistory> dateHistoryMap = new HashMap<>();
        Stack<FundHistory> toFix = new Stack<>();
        parseAndFixFundHistory(netWorthTrend, acWorthTrend, startDate, endDate, dateHistoryMap, toFix);
        // 检查并尝试修复缺失数据, 直接使用前一天的单位净值差, 如有分红或拆分, 累计净值可能偏小
        List<FundHistory> fundHistoryList = new ArrayList<>(dateHistoryMap.values());
        logger.debug("FundHistory of {} from {} to {}, size: {}", fundCode, startDate, endDate, fundHistoryList.size()); // TODO
        return fundHistoryList;
    }

    /**
     * 解析并修复日期间的FundHistory数据, 结果在dateHistoryMap中.
     * <p>修复使用待修复日期与前一日的单位净值差加上前一日的累积净值.
     * 
     * @param netWorthTrend
     * @param acWorthTrend
     * @param startDate
     * @param endDate
     * @param dateHistoryMap 解析后的键值对
     * @param toFix 需要修复的FundHistory栈, 由于使用前一日数据修复, 而遍历是逆序的, 修复过程是后进先出的(LIFO)
     */
    private void parseAndFixFundHistory(List<?> netWorthTrend, List<?> acWorthTrend, LocalDate startDate,
        LocalDate endDate, Map<LocalDate, FundHistory> dateHistoryMap, Stack<FundHistory> toFix) {
        // 解析acWorthTrend
        for (int i = acWorthTrend.size() - 1; i >= 0; i--) {
            List<?> acWorthList = (List<?>) acWorthTrend.get(i);
            long timestamp = (long) acWorthList.get(0);
            LocalDate date = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZONE_OFFSET_BEIJING).toLocalDate();
            if (date.isAfter(endDate)) {
                continue; // 未到取数据日期
            }
            if (date.isBefore(startDate)) {
                break; // 超过应取最早日期时结束
            }
            Double accNav = (Double) acWorthList.get(1);
            FundHistory history = new FundHistory();
            history.setDate(date);
            history.setAccNav(accNav);
            dateHistoryMap.put(date, history);
        }
        // 解析netWorthTrend并进行修复
        int i = netWorthTrend.size() - 1;
        for (; i >= 0; i--) {
            Map<?, ?> netWorthMap = (Map<?, ?>) netWorthTrend.get(i);
            long timestamp = (long) netWorthMap.get("x"); // ms
            LocalDate date = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZONE_OFFSET_BEIJING)
                .toLocalDate();
            if (date.isAfter(endDate)) {
                continue; // 未到取数据日期
            }
            if (date.isBefore(startDate)) {
                break; // 超过应取最早日期时结束
            }
            Double nav = (Double) netWorthMap.get("y");
            Double equityReturn = Double.valueOf(netWorthMap.get("equityReturn").toString());
            String unitMoney = (String) netWorthMap.get("unitMoney");
            FundHistory history = dateHistoryMap.get(date);
            // dateHistoryMap中获取不到对应于date的FundHistory, 说明accNav缺失, 压入栈toFix中等待修复
            if (history == null) {
                if (i > 0) {
                    history = new FundHistory(date, nav, null, equityReturn, unitMoney);
                    toFix.push(history);
                    continue;
                } else {
                    // 此时i == 0, 表示已经完整遍历所有数据且首个数据也缺失accNav, 但其nav就是accnav, 也就是说一定存在完整的FundHistory
                    history = new FundHistory();
                    history.setDate(date);
                    history.setAccNav(nav);
                }
            }
            history.setNav(nav);
            history.setEquityReturnPct(equityReturn);
            history.setUnitMoney(unitMoney);
            // 当前FundHistory完整, 若toFix中有需要修复的FundHistory, 则按后进后出逐一进行修复
            if (toFix.size() > 0) {
                FundHistory fixed = history;
                FundHistory fixing = null;
                while (toFix.size() > 0) {
                    fixing = toFix.pop();
                    double accNav = MathUtils.round(fixing.getNav() - fixed.getNav() + fixed.getAccNav(), 3);
                    fixing.setAccNav(accNav);
                    dateHistoryMap.put(fixed.getDate(), fixed);
                    logger.info("\nFixed: {},\n with: {}", fixing, fixed); // TODO
                    fixing.setId(-1L); // TODO 标识是修复过的
                    fixed = fixing;
                }
            } else {
                dateHistoryMap.put(date, history);
            }
        }

        // 判断在取完startDate到endDate之间的数据后是否仍然需要修复, 若需要则再向前取1个非周末日期数据, 一般只有单日更新的时候会存在递归
        if (toFix.size() > 0) {
            LocalDate start = startDate.minusDays(startDate.getDayOfWeek().equals(DayOfWeek.MONDAY) ? 3 : 1);
            LocalDate end = startDate.minusDays(1);
            parseAndFixFundHistory(netWorthTrend, acWorthTrend, startDate.minusDays(10), startDate.minusDays(1),
                dateHistoryMap, toFix);
            // 递归调用本方法会添加多余的日期, 在结果map中将其移除
            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                dateHistoryMap.remove(date);
            }
        }
    }

    @Override
    public AssetAllocation getAssetAllocation(String fundCode)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        Map<?, ?> resultMap = getFundDetailResultMap(fundCode);
        logger.debug("Getting asset allocation of: {}", fundCode); // TODO
        // 资产配置, 当前数据源, 货币基金的资产分配在Data_assetAllocationCurrency中, 而Data_assetAllocation中是无用数据,
        // 其他基金在有的在Data_assetAllocation中
        boolean isNotMoneyFund = false;
        Map<?, ?> assetAllocationMap = (Map<?, ?>) resultMap.get("Data_assetAllocationCurrency");
        if (MapUtils.isEmpty(assetAllocationMap)) {
            assetAllocationMap = (Map<?, ?>) resultMap.get("Data_assetAllocation");
            if (MapUtils.isEmpty(assetAllocationMap)) {
                logger.warn("Cannot find asset allocation");
                return new AssetAllocation();
            }
            isNotMoneyFund = true;
        }
        // 包含资产分配日期的categories
        List<?> categories = (List<?>) assetAllocationMap.get("categories");
        // 新成立的基金可能没有资产分配, 此时categoryList为空
        if (categories.isEmpty()) {
            logger.warn("Empty asset allocation"); // TODO
            return new AssetAllocation();
        }
        // 货币基金第一个是最新的数据, 其他基金最后一个是最新的数据
        int latestIndex = isNotMoneyFund ? categories.size() - 1 : 0;
        String dateStr = (String) categories.get(latestIndex);
        LocalDate date = null;
        // 货币基金日期不带年, 其他的是完整日期
        if (isNotMoneyFund) {
            date = LocalDate.parse(dateStr);
        } else {
            date = LocalDate.parse(new StringBuilder().append(LocalDate.now().getYear()).append("-").append(dateStr)); // TODO
                                                                                                                       // 年可能不对
        }
        // 包含资产分配数据的series
        List<?> series = (List<?>) assetAllocationMap.get("series");
        // stock propertion
        Map<?, ?> stockMap = (Map<?, ?>) series.get(0);
        List<?> stockPctList = (List<?>) stockMap.get("data");
        Object stockPctObj = (Object) stockPctList.get(latestIndex);
        Double stockPct = stockPctObj == null ? 0 : Double.valueOf(stockPctObj.toString());
        // debenture propertion
        Map<?, ?> debentureMap = (Map<?, ?>) series.get(1);
        List<?> debenturePctList = (List<?>) debentureMap.get("data");
        Object debenturePctObj = (Object) debenturePctList.get(latestIndex);
        Double debenturePct = debenturePctObj == null ? 0 : Double.valueOf(debenturePctObj.toString());
        // cash propertion
        Map<?, ?> cashMap = (Map<?, ?>) series.get(2);
        List<?> cashPctList = (List<?>) cashMap.get("data");
        Object cashPctObj = (Object) cashPctList.get(latestIndex);
        Double cashPct = cashPctObj == null ? 0 : Double.valueOf(cashPctObj.toString());
        // net assert, 货币基金没有此项数据
        Double netAssert = null;
        if (isNotMoneyFund) {
            Map<?, ?> netAssertMap = (Map<?, ?>) series.get(3);
            List<?> netAssertList = (List<?>) netAssertMap.get("data");
            if (CollectionUtils.isNotEmpty(netAssertList)) {
                netAssert = Double.valueOf(netAssertList.get(latestIndex).toString());
            }
        }
        AssetAllocation assetAllocation = new AssetAllocation(date, stockPct, debenturePct, cashPct, null,
            netAssert);
        // TODO 股票代码解析问题较多, 数据混乱
        // 持仓股票代码
        // List<FundPosition> stockPositionList = new ArrayList<>();
        // List<String> positionSymbolList = parsePositionStockCodes(resultMap);
        // for (String symbol : positionSymbolList) {
        // FundPosition fundPosition = new FundPosition(SecurityTypeEnum.STOCK, symbol, null); // 当前数据源不包含持仓
        // stockPositionList.add(fundPosition);
        // }
        // assetAllocation.setStockList(stockPositionList);

        logger.debug("AssetAllocation: {}", assetAllocation); // TODO
        return assetAllocation;
    }

    @Override
    public FundInfo getFundInfo(String fundCode)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        // 现任基金经理
        List<?> currentFundManager = (List<?>) getFundDetailResultMap(fundCode).get("Data_currentFundManager"); // TODO
        Map<?, ?> currentFundManagerMap = (Map<?, ?>) currentFundManager.get(0);
        String managerCode = (String) currentFundManagerMap.get("id");
        String workTime = (String) currentFundManagerMap.get("workTime");
        FundInfo fundInfo = new FundInfo(LocalDate.now(), managerCode, workTime, null); // 当前数据源不包含基金公司
        logger.debug("FundInfo: {}", fundInfo);
        return fundInfo;
    }

    @Override
    public RealTimeFundData getRealTimeFundData(String fundCode)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        StringBuilder urlSb = new StringBuilder(BASE_REAL_TIME_FUND_DATA_URL).append(fundCode)
            .append(SUFFIX_REAL_TIME_FUND_DATA_URL);
        Map<?, ?> resultMap = getParsedJsonFromUrl(urlSb.toString(), simpleCharSequenceToJsonConvertor);
        LocalDateTime dateTime = LocalDateTime.parse((CharSequence) resultMap.get("gztime"), DATE_TIME_FORMATTER); // 估值时间
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        Double estimatedNav = Double.valueOf((String) resultMap.get("gsz")); // 估算值
        Double estimatedChangeRatePct = Double.valueOf((String) resultMap.get("gszzl")); // 估算值??
        RealTimeFundData realTimeFundData = new RealTimeFundData(fundCode, date, time, estimatedNav,
            estimatedChangeRatePct);
        return realTimeFundData;
    }

    /**
     * 解析SimilarTypeRankingHistory
     * 
     * @param resultMap 方法getFundDetail的resultMap
     * @return
     */
    @SuppressWarnings("unused")
    private List<SimilarTypeRankingHistory> parseSimilarTypeRankingHistory(Map<?, ?> resultMap) {
        // 同类排名
        List<SimilarTypeRankingHistory> rankingHistoryList = new ArrayList<>();
        List<?> rateInSimilarType = (List<?>) resultMap.get("Data_rateInSimilarType");
        for (Object rankingHistory : rateInSimilarType) {
            Map<?, ?> rankingHistoryMap = (Map<?, ?>) rankingHistory;
            long timestamp = (long) rankingHistoryMap.get("x");
            LocalDate date = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.UTC).toLocalDate();
            Integer ranking = (Integer) rankingHistoryMap.get("y");
            Integer totalSimilar = Integer.valueOf((String) rankingHistoryMap.get("sc")); // json中为字符串
            SimilarTypeRankingHistory history = new SimilarTypeRankingHistory(date, ranking, totalSimilar);
            rankingHistoryList.add(history);
        }
        logger.debug("Similar type ranking history: {}", rankingHistoryList);
        return rankingHistoryList;
    }

    // /**
    // * 解析基金持仓股票代码
    // *
    // * @param resultMap 方法getFundDetail的resultMap
    // * @return
    // * @throws InvalidParamException
    // */
    // private List<String> parsePositionStockCodes(Map<?, ?> resultMap) throws InvalidParamException {
    // // 基金持仓股票代码
    // List<?> stockCodes = (List<?>) resultMap.get("stockCodes");
    // List<String> positionSymbolList = new ArrayList<>();
    // // 一些债券型的可能没有"stockCodes"
    // if (CollectionUtils.isNotEmpty(stockCodes)) {
    // for (Object stockCode : stockCodes) {
    // String symbol = CodeUtils.convert((String) stockCode, SymbolFormatEnum.EASYMONEY,
    // SymbolFormatEnum.AUTOFUND);
    // positionSymbolList.add(symbol);
    // }
    // logger.debug("FundPosition: {}", positionSymbolList);
    // }
    // return positionSymbolList;
    // }

    /**
     * 获取FundDetail并解析json成Map<?,?>
     * 
     * @param fundCode 基金代码
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    private Map<?, ?> getFundDetailResultMap(String fundCode)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        if (!CodeUtils.isFundCode(fundCode)) {
            throw new InvalidFundCodeException(
                "The input fund code: " + fundCode + " is invalid. It should be 6 numbers like \"000001\"");
        }
        StringBuilder sb = new StringBuilder(BASE_FUND_DETAIL_URL).append(fundCode).append(SUFFIX_FUND_DETAIL_URL);
        Map<?, ?> resultMap = getParsedJsonFromUrl(sb, Arrays.asList(FUND_DETAIL_DISCARD_VARS), jsVarToJsonConverter);
        // TODO resultMap放入redis中, 避免重复从网络获取和解析
        if (MapUtils.isEmpty(resultMap)) {
            throw new EmptyResultException("The returned value is empty. please check the fund code " + fundCode);
        }
        return resultMap;
    }

    private Map<?, ?> getParsedJsonFromUrl(CharSequence urlStr, CharSequenceToJsonConvertor convertor)
        throws UnsupportedEncodingException, MalformedURLException,
        IOException, InterruptedException, ExecutionException, TimeoutException {
        return getParsedJsonFromUrl(urlStr, null, convertor);
    }

    private Map<?, ?> getParsedJsonFromUrl(CharSequence urlStr, List<String> discardVars, CharSequenceToJsonConvertor convertor)
        throws UnsupportedEncodingException, MalformedURLException, IOException, InterruptedException,
        ExecutionException, TimeoutException {
        URL url = new URL(urlStr.toString());
        Reader reader = new InputStreamReader(url.openStream(), CHARSET);
        String json = convertor.convert(reader, discardVars);
        return new ObjectMapper().readValue(json, Map.class);
    }

}
