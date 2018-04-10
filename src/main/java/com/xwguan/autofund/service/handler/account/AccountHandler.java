package com.xwguan.autofund.service.handler.account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.dao.account.AssetHistoryDao;
import com.xwguan.autofund.dao.account.TradeDetailDao;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.entity.plan.backtest.Drawdown;
import com.xwguan.autofund.exception.OverMaxIterationNumberException;
import com.xwguan.autofund.exception.io.EmptyInputException;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.service.handler.AbstractEntityHandler;
import com.xwguan.autofund.service.handler.CleanlyCopyable;
import com.xwguan.autofund.service.template.account.AccountTemplate;
import com.xwguan.autofund.service.template.account.AssetHistoryTemplate;
import com.xwguan.autofund.service.template.account.TradeDetailTemplate;
import com.xwguan.autofund.service.util.Comparators;
import com.xwguan.autofund.service.util.Predicates;
import com.xwguan.autofund.util.FinanceUtils;
import com.xwguan.autofund.util.MathUtils;
import com.xwguan.autofund.util.ToStringUtils;

/**
 * 账户处理者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-07
 */
@Component
@Scope("prototype")
public class AccountHandler extends AbstractEntityHandler<Account> implements CleanlyCopyable<Account> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 是否从数据库获取数据, 主要用于getLatestAssetHistory和getLatestTradeDetail方法, 默认为true
     */
    private boolean connDbForData = true;

    @Autowired
    private AssetHistoryDao assetHistoryDao;

    @Autowired
    private TradeDetailDao tradeDetailDao;

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private TradeDetailTemplate tradeDetailTemplate;

    @Autowired
    private AssetHistoryTemplate assetHistoryTemplate;

    @Autowired
    private AccountTemplate accountTemplate;

    @Override
    public AccountHandler handle(Account account) {
        super.handle(account);
        return this;
    }

    public AccountHandler setConnDbForData(boolean connDbForData) {
        this.connDbForData = connDbForData;
        return this;
    }

    /**
     * 获取最新的AssetHistory
     * <ul>
     * <li>若account中的assetHistoryList非空, 则获取列表中的日期最大值, 此时<b>默认列表中不会有重复日期</b>
     * <li>若account中的assetHistoryList为空且connDbForData为true, 则尝试从数据库中获取, 若数据库中也为空, 则返回Optional.EMPTY
     * <li>若account中的assetHistoryList为空且connDbForData为false, 则返回Optional.EMPTY
     * </ul>
     *
     * @return
     */
    public Optional<AssetHistory> getLatestAssetHistory() {
        AssetHistory latestHistory = getAccount().getAssetHistoryList().parallelStream()
            .max(Comparators.HISTORICAL_DATE_COMPARATOR)
            .orElse(connDbForData && getAccount().getId() > 0
                ? assetHistoryDao.getLatestByAccountId(getAccount().getId())
                : null);
        return Optional.ofNullable(latestHistory);
    }

    /**
     * 获取某交易日的AssetHistory
     * 
     * <ul>
     * <li>若在account的assetHistoryList中找到, 则获取第一个值返回, 此时<b>默认列表中不会有重复日期</b>
     * <li>若在account的assetHistoryList中没找到且connDbForData为true, 则尝试从数据库中获取, 若数据库中也为空, 则返回Optional.EMPTY
     * <li>若在account的assetHistoryList中没找到且connDbForData为false, 则返回Optional.EMPTY
     * </ul>
     * 
     * @param tradeDate 交易日日期
     * @return 包含AssetHistory的Optional
     */
    public Optional<AssetHistory> getAssetHistory(LocalDate tradeDate) {
        AssetHistory assetHistory = getAccount().getAssetHistoryList().parallelStream()
            .filter(as -> as.getDate().equals(tradeDate))
            .findFirst()
            .orElse(connDbForData && getAccount().getId() > 0
                ? assetHistoryDao.getByAccountIdAndDate(getAccount().getId(), tradeDate)
                : null);
        return Optional.ofNullable(assetHistory);
    }

    /**
     * 获取某日前一交易日的AssetHistory
     * 
     * <ul>
     * <li>若在account的assetHistoryList中找到, 则获取第一个值返回, 此时<b>默认列表中不会有重复日期</b>
     * <li>若在account的assetHistoryList中没找到且connDbForData为true, 则尝试从数据库中获取, 若数据库中也为空, 则返回Optional.EMPTY
     * <li>若在account的assetHistoryList中没找到且connDbForData为false, 则返回Optional.EMPTY
     * </ul>
     * 
     * @param date 日期
     * @return 包含AssetHistory的Optional
     */
    public Optional<AssetHistory> getPrevAssetHistory(LocalDate date) {
        return getAssetHistory(dateTimeService.prevTradeDay(date));
    }

    /**
     * 获取最新的TradeDetail, 异常状态会被过滤
     * <ul>
     * <li>若account中的tradeDetailList非空, 则获取列表中的日期最大值, 此时<b>默认列表中不会有重复日期</b>
     * <li>若account中的tradeDetailList为空且connDbForData为true, 则尝试从数据库中获取, 若数据库中也为空, 则返回Optional.EMPTY
     * <li>若account中的tradeDetailList为空且connDbForData为false, 则返回Optional.EMPTY
     * </ul>
     *
     * @return
     */
    public Optional<TradeDetail> getLatestTradeDetail() {
        TradeDetail latestHistory = getAccount().getTradeDetailList().parallelStream()
            .filter(Predicates::validTradeDetail) // 过滤异常状态
            .max(Comparators.HISTORICAL_DATE_COMPARATOR)
            .orElse(connDbForData && getAccount().getId() > 0
                ? tradeDetailDao.getLatestByAccountId(getAccount().getId())
                : null);
        return Optional.ofNullable(latestHistory);
    }

    /**
     * 获取某交易日的TradeDetail, 异常状态不过滤
     * 
     * <ul>
     * <li>若在account的tradeDetailList中找到, 则获取第一个值返回, 此时<b>默认列表中不会有重复日期</b>
     * <li>若在account的tradeDetailList中没找到且connDbForData为true, 则尝试从数据库中获取, 若数据库中也为空, 则返回Optional.EMPTY
     * <li>若在account的tradeDetailList中没找到且connDbForData为false, 则返回Optional.EMPTY
     * </ul>
     * 
     * @param tradeDate
     * @return 包含TradeDetail的Optional
     */
    public Optional<TradeDetail> getTradeDetail(LocalDate tradeDate) {
        TradeDetail tradeDetail = getAccount().getTradeDetailList().parallelStream()
            .filter(td -> td.getDate().equals(tradeDate))
            .findFirst()
            .orElse(connDbForData && getAccount().getId() > 0
                ? tradeDetailDao.getByAccountIdAndDate(getAccount().getId(), tradeDate)
                : null);
        return Optional.ofNullable(tradeDetail);
    }

    /**
     * 获取某日之前的一个TradeDetail, 不一定是前一交易日的, 异常状态不过滤
     * 
     * <ul>
     * <li>若在account的tradeDetailList中找到, 则获取第一个值返回, 此时<b>默认列表中不会有重复日期</b>
     * <li>若在account的tradeDetailList中没找到且connDbForData为true, 则尝试从数据库中获取, 若数据库中也为空, 则返回Optional.EMPTY
     * <li>若在account的tradeDetailList中没找到且connDbForData为false, 则返回Optional.EMPTY
     * </ul>
     * 
     * @param date 日期
     * @return 包含TradeDetail的Optional
     */
    public Optional<TradeDetail> getPrevTradeDetail(LocalDate date) {
        TradeDetail prevDetail = getAccount().getTradeDetailList().parallelStream()
            .filter(td -> td.getDate().isBefore(date))
            .max(Comparators.HISTORICAL_DATE_COMPARATOR)
            .orElse(null);
        if (prevDetail == null && connDbForData && getAccount().getId() > 0) {
            prevDetail = tradeDetailDao.getPrevTradeDetailByAccountIdAndDate(getAccount().getId(), date);
        }
        return Optional.ofNullable(prevDetail);
    }

    /**
     * 使用{@link #getTradeDetail(LocalDate date)}获取某日的TradeDetail, 不存在则返回null
     */
    public TradeDetail getTradeDetailOrNull(LocalDate tradeDate) {
        return getTradeDetail(tradeDate).orElse(null);
    }

    /**
     * 使用{@link #getPrevTradeDetail(LocalDate date)}获取获取某日之前的一个持仓交易细节,
     * 获取不到时返回模板中的默认TradeDetail, 其中date为null
     */
    public TradeDetail getPrevTradeDetailOrDefault(LocalDate date) {
        return getPrevTradeDetail(date)
            .orElse(tradeDetailTemplate.defaultTradeDetail(null));
    }

    /**
     * 使用{@link #getPrevAssetHistory(LocalDate)}获取某日前一交易日的持仓资产历史,
     * 获取不到时返回模板中的默认AssetHistory, 其中date为null
     */
    public AssetHistory getPrevAssetHistoryOrDefault(LocalDate date) {
        return getPrevAssetHistory(date)
            .orElse(assetHistoryTemplate.defaultAssetHistory(null));
    }

    /**
     * 产生现金流TreeMap, 用于计算xirr, TreeMap保证时间顺序
     * 
     * @param tradeDetailList 交易细节列表
     * @param lastTradeDate 最后一个交易日
     * @param lastAsset 最后一个交易日对应的资产市值
     * @return 现金流TreeMap
     */
    public TreeMap<LocalDate, Double> getCashFlows() {
        // 买入时tradeYuan为正, 现金流为负, 卖出时相反
        TreeMap<LocalDate, Double> cashFlows = getAccount().getTradeDetailList().parallelStream()
            .filter(Predicates::nonZeroValidTradeDetail)
            .collect(Collectors
                .toMap(TradeDetail::getDate, td -> -td.getTradeYuan(), (td1, td2) -> td1 + td2, TreeMap::new));
        // 最终市值(正值)放入cashFlows, 加1日是避免覆盖最后一个tradeDetail(误差有多少?)
        getLatestAssetHistory().ifPresent(lah -> cashFlows.put(lah.getDate().plusDays(1), lah.getPosAsset()));
        return cashFlows;
    }

    /**
     * 计算xirr年化收益百分比
     * 
     * @return 年化收益百分比
     * @throws OverMaxIterationNumberException 超过最大迭代次数
     * @throws EmptyInputException assetHistoryList为空
     */
    public double calcXirrPct() throws OverMaxIterationNumberException {
        TreeMap<LocalDate, Double> cashFlows = getCashFlows();
        logger.info(ToStringUtils.unfoldMapIfNeeded("CashFlow", cashFlows, true).toString()); // TODO
        double xirr = FinanceUtils.xirr(cashFlows);
        return MathUtils.decimalToPct(xirr);
    }

    /**
     * 计算最大回撤百分比
     * 
     * @return
     */
    public Drawdown calcMaxDrawdownPct() {
        TreeMap<LocalDate, Double> posAssetHistories = getAccount().getAssetHistoryList().parallelStream()
            .collect(Collectors
                .toMap(AssetHistory::getDate, AssetHistory::getPosAsset, (pa1, pa2) -> pa1 + pa2, TreeMap::new));
//        logger.info(ToStringUtils.unfoldMapIfNeeded("posAssetHistories", posAssetHistories, true).toString()); // TODO
        return FinanceUtils.maxDrawdownPct(posAssetHistories);
    }

    /**
     * 重置资产历史和交易细节为空列表, 其余属性不改变
     * 
     * @return
     */
    public Account resetAccountHistory() {
        getAccount().setAssetHistoryList(new ArrayList<>());
        getAccount().setTradeDetailList(new ArrayList<>());
        return getAccount();
    }

    @Override
    public Account cleanCopy() {
        return isEntityNull() ? null : accountTemplate.of(getAccount().getOwnerType());
    }

    public Account getAccount() {
        return getEntity();
    }

    public boolean isConnDbForData() {
        return connDbForData;
    }

}
