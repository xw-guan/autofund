package com.xwguan.autofund.service.api;

import java.util.List;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.tactic.PlanTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.TacticTemplateEnum;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.exception.plan.TacticTemplateException;

/**
 * 策略服务, 会使用作为参数的Tactic的类型进行反射获取相应dao
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-17
 */
public interface TacticService {

    /**
     * 获取完整策略, 使用tactic的class进行反射获取dao, 使用tactic的id获取具体的策略
     * 
     * @param tactic 策略对象, 必须包含有效id, 其余属性不使用
     * @return
     */
    Tactic getTactic(Tactic tactic);

    /**
     * 获取完整策略
     * 
     * @param tacticId
     * @param tacticType
     * @return
     */
    Tactic getTactic(Long tacticId, TacticTypeEnum tacticType);

    /**
     * 计划id对应的策略列表, 不带详细规则
     * 
     * @param planId
     * @return
     */
    List<PlanTactic> listPlanTacticByPlanId(Long planId);

    /**
     * 计划id对应的策略列表, 不带详细规则
     * 
     * @param planId
     * @return
     */
    List<PositionTactic> listPositionTacticByPlanId(Long planId);

    /**
     * 获取代号对应的策略模板
     * 
     * @param templateCode 策略模板代号, 参看{@link TacticTemplateEnum}, {@link TacticTypeEnum}
     * @return 代号对应的策略模板
     * @throws TacticTemplateException 没有对应的模板策略
     */
    Tactic getTacticTemplate(String templateCode) throws TacticTemplateException;
    
    /**
     * 获取代号对应的策略模板
     * 
     * @return 所有策略模板
     */
    List<Tactic> listAllTacticTemplate();

    /**
     * 插入策略, 也插入所属的Rule和Condition, PeriodCondition将当日设为激活日期重置
     * 
     * @param tactic
     * @return
     */
    int insertTactic(Tactic tactic);

    /**
     * 插入策略, 也插入所属的Rule和Condition, PeriodCondition将当日设为激活日期重置
     * 
     * @param tacticList
     * @return
     */
    int insertTactic(List<Tactic> tacticList);

    /**
     * 删除id对应的策略, 使用tactic的class进行反射获取dao, 使用tactic的id进行删除, 也删除属于该策略的Rule和Condition
     * 
     * @param tactic
     * @return
     */
    int deleteTactic(Tactic tactic);

    /**
     * 删除策略id和类型对应的策略, 也删除属于该策略的Rule和Condition
     * 
     * @param tacticId
     * @param tacticType
     * @return
     */
    int deleteTactic(Long tacticId, TacticTypeEnum tacticType);

    /**
     * 删除idList对应的策略, 使用tactic的class进行反射获取dao, 使用tactic的id进行删除, 也删除属于该策略的Rule和Condition
     * 
     * @param tacticList
     * @return
     */
    int deleteTactic(List<Tactic> tacticList);

    /**
     * 删除planId对应的策略, 也删除属于该策略的Rule和Condition
     * 
     * @param planId
     * @return
     */
    int deleteTacticOfPlan(Long planId);

    /**
     * 更新策略, 并将属于该策略的Rule全部替换(先删除再插入), PeriodCondition将当日设为激活日期重置
     * 
     * @param tactic
     * @return
     */
    int updateTactic(Tactic tactic);

    /**
     * 改变策略, 会将原有策略及属于该策略的Rule和Condition全部删除, 再按照新的策略列表插入.
     * 实际是先调用deleteTactic再调用insertTactic方法
     * 列表中的策略必须包含有效计划id
     * 
     * @param tacticList
     * @return
     */
    int changeTactic(List<Tactic> tacticList);

    /**
     * 重置周期条件, 将当日设为激活日期, 并更新入数据库
     * 
     * @param periodCondition
     * @return 成功重设数量
     */
    int resetAndUpdatePeriodCondition(PeriodCondition periodCondition);

    /**
     * 完整更新周期条件至数据库
     * 
     * @param periodCondition
     * @return
     */
    int updatePeriodCondition(PeriodCondition periodCondition);

    /**
     * 完整更新周期条件至数据库
     * 
     * @param periodConditionList
     * @return
     */
    int updatePeriodCondition(List<PeriodCondition> periodConditionList);

    /**
     * 设置激活状态
     * 
     * @param tacticId
     * @param tacticType
     * @param acticated 激活状态
     * @return 成功更新数量
     */
    int setActivated(Long tacticId, TacticTypeEnum tacticType, Boolean acticated);

}
