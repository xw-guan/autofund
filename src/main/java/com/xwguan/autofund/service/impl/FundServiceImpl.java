package com.xwguan.autofund.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xwguan.autofund.dao.common.FundAndStockCUDBatchDao;
import com.xwguan.autofund.dao.fund.AssetAllocationDao;
import com.xwguan.autofund.dao.fund.FundCompanyDao;
import com.xwguan.autofund.dao.fund.FundDao;
import com.xwguan.autofund.dao.fund.FundHistoryDao;
import com.xwguan.autofund.dao.fund.FundInfoDao;
import com.xwguan.autofund.dao.fund.FundManagerDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.fund.AssetAllocation;
import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.entity.fund.FundCompany;
import com.xwguan.autofund.entity.fund.FundHistory;
import com.xwguan.autofund.entity.fund.FundManager;
import com.xwguan.autofund.entity.fund.RealTimeFundData;
import com.xwguan.autofund.enums.UpdateStateEnum;
import com.xwguan.autofund.exception.FailInitializationException;
import com.xwguan.autofund.exception.io.AbnormalDataSourceException;
import com.xwguan.autofund.exception.io.EmptyResultException;
import com.xwguan.autofund.exception.io.InvalidParamException;
import com.xwguan.autofund.manager.api.FundDataManager;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.service.api.FundService;
import com.xwguan.autofund.service.util.Comparators;
import com.xwguan.autofund.service.util.FieldChecker;
import com.xwguan.autofund.service.util.FieldCheckers;

@Service
public class FundServiceImpl implements FundService {

    @Autowired
    private FundDao fundDao;
    @Autowired
    private FundInfoDao fundInfoDao;
    @Autowired
    private FundHistoryDao fundHistoryDao;
    @Autowired
    private AssetAllocationDao assetAllocationDao;
    @Autowired
    private FundCompanyDao fundCompanyDao;
    @Autowired
    private FundManagerDao fundManagerDao;
    @Autowired
    private FundDataManager fundDataManager;
    @Autowired
    private DateTimeService dateTimeService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Fund> searchFund(String searchField, Page page) {
        if (StringUtils.isEmpty(searchField)) {
            return null;
        }
        if (page == null) {
            page = new Page();
        }
        List<Fund> fundList = fundDao.listFundBySearchField(searchField, false, true, page);
        return fundList;
    }

    @Override
    public Fund getFund(Integer id) {
        return getFund(id, false, false, null);
    }

    @Override
    public Fund getFund(Integer id, Boolean includeFundInfo, Boolean includeLatestHistory, Page page) {
        if (id == null) {
            return null;
        }
        Fund fund = fundDao.getFundById(id, includeFundInfo, includeLatestHistory, page);
        return fund;
    }

    @Override
    public Fund getFund(String code, Boolean includeFundInfo, Boolean includeLatestHistory, Page page) {
        if (code == null) {
            return null;
        }
        Fund fund = fundDao.getFundByCode(code, includeFundInfo, includeLatestHistory, page);
        return fund;
    }

    @Override
    public List<Fund> listFundByType(String type, Boolean includeFundInfo, Boolean includeLatestHistory, Page page) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        if (page == null) {
            page = new Page();
        }
        List<Fund> fundList = fundDao.listFundByType(type, includeFundInfo, includeLatestHistory, page);
        return fundList;
    }

    @Override
    public AssetAllocation getAssetAllocationByFundId(Integer fundId) throws InvalidParamException {
        if (fundId == null) {
            throw new InvalidParamException("fundId is not allowed to be null");
        }
        AssetAllocation assetAllocation = assetAllocationDao.getAssetAllocationByFundId(fundId);
        return assetAllocation;
    }

    @Override
    public RealTimeFundData getRealTimeFundData(Integer fundId) throws IOException {
        String fundCode = fundDao.getCodeById(fundId);
        if (fundCode == null) {
            return null;
        }
        RealTimeFundData realTimeFundData = null;
        try {
            realTimeFundData = fundDataManager.getRealTimeFundData(fundCode);
            realTimeFundData.setFundId(fundId);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            try {
                Thread.sleep(1000);
                realTimeFundData = fundDataManager.getRealTimeFundData(fundCode);
                realTimeFundData.setFundId(fundId);
            } catch (InterruptedException | ExecutionException | TimeoutException e1) {
                logger.error(e.getMessage() + "\n" + e.getStackTrace());
            }
        }
        return realTimeFundData;
    }

    @Override
    public int countFund() {
        return fundDao.countFund();
    }

    @Override
    public int countUpdateRequired() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        return (int) listFundsAndInitIfNeed().parallelStream().filter(f -> {
            LocalDate latestFHDate = fundHistoryDao.getLatestDate(f.getId());
            return !dateTimeService.isDataNewest(latestFHDate);
        }).count();
    }

    @Override
    @Transactional
    public synchronized UpdateStateEnum updateFund()
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        logger.info("Updating funds, please wait...");
        List<Fund> oldList = fundDao.listAllFund(false, false);
        List<Fund> newList = fundDataManager.listFund();
        Map<String, List<Fund>> updateListsMap = getUpdateListsFromOldAndNew(oldList, newList,
            Comparators.FUND_CODE_COMPARATOR, FieldCheckers.FUND_BASIC_INFO_CHECKER);
        UpdateStateEnum updateState = executeUpdate(fundDao, updateListsMap);
        logger.debug("===== UpdateState of updateFundTable: {} ====", updateState);
        return updateState;
    }

    @Override
    @Transactional
    public synchronized UpdateStateEnum updateFundCompany()
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        List<FundCompany> oldList = fundCompanyDao.listAllFundCompany(null);
        List<FundCompany> newList = fundDataManager.listFundCompany();
        Map<String, List<FundCompany>> updateListsMap = getUpdateListsFromOldAndNew(oldList, newList,
            Comparators.FUND_COMPANY_CODE_COMPARATOR, FieldCheckers.FUND_COMPANY_BASIC_INFO_CHECKER);
        UpdateStateEnum updateState = executeUpdate(fundCompanyDao, updateListsMap);
        logger.debug("==== UpdateState of updateFundCompanyTable: {} ====", updateState);
        return updateState;
    }

    @Override
    @Transactional
    public synchronized UpdateStateEnum updateFundManager() throws MalformedURLException, UnsupportedEncodingException,
        IOException, InterruptedException, ExecutionException, TimeoutException {
        List<FundManager> oldList = fundManagerDao.listAllFundManager(null);
        List<FundManager> newList = fundDataManager.listFundManager();
        Map<String, List<FundManager>> updateListsMap = getUpdateListsFromOldAndNew(oldList, newList,
            Comparators.FUND_MANAGER_CODE_COMPARATOR, FieldCheckers.FUND_MANAGER_BASIC_INFO_CHECKER);
        UpdateStateEnum updateState = executeUpdate(fundManagerDao, updateListsMap);
        logger.debug("===== UpdateState of updateFundManagerTable: {} ====", updateState);
        return updateState;
    }

    @Override
    @Transactional
    public synchronized UpdateStateEnum updateAssetAllocation() throws AbnormalDataSourceException, InvalidParamException {
        List<AssetAllocation> oldList = assetAllocationDao.listAllAssetAllocation();
        List<AssetAllocation> newList = new ArrayList<>();
        List<Fund> fundList = fundDao.listAllFund(false, false);
        for (Fund fund : fundList) {
            try {
                AssetAllocation assetAllocation = fundDataManager.getAssetAllocation(fund.getCode());
                assetAllocation.setFundId(fund.getId());
                newList.add(assetAllocation);
            } catch (FileNotFoundException e) {
                logger.debug("Cannot find data of fund code: {}, from: {}", fund.getCode(), e.getMessage());
            } catch (EmptyResultException e) {
                logger.debug("Empty result of fund code: {}", fund.getCode());
            } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
                logger.error("{}", e.getStackTrace().toString());
            }
        }
        Map<String, List<AssetAllocation>> updateListsMap = getUpdateListsFromOldAndNew(oldList, newList,
            Comparators.ASSET_ALLOCATION_FUND_ID_COMPARATOR, FieldCheckers.ASSET_ALLOCATION_DATE_CHECKER);
        UpdateStateEnum updateState = executeUpdate(assetAllocationDao, updateListsMap);
        // TODO fund position
        // UpdateStateEnum updateState = executeUpdate(FundPositionDao, updateListsMap);
        logger.debug("===== map: {} ====", updateListsMap);
        logger.debug("===== UpdateState of updateFundManagerTable: {} ====", updateState);
        return updateState;
    }

    @Override
    public synchronized Map<Integer, UpdateStateEnum> updateFundHistory(Boolean useMultiThread)
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        logger.info(
            "Updating stockDatas, please wait. It may take a while. If the process is unexpected terminated, try to update again");
        List<Fund> fundList = listFundsAndInitIfNeed();
        // Set<String> incompleteData = new TreeSet<>();
        Map<Integer, UpdateStateEnum> updateStateMap = new HashMap<>();
        boolean useMultiThreadUodate = useMultiThread != null && useMultiThread;
        Stream<Fund> funds = useMultiThreadUodate ? fundList.parallelStream() : fundList.stream();
        funds.forEach(fund -> {
            UpdateStateEnum updateState = updateFundHistory(fund);
            if (updateState != null) {
                updateStateMap.put(fund.getId(), updateState);
            }
        });
        return updateStateMap;
    }

    @Transactional
    private synchronized UpdateStateEnum updateFundHistory(Fund fund) {
        logger.debug("Updating fund: {}", fund.getCode());
        // TODO 因数据结构不同, 暂不处理理财型和货币型
        if ("理财型".equals(fund.getType()) || "货币型".equals(fund.getType())) {
            return null;
        }
        LocalDate latestDate = fundHistoryDao.getLatestDate(fund.getId());
        // TODO fundList不从数据库中取出全部, 而是取出日期不等于当天的
        if (dateTimeService.isDataNewest(latestDate)) {
            return UpdateStateEnum.ALREADY_NEWEST;
        }
        try {
            List<FundHistory> fundHistoryList = fundDataManager
                .getFundHistory(fund.getCode(), latestDate, LocalDate.now()); // TODO 下一天开始?
            if (fundHistoryList.isEmpty()) {
                return UpdateStateEnum.SUCCESS; // 该基金处于募集阶段或还没有公布净值
            }
            fundHistoryList.parallelStream().forEach(fh -> fh.setFundId(fund.getId()));
            long cntSuccessInsert = fundHistoryDao.insertFundHistory(fundHistoryList);
            return cntSuccessInsert > 0
                ? UpdateStateEnum.SUCCESS
                : UpdateStateEnum.FAIL_TO_UPDATE_DB;

        } catch (FileNotFoundException e) {
            logger.warn("Cannot find data of fund code: {}, from: {}", fund.getCode(), e.getMessage());
            return UpdateStateEnum.ABNORMAL_DATA_SOURCE;
        } catch (EmptyResultException e) {
            logger.error("Empty result of fund code: {}", fund.getCode());
            return UpdateStateEnum.ABNORMAL_DATA_SOURCE;
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("{}", e.getStackTrace().toString());
            return UpdateStateEnum.ABNORMAL_DATA_SOURCE;
        }
    }

    @Override
    @Transactional
    public int deleteFund(List<Integer> idList) throws IOException {
        int successDelete = fundDao.deleteFundBatchById(idList);
        fundHistoryDao.deleteFundHistoryBatchByFundId(idList);
        fundInfoDao.deleteFundInfoBatchByFundId(idList);
        assetAllocationDao.deleteAssetAllocationBatchByFundId(idList);
        return successDelete;
    }

    /**
     * 根据oldList和newList获取数据更新需要增, 删, 改的对象列表, 放入Map中返回, key分别为"toInsert", "toUpdate", "toDelete"
     * 
     * @param <T> 与数据库表对应的实体类对象
     * @param oldList 由数据库获取的旧对象列表
     * @param newList 由网络获取的新对象列表
     * @param comparator 比较器, 用于对象T的排序和比较, 不能为null
     * @param checker 判断新旧T对象的选定字段是否相同, 进而判断是否需要更新. checher为null时, 所有comparator.compare()返回值为0的都更新
     * @return 需要增, 删, 改的Fund对象列表, 放入Map中返回, key分别为"toInsert", "toUpdate", "toDelete"
     * @throws AbnormalDataSourceException newList为空
     * @throws InvalidParamException fund为null
     */
    @SuppressWarnings("unchecked")
    private <T> Map<String, List<T>> getUpdateListsFromOldAndNew(List<T> oldList, List<T> newList,
        Comparator<T> comparator, FieldChecker<T> checker)
        throws AbnormalDataSourceException, InvalidParamException {
        if (comparator == null) {
            throw new InvalidParamException("The comparator is not allowed to be null");
        }
        if (CollectionUtils.isEmpty(newList)) {
            throw new AbnormalDataSourceException("The newList got from internet should not be empty");
        }
        if (checker == null) {
            checker = (FieldChecker<T>) FieldCheckers.ALWAYS_FALSE;
        }
        Map<String, List<T>> map = new HashMap<>();
        List<T> toInsert = new ArrayList<>();
        List<T> toUpdate = new ArrayList<>();
        List<T> toDelete = new ArrayList<>();
        map.put("toInsert", toInsert);
        map.put("toUpdate", toUpdate);
        map.put("toDelete", toDelete);
        if (CollectionUtils.isEmpty(oldList)) {
            // 数据表尚未初始化, 所有数据都插入
            map.put("toInsert", newList);
            return map;
        }
        // 将oldList和newList按照传入的比较器进行排序
        oldList.sort(comparator);
        newList.sort(comparator);
        Iterator<T> oldIter = oldList.iterator();
        Iterator<T> newIter = newList.iterator();
        // 此时两表都不为空, 一定有首个元素
        T oldObj = oldIter.next();
        T newObj = newIter.next();
        do {
            int compareNewToOld = comparator.compare(newObj, oldObj);
            if (compareNewToOld < 0) {
                // newObj小于oldObj, 说明new在oldList中不存在, 需要被插入
                toInsert.add(newObj);
                if (!newIter.hasNext()) {
                    // newIter没有下一个元素, 则当前oldObj和oldList中剩余的对象都应被删除
                    toDelete.add(oldObj);
                    CollectionUtils.addAll(toDelete, oldIter);
                    return map;
                }
                newObj = newIter.next();
            } else if (compareNewToOld > 0) {
                // newObj大于oldObj, 说明oldObj在newList中不存在, 需要被删除
                toDelete.add(oldObj);
                if (!oldIter.hasNext()) {
                    // oldIter没有下一个元素, 则当前newObj和newList中剩余的对象都应被插入
                    toInsert.add(newObj);
                    CollectionUtils.addAll(toInsert, newIter);
                    return map;
                }
                oldObj = oldIter.next();
            } else {
                // 新旧对象相等表明是同一个, 此时根据FieldChecker判断是否需要更新, isSelectedFieldsEqual返回false需要更新, 否则不需要
                if (!checker.isSelectedFieldsEqual(newObj, oldObj)) {
                    toUpdate.add(newObj);
                }
                // oldIter没有下一个元素, 而newIter还有, 则newList中剩余的对象都是oldList没有的, 都应被插入
                if (!oldIter.hasNext()) {
                    CollectionUtils.addAll(toInsert, newIter);
                    return map;
                }
                // newIter没有下一个元素, 而oldIter还有, 则oldist中剩余的对象都应被删除
                if (!newIter.hasNext()) {
                    CollectionUtils.addAll(toDelete, oldIter);
                    return map;
                }
                // 此时oldIter.next()和newIter.next()必有元素
                oldObj = oldIter.next();
                newObj = newIter.next();
            }
        } while (true);
    }

    /**
     * 从传入的Map中获取要插入, 更新, 删除的T对象列表(key分别为"toInsert", "toUpdate", "toDelete"), 并对数据库中执行批量操作
     * 
     * @param <T> 与数据库表对应的实体类对象
     * @param dao 包含批量执行插入, 更新, 删除操作的dao
     * @param updateListsMap key分别为"toInsert", "toUpdate", "toDelete", value分别为要插入, 更新, 删除的T对象列表
     * @return 更新状态, UpdateStateEnum.SUCCESS: 成功, UpdateStateEnum.ALREADY_NEWEST: 已是最新, 无需更新,
     *         UpdateStateEnum.FAIL_TO_UPDATE_DB: 更新数据库失败(实际上更新数据库发生问题时会抛出异常, 因此应该不会返回此状态)
     */
    @Transactional
    private <T> UpdateStateEnum executeUpdate(FundAndStockCUDBatchDao<T> dao, Map<String, List<T>> updateListsMap) {
        List<T> toInsert = updateListsMap.get("toInsert");
        List<T> toUpdate = updateListsMap.get("toUpdate");
        List<T> toDelete = updateListsMap.get("toDelete");
        logger.debug("Size of toInsert: {}, toUpdate: {}, toDelete: {}", toInsert.size(), toUpdate.size(),
            toDelete.size());
        // 三个表都为空表示新旧信息相同, 不需要更新
        boolean needNotUpdate = CollectionUtils.isEmpty(toInsert) && CollectionUtils.isEmpty(toUpdate)
            && CollectionUtils.isEmpty(toDelete);
        if (needNotUpdate) {
            return UpdateStateEnum.ALREADY_NEWEST;
        }
        int insertSuccess = 0;
        if (CollectionUtils.isNotEmpty(toInsert)) {
            insertSuccess = dao.insertAndSetIdBatch(toInsert);
        }
        int updateSuccess = 0;
        if (CollectionUtils.isNotEmpty(toUpdate)) {
            updateSuccess = dao.updateBatch(toUpdate) / 2;
        }
        int deleteSuccess = 0;
        if (CollectionUtils.isNotEmpty(toDelete)) {
            deleteSuccess = dao.deleteBatch(toDelete); // TODO 
        }
        logger.debug("Insert Success: {}, Update Success: {}, Delete Success: {}", insertSuccess, updateSuccess,
            deleteSuccess);
        boolean successUpdate = insertSuccess + updateSuccess + deleteSuccess > 0;
        if (successUpdate) {
            return UpdateStateEnum.SUCCESS;
        } else {
            return UpdateStateEnum.FAIL_TO_UPDATE_DB;
        }
    }

    /**
     * 列出stock表中的所有股票基本信息(id, symbol, name)
     * <p>若stock表中没有数据, 则调用updateStock()进行初始化后重新获取stock表内容
     * 
     * @return stock表中的所有股票基本信息
     * @throws IOException
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @exception FailInitializationException 初始化数据表失败, 可能是数据库相关原因
     */
    @Transactional
    private List<Fund> listFundsAndInitIfNeed()
        throws IOException, InterruptedException, ExecutionException, TimeoutException {
        List<Fund> fundList = fundDao.listAllFund(false, false);
        if (CollectionUtils.isEmpty(fundList)) {
            // oldStockList没有数据时, 即数据库中的stock表尚未初始化, 调用updateStock()进行初始化
            updateFund();
            fundList = fundDao.listAllFund(false, false);
        }
        return fundList;
    }

}
