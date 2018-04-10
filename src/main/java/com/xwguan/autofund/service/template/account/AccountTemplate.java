package com.xwguan.autofund.service.template.account;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;

/**
 * 账户模板
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-07
 */
@Component
public class AccountTemplate {

    /**
     * 默认账户, id, ownerId为-1, 各列表为空列表
     * 
     * @param ownerType 所有者类型
     * @return
     */
    public Account of(AccountOwnerTypeEnum ownerType) {
        return of(ownerType, -1L);
    }

    /**
     * 默认账户, id为-1, 各列表为空列表
     * 
     * @param ownerType 所有者类型
     * @param ownerId 所有者id
     * @return
     */
    public Account of(AccountOwnerTypeEnum ownerType, long ownerId) {
        return new Account(-1L, ownerId, ownerType, new ArrayList<>(), new ArrayList<>());
    }

}
