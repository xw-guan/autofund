package com.xwguan.autofund.service.handler.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.dao.account.AccountDao;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.HistoryScopeEnum;

/**
 * Position对象装配器, id大于0且对应属性为空时, 从数据库获取并装配到Plan中.
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-15
 */
@Component
public class PositionAssembler {

    @Autowired
    private AccountDao accountDao;

    public void assembleAccount(Position position, HistoryScopeEnum historyScope) {
        if (position.getId() > 0 && position.getAccount() == null) {
            position.setAccount(
                accountDao.getByOwnerIdAndType(
                    position.getId(), AccountOwnerTypeEnum.POSITION, historyScope, null));
        }
    }
}
