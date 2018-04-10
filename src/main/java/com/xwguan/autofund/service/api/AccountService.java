package com.xwguan.autofund.service.api;

import java.time.LocalDate;
import java.util.List;

import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.HistoryScopeEnum;

/**
 * 账户服务
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-16
 */
public interface AccountService {

    /**
     * 获取Account, 账户历史根据HistoryScopeEnum的值给出
     * <ul>
     * <li>值为FULL, 所有账户历史
     * <li>值为LATEST, 最新账户历史
     * <li>值为NONE或null, 空列表
     * </ul>
     * 
     * @param id
     * @param historyScope
     * @param page
     * @return
     */
    Account getAccount(Long id, HistoryScopeEnum historyScope, Page page);

    /**
     * 获取Account, 账户历史根据HistoryScopeEnum的值给出
     * <ul>
     * <li>值为FULL, 所有账户历史
     * <li>值为LATEST, 最新账户历史
     * <li>值为NONE或null, 空列表
     * </ul>
     * 
     * @param ownerId
     * @param ownerType
     * @param historyScope
     * @param page
     * @return
     */
    Account getAccount(Long ownerId, AccountOwnerTypeEnum ownerType, HistoryScopeEnum historyScope, Page page);

    /**
     * 获取Account, 账户历史根据日期范围给出
     * 
     * @param id
     * @param fromDate
     * @param toDate
     * @param page
     * @return
     */
    Account getAccount(Long id, LocalDate fromDate, LocalDate toDate, Page page);

    /**
     * 获取账户模板
     * 
     * @param ownerType
     * @return
     */
    Account getTemplate(AccountOwnerTypeEnum ownerType);

    /**
     * 获取账户模板
     * 
     * @param ownerType
     * @param ownerId
     * @return
     */
    Account getTemplate(AccountOwnerTypeEnum ownerType, long ownerId);
    
    /**
     * 列出属于accountId的资产历史
     * 
     * @param accountId
     * @param page
     * @return
     */
    List<AssetHistory> listAssetHistory(Long accountId, Page page);
    
    /**
     * 列出属于accountId的交易细节
     * 
     * @param accountId
     * @param page
     * @return
     */
    List<TradeDetail> listTradeDetail(Long accountId, Page page);

    /**
     * 插入账户, 若存在账户历史也插入
     * 
     * @param account
     * @return
     */
    int insertAccount(Account account);

    /**
     * 批量插入账户, 若存在账户历史也插入
     * 
     * @param accountList
     * @return
     */
    int insertAccount(List<Account> accountList);

    /**
     * 删除id对应的账户, 账户历史也删除
     * 
     * @param id
     * @return
     */
    int deleteAccount(Long id);

    /**
     * 删除ownerId和ownerType唯一确定的账户, 账户历史也删除
     * 
     * @param ownerId
     * @param ownerType
     * @return
     */
    int deleteAccount(Long ownerId, AccountOwnerTypeEnum ownerType);

    /**
     * 批量删除账户, 账户历史也删除
     * 
     * @param idList
     * @return
     */
    int deleteAccount(List<Long> idList);

    /**
     * 插入资产历史
     * 
     * @param assetHistory
     * @return
     */
    int insertAssetHistory(AssetHistory assetHistory);

    /**
     * 插入资产历史
     * 
     * @param assetHistoryList
     * @return
     */
    int insertAssetHistory(List<AssetHistory> assetHistoryList);

    /**
     * 插入交易细节
     * 
     * @param tradeDetail
     * @return
     */
    int insertTradeDetail(TradeDetail tradeDetail);

    /**
     * 插入交易细节
     * 
     * @param tradeDetailList
     * @return
     */
    int insertTradeDetail(List<TradeDetail> tradeDetailList);

    /**
     * 更新资产历史
     * 
     * @param assetHistory
     * @return 成功更新数量
     */
    int updateAssetHistory(AssetHistory assetHistory);

    /**
     * 更新资产历史, 存在则更新, 不存在则插入
     * 
     * @param assetHistoryList
     * @return 成功更新数量
     */
    int updateAssetHistory(List<AssetHistory> assetHistoryList);

    /**
     * 更新交易细节
     * 
     * @param tradeDetail
     * @return 成功更新数量
     */
    int updateTradeDetail(TradeDetail tradeDetail);

    /**
     * 更新交易细节, 存在则更新, 不存在则插入
     * 
     * @param tradeDetailList
     * @return 成功更新数量
     */
    int updateTradeDetail(List<TradeDetail> tradeDetailList);

    /**
     * ownerId和ownerType对应的账户内是否还有资产
     * 
     * @param ownerId
     * @param ownerType
     * @return
     */
    boolean hasAsset(Long ownerId, AccountOwnerTypeEnum ownerType);

    /**
     * ownerId和ownerType对应的账户内是否没有资产
     * 
     * @param ownerId
     * @param ownerType
     * @return
     */
    boolean notHasAsset(Long ownerId, AccountOwnerTypeEnum ownerType);

}
