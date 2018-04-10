package com.xwguan.autofund.service.template.user;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.user.User;
import com.xwguan.autofund.entity.user.UserInfo;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.UserTypeEnum;
import com.xwguan.autofund.service.template.account.AccountTemplate;

/**
 * 用户模板
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-07
 */
@Component
public class UserTemplate {

    @Autowired
    private AccountTemplate accountTemplate;

    public User defaultUser() {
        String name = UUID.randomUUID().toString();
        // TODO userInfo
        User user = new User(-1, name, null, null, null, new UserInfo(), UserTypeEnum.NON_ACTIVATED, new ArrayList<>(),
            new ArrayList<>(), accountTemplate.of(AccountOwnerTypeEnum.USER));
        return user;
    }

}
