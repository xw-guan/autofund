package com.xwguan.autofund.dao.account;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.account.AssetHistory;

/**
 * 资产历史dao
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-07
 */
public interface AssetHistoryDao extends BaseDao<AssetHistory>, CUDBatchDao<AssetHistory> {

    AssetHistory getByAccountIdAndDate(@Param("accountId") Long accountId, @Param("date") LocalDate date);

    AssetHistory getLatestByAccountId(@Param("accountId") Long accountId);

    List<AssetHistory> listByAccountId(@Param("accountId") Long accountId, @Param("page") Page page);
    
    int deleteByAccountId(@Param("accountId") Long accountId);
}
