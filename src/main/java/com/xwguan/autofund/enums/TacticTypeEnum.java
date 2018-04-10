package com.xwguan.autofund.enums;

import com.xwguan.autofund.dao.plan.tactic.TacticDao;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.service.mapper.tactic.TacticMapper;
import com.xwguan.autofund.dao.plan.tactic.GainLossTacticDao;
import com.xwguan.autofund.dao.plan.tactic.IndexChangeTacticDao;
import com.xwguan.autofund.dao.plan.tactic.MATacticDao;
import com.xwguan.autofund.dao.plan.tactic.NavChangeTacticDao;
import com.xwguan.autofund.dao.plan.tactic.PeriodBuyTacticDao;
import com.xwguan.autofund.dao.plan.tactic.ProfitTakingTacticDao;
import com.xwguan.autofund.service.mapper.tactic.GainLossTacticMapper;
import com.xwguan.autofund.service.mapper.tactic.IndexChangeTacticMapper;
import com.xwguan.autofund.service.mapper.tactic.MATacticMapper;
import com.xwguan.autofund.service.mapper.tactic.NavChangeTacticMapper;
import com.xwguan.autofund.service.mapper.tactic.PeriodBuyTacticMapper;
import com.xwguan.autofund.service.mapper.tactic.ProfitTakingTacticMapper;

/**
 * 策略类型枚举
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-21
 */
public enum TacticTypeEnum {

    GAIN_LOSS_TACTIC("盈亏策略", "GLT", "GainLossTactic", GainLossTacticDao.class, GainLossTacticMapper.class, false),

    INDEX_CHANGE_TACTIC("指数变化策略", "ICT", "IndexChangeTactic", IndexChangeTacticDao.class, IndexChangeTacticMapper.class,
        false),

    MA_TACTIC("均线策略", "MAT", "MATactic", MATacticDao.class, MATacticMapper.class, false),

    NAV_CHANGE_TACTIC("净值变化策略", "NCT", "NavChangeTactic", NavChangeTacticDao.class, NavChangeTacticMapper.class, false),

    PERIOD_BUY_TACTIC("定期买入策略", "PBT", "PeriodBuyTactic", PeriodBuyTacticDao.class, PeriodBuyTacticMapper.class, false),

    // PE_TACTIC("市盈率策略", "PETactic", PETacticDao.class, "PET", false),

    // PORTFOLIO_REBALANCE_TACTIC("投资组合再平衡策略", "PortfolioRebalanceTactic", PortfolioRebalanceTacticDao.class, "PRT",
    // true),

    PROFIT_TAKING_TACTIC("计划止盈策略", "PTT", "ProfitTakingTactic", ProfitTakingTacticDao.class,
        ProfitTakingTacticMapper.class, true),

    // VALUE_AVERAGING_TACTIC("价值平均策略", "ValueAveragingTactic", ValueAveragingTacticDao.class, "VAT", false),

    ;

    private String tacticName;

    private String typeCode;

    private String entityName;

    private Class<? extends TacticDao<?>> tacticDaoClass;

    private Class<? extends TacticMapper<?, ?>> tacticMapperClass;

    private boolean isPlanTactic;

    private TacticTypeEnum(String tacticName, String typeCode, String entityName,
        Class<? extends TacticDao<?>> tacticDaoClass, Class<? extends TacticMapper<?, ?>> tacticMapperClass,
        boolean isPlanTactic) {
        this.tacticName = tacticName;
        this.typeCode = typeCode;
        this.entityName = entityName;
        this.tacticDaoClass = tacticDaoClass;
        this.tacticMapperClass = tacticMapperClass;
        this.isPlanTactic = isPlanTactic;
    }

    public String getTacticName() {
        return tacticName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getEntityName() {
        return entityName;
    }

    public Class<? extends TacticDao<?>> getTacticDaoClass() {
        return tacticDaoClass;
    }

    public Class<? extends TacticMapper<?, ?>> getTacticMapperClass() {
        return tacticMapperClass;
    }

    public boolean isPlanTactic() {
        return isPlanTactic;
    }

    public boolean isPositionTactic() {
        return !isPlanTactic;
    }

    public static TacticTypeEnum of(Tactic tactic) {
        if (tactic == null) {
            return null;
        }
        for (TacticTypeEnum tt : values()) {
            if (tt.getEntityName().equals(tactic.getClass().getSimpleName())) {
                return tt;
            }
        }
        return null;
    }

    public static TacticTypeEnum of(String typeCode) {
        for (TacticTypeEnum tt : values()) {
            if (tt.getTypeCode().equals(typeCode)) {
                return tt;
            }
        }
        return null;
    }
}
