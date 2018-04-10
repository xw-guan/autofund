package com.xwguan.autofund.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xwguan.autofund.dao.plan.PositionDao;
import com.xwguan.autofund.dao.stock.StockDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.exception.io.InvalidFundCodeException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;
import com.xwguan.autofund.service.api.AccountService;
import com.xwguan.autofund.service.api.PositionService;
import com.xwguan.autofund.service.template.plan.PositionTemplate;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PositionTemplate positionTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Position getPositionById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return Optional.ofNullable(positionDao.getById(id))
            .filter(pst -> accountService.hasAsset(pst.getId(), AccountOwnerTypeEnum.POSITION))
            .orElse(null);
    }

    @Override
    public List<Position> listByPlanId(Long planId, Boolean includeLatestAccountHistory, Page page) {
        if (planId == null || planId <= 0) {
            return null;
        }
        return positionDao.listByPlanId(planId, includeLatestAccountHistory, page).stream()
            .filter(pst -> accountService.hasAsset(pst.getId(), AccountOwnerTypeEnum.POSITION))
            .collect(Collectors.toList());
    }

    @Override
    public Position getPositionTemplate(String fundCode, String refIndexSymbol)
        throws InvalidFundCodeException, InvalidTickerSymbolException {
        return positionTemplate.of(fundCode, refIndexSymbol);
    }

    @Override
    @Transactional
    public int insertPosition(Position position) {
        if (position == null) {
            logger.debug("Empty position");
            return 0;
        }
        int cntInsPosition = positionDao.insertAndSetId(position);
        logger.debug("cntInsPosition = " + cntInsPosition);
        Long positionId = position.getId();
        Account account = position.getAccount();
        if (account != null) {
            account.setOwnerId(positionId);
            int cntInsAccount = accountService.insertAccount(account);
            logger.debug("cntInsAccount = " + cntInsAccount);
        }
        return cntInsPosition;
    }

    // TODO SET ID OF INSERT HERE

    @Override
    @Transactional
    public int insertPosition(List<Position> positionList) {
        if (CollectionUtils.isEmpty(positionList)) {
            logger.debug("Empty positionList");
            return 0;
        }
        int cntInsPosition = positionDao.insertAndSetIdBatch(positionList);
        List<Account> accountToInsert = new ArrayList<>();
        for (Position pst : positionList) {
            Long positionId = pst.getId();
            Account account = pst.getAccount();
            if (account != null) {
                account.setOwnerId(positionId);
                accountToInsert.add(account);
            }
        }
        accountService.insertAccount(accountToInsert);
        logger.debug("cntInsPosition = " + cntInsPosition);
        return cntInsPosition;
    }

    // @Override
    // @Transactional
    // public int deletePosition(Long id) {
    // if (id == null || id <= 0) {
    // return 0;
    // }
    // int cntDelPosition = positionDao.deleteById(id);
    // int cntDelAccount = accountService.deleteAccount(id, AccountOwnerTypeEnum.POSITION);
    // logger.debug("cntDelPosition = " + cntDelPosition);
    // logger.debug("cntDelAccount = " + cntDelAccount);
    // return cntDelPosition;
    // }

    // @Override
    // @Transactional
    // public int deletePosition(List<Long> idList) {
    // if (CollectionUtils.isEmpty(idList)) {
    // return 0;
    // }
    // List<Long> filteredIds = Filters.filterValidId(idList);
    // if (CollectionUtils.isEmpty(filteredIds)) {
    // return 0;
    // }
    // int cntDelPosition = positionDao.deleteByIdListBatch(filteredIds);
    // for (Long id : filteredIds) {
    // int cntDelAccount = accountService.deleteAccount(id, AccountOwnerTypeEnum.POSITION);
    // logger.debug("cntDelAccount = " + cntDelAccount);
    // }
    // logger.debug("cntDelPosition = " + cntDelPosition);
    // return cntDelPosition;
    // }

    @Override
    @Transactional
    public int deletePositionOfPlan(Long planId) {
        if (planId == null || planId <= 0) {
            return 0;
        }
        positionDao.listIdByPlanId(planId).stream()
            .forEach(id -> accountService.deleteAccount(id, AccountOwnerTypeEnum.POSITION));
        int cntDelPosition = positionDao.deleteByPlanId(planId);
        logger.debug("cntDelPosition = " + cntDelPosition);
        return cntDelPosition;
    }

    @Override
    @Transactional
    public int changeRefIndex(Long id, String symbol) throws InvalidTickerSymbolException {
        if (id == null || id <= 0 || symbol == null) {
            return 0;
        }
        Integer refIndexId = stockDao.getIdBySymbol(symbol);
        if (refIndexId == null) {
            throw new InvalidTickerSymbolException("Invalid symbol(" + symbol + ")");
        }
        int cntUpdate = positionDao.updateRefIndexId(id, refIndexId);
        return cntUpdate;
    }

}
