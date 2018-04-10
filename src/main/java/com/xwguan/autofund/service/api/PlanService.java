package com.xwguan.autofund.service.api;

import java.time.LocalDate;
import java.util.List;

import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.HistoryScopeEnum;
import com.xwguan.autofund.enums.PlanTemplateEnum;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.exception.FailGettingRealTimeDataException;
import com.xwguan.autofund.exception.NotTradeDayException;
import com.xwguan.autofund.exception.account.DeleteAccountException;
import com.xwguan.autofund.exception.io.InvalidParamException;
import com.xwguan.autofund.exception.plan.PlanServiceException;
import com.xwguan.autofund.exception.plan.UnknownTemplateCodeException;

/**
 * 计划服务, 提供操作建议
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-15
 */
public interface PlanService {

    /**
     * 获取交易方案, 同时有买入和卖出条件符合的情况, 按照前一交易日的单位净值进行份额和金额的换算
     * 
     * @param plan 计划
     * @param tradeDate 生成方案的交易日日期
     * @return 交易方案
     * @throws FailGettingRealTimeDataException 网络异常无法获取实时值
     * @throws NotTradeDayException 不是交易日
     */
    PlanTradeScheme getTradeScheme(Plan plan, LocalDate tradeDate)
        throws FailGettingRealTimeDataException, NotTradeDayException;
    
    /**
     * 获取交易方案, 同时有买入和卖出条件符合的情况, 按照前一交易日的单位净值进行份额和金额的换算
     * 
     * @param planId 计划id
     * @param tradeDate 生成方案的交易日日期
     * @return 交易方案
     * @throws FailGettingRealTimeDataException 网络异常无法获取实时值
     * @throws NotTradeDayException 不是交易日
     */
    PlanTradeScheme getTradeScheme(Long planId, LocalDate tradeDate)
        throws FailGettingRealTimeDataException, NotTradeDayException;

    /**
     * 列出属于userId对应用户的所有计划, 不包含详细内容
     * 
     * @param userId 用户id
     * @param page 分页对象
     * @return userId对应用户的所有计划列表
     */
    List<Plan> listPlanByUserId(Integer userId, Page page);

    /**
     * 根据计划id获取计划, 不包含详细内容, 列表为null
     * 
     * @param planId
     * @return
     */
    Plan getPlanByPlanId(Long planId);
    
    /**
     * 根据计划id获取完整计划(但不带计划历史 TODO), 账户历史范围由historyScope确定
     * 
     * @param planId
     * @param historyScope
     * @return
     */
    Plan getFullPlanByPlanId(Long planId, HistoryScopeEnum historyScope); // here

    /**
     * 复制并重置Plan
     * 
     * @param plan 被复制的计划, not null
     * @param initDate 重置使用的初始化日期, 若为null则使用当日
     * @return
     * @throws InvalidParamException plan为null
     */
    Plan cleanCopyAndResetPlan(Plan plan, LocalDate initDate) throws InvalidParamException;
    
    /**
     * 复制并重置Plan
     * 
     * @param planId 计划id, not null and > 0
     * @param initDate 重置使用的初始化日期, 若为null则使用当日
     * @return
     * @throws InvalidParamException  plan为null
     */
    Plan cleanCopyAndResetPlan(Long planId, LocalDate initDate) throws InvalidParamException;

    
    /**
     * 插入计划
     * 
     * @param plan 待插入的计划
     * @return 成功插入数量
     */
    int insertPlan(Plan plan);

    /**
     * 更新计划, 只更新计划名称, 执行模式和激活状态, 必须完整, 否则会设为null
     * 
     * @param plan 待更新的计划
     * @return 更新是否成功
     */
    int updatePlan(Plan plan);

    /**
     * 更新计划从属的策略
     * 
     * @param plan
     * @return
     */
    int updateTactics(Plan plan);

    /**
     * 删除计划, 也删除计划所属账户, 持仓, 策略, 若计划所属账户内还有资产则不能删除并抛出异常
     * 
     * @param planId 待删除的计划id
     * @return 删除数量
     * @throws DeleteAccountException 删除账户异常, 账户内还有资产时抛出
     */
    int deletePlan(Long planId) throws DeleteAccountException;

    /**
     * 删除属于userId的计划, 若计划所属账户内还有资产则不能删除, 全部回滚并抛出异常
     * 
     * @param userId 用户id
     * @return 删除数量
     * @throws DeleteAccountException, 账户内还有资产时抛出
     */
    int deletePlanOfUser(Integer userId) throws DeleteAccountException;

    /**
     * 按照计划模板名获取计划
     * 
     * @param templateCode 计划模板代号{@link PlanTemplateEnum}
     * @return 获取的计划, not null
     * @throws UnknownTemplateCodeException 没有对应的模板策略
     */
    Plan getTemplate(String templateCode) throws UnknownTemplateCodeException;

    /**
     * 按照计划模板名获取计划
     * 
     * @param templateCode 计划模板代号{@link PlanTemplateEnum}
     * @param initDate 激活日期
     * @return 获取的计划, not null
     * @throws UnknownTemplateCodeException 没有对应的模板策略
     */
    Plan getTemplate(String templateCode, LocalDate initDate) throws UnknownTemplateCodeException;
    
    /**
     * 新增策略到Plan
     * 
     * @param plan 计划
     * @param templateCode 策略模板代号{@link TacticTypeEnum}
     * @return 新增到Plan的策略
     * @throws UnknownTemplateCodeException 没有对应的模板策略
     */
    Tactic addTactic(Plan plan, String templateCode) throws UnknownTemplateCodeException;
    
    /**
     * 新增策略到Plan
     * 
     * @param plan 计划
     * @param templateCode 策略模板代号{@link TacticTypeEnum}
     * @param initDate 激活日期
     * @return 新增到Plan的策略
     * @throws UnknownTemplateCodeException 没有对应的模板策略
     */
    Tactic addTactic(Plan plan, String templateCode, LocalDate initDate) throws UnknownTemplateCodeException;

    /**
     * 新增持仓到计划
     * 
     * @param plan 计划
     * @param fundCode 基金代码
     * @param refIndexSymbol 参考指数代码
     * @return 新增到Plan的持仓
     * @throws PlanServiceException 没有对应的基金代码或指数代码
     */
    Position addPosition(Plan plan, String fundCode, String refIndexSymbol) throws PlanServiceException;

}
