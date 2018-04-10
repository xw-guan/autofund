package com.xwguan.autofund.dao.plan.tactic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.xwguan.autofund.entity.plan.tactic.PlanTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.util.IocUtils;

/**
 * 获取TacticDao的方法
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-18
 */
public class TacticDaos {
    
    /**
     * @return 计划策略对应的Dao的List
     */
    @SuppressWarnings("unchecked")
    public static List<TacticDao<? extends PlanTactic>> listPlanTacticDao() {
        return Stream.of(TacticTypeEnum.values())
            .filter(TacticTypeEnum::isPlanTactic)
            .map(TacticTypeEnum::getTacticDaoClass)
            .map(c -> (TacticDao<? extends PlanTactic>) IocUtils.getBean(c))
            .collect(Collectors.toList());
    }

    /**
     * @return 持仓策略对应的Dao的List
     */
    @SuppressWarnings("unchecked")
    public static List<TacticDao<? extends PositionTactic>> listPositionTacticDao() {
        return Stream.of(TacticTypeEnum.values())
            .filter(TacticTypeEnum::isPositionTactic)
            .map(TacticTypeEnum::getTacticDaoClass)
            .map(c -> (TacticDao<? extends PositionTactic>) IocUtils.getBean(c))
            .collect(Collectors.toList());
    }
    
    /**
     * @return 所有策略Dao的List
     */
    public static List<TacticDao<? extends Tactic>> listTacticDao() {
        return Stream.of(TacticTypeEnum.values())
            .map(TacticTypeEnum::getTacticDaoClass)
            .map(c -> IocUtils.getBean(c))
            .collect(Collectors.toList());
    }
    
    /**
     * 获取tactic对应的dao, dao的命名必须满足Tactic类名+Dao, 如MATactic对应MATacticDao
     * 
     * @param tactic
     * @return
     */
    public static TacticDao<? extends Tactic> getDao(Tactic tactic) {
        return getDao(TacticTypeEnum.of(tactic));
    }

    /**
     * 获取tactic对应的dao, dao的命名必须满足Tactic类名+Dao, 如MATactic对应MATacticDao
     * 
     * @param tacticType
     * @return
     */
    public static TacticDao<? extends Tactic> getDao(TacticTypeEnum tacticType) {
        return Optional.of(tacticType)
            .map(TacticTypeEnum::getTacticDaoClass)
            .map(c -> IocUtils.getBean(c))
            .orElse(null);
    }

}
