package com.xwguan.autofund.dao.account;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.HistoryScopeEnum;

/**
 * 账户dao, 不提供批量修改方法
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-07
 */
public interface AccountDao extends BaseDao<Account>, CDBatchDao<Account> {

    // TODO 这种情况下getById(long id)仍然可用, 并且没有实现, 会报错

    Account getById(@Param("id") Long id, @Param("historyScope") HistoryScopeEnum historyScope,
        @Param("page") Page page);

    Account getByOwnerIdAndType(@Param("ownerId") Long ownerId, @Param("ownerType") AccountOwnerTypeEnum ownerType,
        @Param("historyScope") HistoryScopeEnum historyScope, @Param("page") Page page);

    /**
     * 资产历史和交易记录在日期范围内, 包含两端
     */
    Account getByDateRange(@Param("id") Long id, @Param("fromDate") LocalDate fromDate,
        @Param("toDate") LocalDate toDate, @Param("page") Page page);

    Long getIdByOwnerIdAndType(@Param("ownerId") Long ownerId, @Param("ownerType") AccountOwnerTypeEnum ownerType);

    int deleteByOwnerIdAndType(@Param("ownerId") Long ownerId, @Param("ownerType") AccountOwnerTypeEnum ownerType);

}
