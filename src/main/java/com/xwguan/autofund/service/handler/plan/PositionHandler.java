package com.xwguan.autofund.service.handler.plan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.service.handler.AbstractEntityHandler;
import com.xwguan.autofund.service.handler.CleanlyCopyable;
import com.xwguan.autofund.service.handler.account.AccountHandler;
import com.xwguan.autofund.service.template.plan.PositionTemplate;

/**
 * 持仓处理者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-14
 */
@Component
@Scope("prototype")
public class PositionHandler extends AbstractEntityHandler<Position> implements CleanlyCopyable<Position> {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PositionTemplate template;

    @Autowired
    private AccountHandler accountHandler;

    /**
     * 在使用position中的List, 而List没有数据时, 是否尝试从数据库中获取, 默认为true, 目前只实现了Account
     */
    private boolean connDbForData = true;

    /**
     * 处理持仓, 持仓中的Account也被对应Handler处理
     */
    @Override
    public PositionHandler handle(Position position) {
        super.handle(position);
        accountHandler.handle(position.getAccount());
        return this;
    }

    public PositionHandler setConnDbForData(boolean connDbForData) {
        this.connDbForData = connDbForData;
        accountHandler.setConnDbForData(connDbForData);
        return this;
    }

    @Override
    public Position cleanCopy() {
        return isEntityNull() ? null : template.of(getPosition().getFundId(), getPosition().getRefIndexId());
    }

    public Position getPosition() {
        return getEntity();
    }

    public boolean isConnDbForData() {
        return connDbForData;
    }

}
