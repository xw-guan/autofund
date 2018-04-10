package com.xwguan.autofund.entity.plan.rule;

import java.time.LocalDate;

import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.rule.SuppressConditionHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 抑制条件, 在lastActivatedDate和suppressBeforeDate之间的日期不满足条件
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-29
 */
@Unimplement // 当前Entity设计有问题, SuppressCondition的lastActivatedDate是与Position状态相关的, 目前只放在Plan中不正确
public class SuppressCondition extends Condition implements Handleable {

    public static final Class<SuppressConditionHandler> HANDLER_CLASS = SuppressConditionHandler.class;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 上次触发日期
     */
    private LocalDate lastActivatedDate;

    /**
     * 抑制天数, 即在上次触发后不再重复触发的交易日数, 若值为null或小于1则不应用
     */
    private Integer suppressTradeDays;

    /**
     * 在该日期之前不触发
     */
    private LocalDate suppressBeforeDate;

    public SuppressCondition() {
        super();
    }

    public SuppressCondition(Long id, Long ruleId, Integer suppressTradeDays) {
        super(id);
        this.ruleId = ruleId;
        this.suppressTradeDays = suppressTradeDays;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public LocalDate getLastActivatedDate() {
        return lastActivatedDate;
    }

    public void setLastActivatedDate(LocalDate lastActivatedDate) {
        this.lastActivatedDate = lastActivatedDate;
    }

    public Integer getSuppressTradeDays() {
        return suppressTradeDays;
    }

    public void setSuppressTradeDays(Integer suppressTradeDays) {
        this.suppressTradeDays = suppressTradeDays;
    }

    public LocalDate getSuppressBeforeDate() {
        return suppressBeforeDate;
    }

    public void setSuppressBeforeDate(LocalDate suppressBeforeDate) {
        this.suppressBeforeDate = suppressBeforeDate;
    }

    @Override
    public String toString() {
        return "SuppressCondition [ruleId=" + ruleId + ", lastActivatedDate=" + lastActivatedDate
            + ", suppressTradeDays=" + suppressTradeDays + ", suppressBeforeDate=" + suppressBeforeDate + ", getId()="
            + getId() + "]";
    }

    @Override
    public Class<SuppressConditionHandler> handlerClass() {
        return HANDLER_CLASS;
    }

    @Override
    public SuppressConditionHandler handler() {
        return IocUtils.getBean(HANDLER_CLASS).handle(this);
    }

}
