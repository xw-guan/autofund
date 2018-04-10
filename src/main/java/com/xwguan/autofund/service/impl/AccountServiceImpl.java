package com.xwguan.autofund.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xwguan.autofund.dao.account.AccountDao;
import com.xwguan.autofund.dao.account.AssetHistoryDao;
import com.xwguan.autofund.dao.account.TradeDetailDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.HistoryScopeEnum;
import com.xwguan.autofund.service.api.AccountService;
import com.xwguan.autofund.service.template.account.AccountTemplate;
import com.xwguan.autofund.service.util.Filters;
import com.xwguan.autofund.service.util.Predicates;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AssetHistoryDao assetHistoryDao;

    @Autowired
    private TradeDetailDao tradeDetailDao;
    
    @Autowired
    private AccountTemplate accountTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Account getAccount(Long id, HistoryScopeEnum historyScope, Page page) {
        if (id == null || id <= 0) {
            logger.debug("Empty account or invalid id");
            return null;
        }
        return accountDao.getById(id, historyScope, page);
    }

    @Override
    public Account getAccount(Long ownerId, AccountOwnerTypeEnum ownerType, HistoryScopeEnum historyScope, Page page) {
        if (ownerId == null || ownerId <= 0 || ownerType == null) {
            logger.debug("Empty account or invalid id or empty ownerType");
            return null;
        }
        return accountDao.getByOwnerIdAndType(ownerId, ownerType, historyScope, page);
    }

    @Override
    public Account getAccount(Long id, LocalDate fromDate, LocalDate toDate, Page page) {
        if (id == null || id <= 0) {
            logger.debug("Empty account or invalid id");
            return null;
        }
        if (fromDate == null) {
            fromDate = LocalDate.of(1970, 1, 1);
        }
        if (toDate == null) {
            toDate = LocalDate.now();
        }
        return accountDao.getByDateRange(id, fromDate, toDate, page);
    }

    @Override
    public Account getTemplate(AccountOwnerTypeEnum ownerType) {
        if (ownerType == null) {
            return null;
        }
        return accountTemplate.of(ownerType);
    }
    
    @Override
    public Account getTemplate(AccountOwnerTypeEnum ownerType, long ownerId) {
        if (ownerType == null) {
            return null;
        }
        return accountTemplate.of(ownerType, ownerId);
    }

    
    
    @Override
    public List<AssetHistory> listAssetHistory(Long accountId, Page page) {
        if (accountId == null || accountId <= 0) {
            return new ArrayList<>();
        }
        return assetHistoryDao.listByAccountId(accountId, page);
    }

    @Override
    public List<TradeDetail> listTradeDetail(Long accountId, Page page) {
        if (accountId == null || accountId <= 0) {
            return new ArrayList<>();
        }
        return tradeDetailDao.listByAccountId(accountId, page);
    }

    @Override
    @Transactional
    public int insertAccount(Account account) {
        if (account == null) {
            logger.debug("Empty account");
            return 0;
        }
        int cntInsertAccount = accountDao.insertAndSetId(account);
        Long accountId = account.getId();
        List<AssetHistory> assetHistoryList = account.getAssetHistoryList();
        if (CollectionUtils.isNotEmpty(assetHistoryList)) {
            assetHistoryList.stream().forEach(ah -> ah.setAccountId(accountId));
            insertAssetHistory(assetHistoryList);
        }
        List<TradeDetail> tradeDetailList = account.getTradeDetailList();
        if (CollectionUtils.isNotEmpty(tradeDetailList)) {
            assetHistoryList.stream().forEach(td -> td.setAccountId(accountId));
            insertTradeDetail(tradeDetailList);
        }
        logger.debug("cntInsertAccount = " + cntInsertAccount + "id = " + accountId);
        return cntInsertAccount;
    }

    @Override
    @Transactional
    public int insertAccount(List<Account> accountList) {
        if (CollectionUtils.isEmpty(accountList)) {
            logger.debug("Empty account");
            return 0;
        }
        int cntInsertAccount = accountDao.insertAndSetIdBatch(accountList);
        List<AssetHistory> ahToInsert = new ArrayList<>();
        List<TradeDetail> tdToInsert = new ArrayList<>();
        for (Account a : accountList) {
            Long accountId = a.getId();
            List<AssetHistory> assetHistoryList = a.getAssetHistoryList();
            if (CollectionUtils.isNotEmpty(assetHistoryList)) {
                assetHistoryList.stream().forEach(ah -> ah.setAccountId(accountId));
                ahToInsert.addAll(assetHistoryList);
            }
            List<TradeDetail> tradeDetailList = a.getTradeDetailList();
            if (CollectionUtils.isNotEmpty(tradeDetailList)) {
                assetHistoryList.stream().forEach(td -> td.setAccountId(accountId));
                tdToInsert.addAll(tradeDetailList);
            }
        }
        if (CollectionUtils.isNotEmpty(ahToInsert)) {
            insertAssetHistory(ahToInsert);
        }
        if (CollectionUtils.isEmpty(tdToInsert)) {
            insertTradeDetail(tdToInsert);
        }
        logger.debug("cntInsertAccount = " + cntInsertAccount);
        return cntInsertAccount;
    }

    @Override
    @Transactional
    public int deleteAccount(Long id) {
        if (id == null || id <= 0) {
            logger.debug("Empty account or invalid id");
            return 0;
        }
        int cntDelAccount = accountDao.deleteById(id);
        int cntDelAH = assetHistoryDao.deleteByAccountId(id);
        int cntDelTD = tradeDetailDao.deleteByAccountId(id);
        logger.debug("cntDelAccount = " + cntDelAccount);
        logger.debug("cntDelAH = " + cntDelAH);
        logger.debug("cntDelTD = " + cntDelTD);
        return cntDelAccount;
    }

    @Override
    @Transactional
    public int deleteAccount(Long ownerId, AccountOwnerTypeEnum ownerType) {
        if (ownerId == null || ownerId <= 0 || ownerType == null) {
            logger.debug("Empty owner info or invalid id");
            return 0;
        }
        Long id = accountDao.getIdByOwnerIdAndType(ownerId, ownerType);
        return deleteAccount(id);
    }

    @Override
    @Transactional
    public int deleteAccount(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            logger.debug("Empty account");
            return 0;
        }
        List<Long> filteredIds = Filters.filterValidId(idList);
        if (CollectionUtils.isEmpty(filteredIds)) {
            logger.debug("Invalid id");
            return 0;
        }
        int cntDelAccount = accountDao.deleteByIdListBatch(filteredIds);
        logger.debug("cntDelAccount = " + cntDelAccount);
        // filteredIds.stream()
        // .map(id -> assetHistoryDao.deleteByAccountId(id))
        // .forEach(cntDelAH -> logger.debug("cntDelAH = " + cntDelAH));
        for (Long id : filteredIds) {
            int cntDelAH = assetHistoryDao.deleteByAccountId(id);
            int cntDelTD = tradeDetailDao.deleteByAccountId(id);
            logger.debug("cntDelAH = " + cntDelAH);
            logger.debug("cntDelTD = " + cntDelTD);
        }
        return cntDelAccount;
    }

    @Override
    @Transactional
    public int insertAssetHistory(AssetHistory assetHistory) {
        if (assetHistory == null) {
            logger.debug("Empty assetHistory");
            return 0;
        }
        int cntInsAH = assetHistoryDao.insertAndSetId(assetHistory);
        logger.debug("cntInsAH = " + cntInsAH);
        return cntInsAH;
    }

    @Override
    @Transactional
    public int insertAssetHistory(List<AssetHistory> assetHistoryList) {
        if (CollectionUtils.isEmpty(assetHistoryList)) {
            logger.debug("Empty assetHistory");
            return 0;
        }
        int cntInsAH = assetHistoryDao.insertAndSetIdBatch(assetHistoryList);
        logger.debug("cntInsAH = " + cntInsAH);
        return cntInsAH;
    }

    @Override
    @Transactional
    public int insertTradeDetail(TradeDetail tradeDetail) {
        if (tradeDetail == null) {
            logger.debug("Empty tradeDetail");
            return 0;
        }
        int cntInsTD = tradeDetailDao.insertAndSetId(tradeDetail);
        logger.debug("cntInsTD = " + cntInsTD);
        return cntInsTD;
    }

    @Override
    @Transactional
    public int insertTradeDetail(List<TradeDetail> tradeDetailList) {
        if (CollectionUtils.isEmpty(tradeDetailList)) {
            logger.debug("Empty tradeDetail");
            return 0;
        }
        int cntInsTD = tradeDetailDao.insertAndSetIdBatch(tradeDetailList);
        logger.debug("cntInsTD = " + cntInsTD);
        return cntInsTD;
    }

    @Override
    @Transactional
    public int updateAssetHistory(AssetHistory assetHistory) {
        if (assetHistory == null) {
            logger.debug("Empty assetHistory");
            return 0;
        }
        if (assetHistory.getId() == null || assetHistory.getId() <= 0) {
            logger.debug("Invalid id");
            return 0;
        }
        int cntReplaceAH = assetHistoryDao.update(assetHistory);
        logger.debug("cntReplaceAH = " + cntReplaceAH);
        return cntReplaceAH;
    }

    @Override
    @Transactional
    public int updateAssetHistory(List<AssetHistory> assetHistoryList) {
        if (CollectionUtils.isEmpty(assetHistoryList)) {
            logger.debug("Empty list");
            return 0;
        }
        List<AssetHistory> filteredAssetHistories = assetHistoryList.parallelStream()
            .filter(Objects::nonNull)
            .filter(ah -> ah.getId() != null && ah.getId() > 0)
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filteredAssetHistories)) {
            logger.debug("Invalid id");
            return 0;
        }
        int cntReplaceAH = assetHistoryDao.updateOrInsertBatch(filteredAssetHistories);
        logger.debug("cntReplaceAH = " + cntReplaceAH);
        return cntReplaceAH;
    }

    @Override
    @Transactional
    public int updateTradeDetail(TradeDetail tradeDetail) {
        if (tradeDetail == null) {
            logger.debug("Empty tradeDetail");
            return 0;
        }
        if (tradeDetail.getId() == null || tradeDetail.getId() <= 0) {
            logger.debug("Invalid id");
            return 0;
        }
        int cntReplaceAH = tradeDetailDao.update(tradeDetail);
        logger.debug("cntReplaceAH = " + cntReplaceAH);
        return cntReplaceAH;
    }

    @Override
    @Transactional
    public int updateTradeDetail(List<TradeDetail> tradeDetailList) {
        if (CollectionUtils.isEmpty(tradeDetailList)) {
            logger.debug("Empty list");
            return 0;
        }
        List<TradeDetail> filteredTradedDetails = tradeDetailList.parallelStream()
            .filter(Objects::nonNull)
            .filter(td -> td.getId() != null && td.getId() > 0)
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filteredTradedDetails)) {
            logger.debug("Invalid id");
            return 0;
        }
        int cntReplaceTD = tradeDetailDao.updateOrInsertBatch(filteredTradedDetails);
        logger.debug("cntReplaceTD = " + cntReplaceTD);
        return cntReplaceTD;
    }

    @Override
    public boolean hasAsset(Long ownerId, AccountOwnerTypeEnum ownerType) {
        return Optional.ofNullable(getAccount(ownerId, ownerType, HistoryScopeEnum.LATEST, null))
            .map(Account::getAssetHistoryList)
            .filter(list -> !list.isEmpty())
            .map(ahl -> ahl.get(0))
            .map(AssetHistory::getPosAsset)
            .map(Predicates::greaterThanZero)
            .orElse(false);
    }

    @Override
    public boolean notHasAsset(Long ownerId, AccountOwnerTypeEnum ownerType) {
        return !hasAsset(ownerId, ownerType);
    }

}
