package com.xwguan.autofund.entity.plan.portfolio;

import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.plan.PositionHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 投资组合持仓
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-07
 */
public class Position implements Handleable {

    /**
     * 持仓id
     */
    private Long id;

    /**
     * 从属的计划id
     */
    private Long planId;

    /**
     * 持仓基金id
     */
    private Integer fundId;

    /**
     * 参考指数id
     */
    private Integer refIndexId;

    /**
     * 账户
     */
    private Account account;

    public Position() {
        super();
    }

    public Position(Long id, Long planId, Integer fundId, Integer refIndexId, Account account) {
        this.id = id;
        this.planId = planId;
        this.fundId = fundId;
        this.refIndexId = refIndexId;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public Integer getRefIndexId() {
        return refIndexId;
    }

    public void setRefIndexId(Integer refIndexId) {
        this.refIndexId = refIndexId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Position [id=" + id + ", planId=" + planId + ", fundId=" + fundId + ", refIndexId=" + refIndexId
            + ", account=" + account + "]";
    }

    @Override
    public Class<PositionHandler> handlerClass() {
        return PositionHandler.class;
    }

    @Override
    public PositionHandler handler() {
        return IocUtils.getBean(handlerClass()).handle(this);
    }

}
