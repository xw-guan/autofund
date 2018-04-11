package com.xwguan.autofund.service.mapper.tactic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xwguan.autofund.dto.plan.tactic.FlatTacticsDto;
import com.xwguan.autofund.dto.plan.tactic.GainLossTacticDto;
import com.xwguan.autofund.dto.plan.tactic.IndexChangeTacticDto;
import com.xwguan.autofund.dto.plan.tactic.MATacticDto;
import com.xwguan.autofund.dto.plan.tactic.NavChangeTacticDto;
import com.xwguan.autofund.dto.plan.tactic.PeriodBuyTacticDto;
import com.xwguan.autofund.dto.plan.tactic.ProfitTakingTacticDto;
import com.xwguan.autofund.dto.plan.tactic.TacticDto;
import com.xwguan.autofund.entity.plan.tactic.GainLossTactic;
import com.xwguan.autofund.entity.plan.tactic.IndexChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.MATactic;
import com.xwguan.autofund.entity.plan.tactic.NavChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.PeriodBuyTactic;
import com.xwguan.autofund.entity.plan.tactic.PlanTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.entity.plan.tactic.ProfitTakingTactic;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.exception.plan.TacticTypeException;
import com.xwguan.autofund.util.IocUtils;

@Mapper
public interface TacticsMapper {
    
    default Tactic toTactic(String tacticDtoJson, TacticTypeEnum tacticType)
        throws JsonParseException, JsonMappingException, IOException, TacticTypeException {
        ObjectMapper jsonMapper = IocUtils.getBean(ObjectMapper.class, "jsonMapper");
        if (tacticType == TacticTypeEnum.GAIN_LOSS_TACTIC) {
            GainLossTacticDto dto = jsonMapper.readValue(tacticDtoJson, GainLossTacticDto.class);
            GainLossTacticMapper mapper = IocUtils.getBean(GainLossTacticMapper.class);
            return mapper.toTactic(dto);
        }
        if (tacticType == TacticTypeEnum.INDEX_CHANGE_TACTIC) {
            IndexChangeTacticDto dto = jsonMapper.readValue(tacticDtoJson, IndexChangeTacticDto.class);
            IndexChangeTacticMapper mapper = IocUtils.getBean(IndexChangeTacticMapper.class);
            return mapper.toTactic(dto);
        }
        if (tacticType == TacticTypeEnum.MA_TACTIC) {
            MATacticDto dto = jsonMapper.readValue(tacticDtoJson, MATacticDto.class);
            MATacticMapper mapper = IocUtils.getBean(MATacticMapper.class);
            return mapper.toTactic(dto);
        }
        if (tacticType == TacticTypeEnum.NAV_CHANGE_TACTIC) {
            NavChangeTacticDto dto = jsonMapper.readValue(tacticDtoJson, NavChangeTacticDto.class);
            NavChangeTacticMapper mapper = IocUtils.getBean(NavChangeTacticMapper.class);
            return mapper.toTactic(dto);
        }
        if (tacticType == TacticTypeEnum.PERIOD_BUY_TACTIC) {
            PeriodBuyTacticDto dto = jsonMapper.readValue(tacticDtoJson, PeriodBuyTacticDto.class);
            PeriodBuyTacticMapper mapper = IocUtils.getBean(PeriodBuyTacticMapper.class);
            return mapper.toTactic(dto);
        }
        if (tacticType == TacticTypeEnum.PROFIT_TAKING_TACTIC) {
            ProfitTakingTacticDto dto = jsonMapper.readValue(tacticDtoJson, ProfitTakingTacticDto.class);
            ProfitTakingTacticMapper mapper = IocUtils.getBean(ProfitTakingTacticMapper.class);
            return mapper.toTactic(dto);
        }
        throw new TacticTypeException("Unsupported tactic type:" + tacticType);
    }

    
    default TacticDto toTacticDto(Tactic tactic) {
        if (tactic == null) {
            return null;
        }
        if (tactic instanceof GainLossTactic) {
            GainLossTactic t = (GainLossTactic) tactic;
            GainLossTacticMapper mapper = IocUtils.getBean(GainLossTacticMapper.class);
            return mapper.toTacticDto(t, TacticTypeEnum.GAIN_LOSS_TACTIC);
        }
        if (tactic instanceof IndexChangeTactic) {
            IndexChangeTactic t = (IndexChangeTactic) tactic;
            IndexChangeTacticMapper mapper = IocUtils.getBean(IndexChangeTacticMapper.class);
            return mapper.toTacticDto(t, TacticTypeEnum.INDEX_CHANGE_TACTIC);
        }
        if (tactic instanceof MATactic) {
            MATactic t = (MATactic) tactic;
            MATacticMapper mapper = IocUtils.getBean(MATacticMapper.class);
            return mapper.toTacticDto(t, TacticTypeEnum.MA_TACTIC);
        }
        if (tactic instanceof NavChangeTactic) {
            NavChangeTactic t = (NavChangeTactic) tactic;
            NavChangeTacticMapper mapper = IocUtils.getBean(NavChangeTacticMapper.class);
            return mapper.toTacticDto(t, TacticTypeEnum.NAV_CHANGE_TACTIC);
        }
        if (tactic instanceof PeriodBuyTactic) {
            PeriodBuyTactic t = (PeriodBuyTactic) tactic;
            PeriodBuyTacticMapper mapper = IocUtils.getBean(PeriodBuyTacticMapper.class);
            return mapper.toTacticDto(t, TacticTypeEnum.PERIOD_BUY_TACTIC);
        }
        if (tactic instanceof ProfitTakingTactic) {
            ProfitTakingTactic t = (ProfitTakingTactic) tactic;
            ProfitTakingTacticMapper mapper = IocUtils.getBean(ProfitTakingTacticMapper.class);
            return mapper.toTacticDto(t, TacticTypeEnum.GAIN_LOSS_TACTIC);
        }
        return null;
    }

    default Tactic toTactic(TacticDto tacticDto) {
        if (tacticDto == null) {
            return null;
        }
        if (tacticDto instanceof GainLossTacticDto) {
            GainLossTacticDto dto = (GainLossTacticDto) tacticDto;
            GainLossTacticMapper mapper = IocUtils.getBean(GainLossTacticMapper.class);
            return mapper.toTactic(dto);
        }
        if (tacticDto instanceof IndexChangeTacticDto) {
            IndexChangeTacticDto dto = (IndexChangeTacticDto) tacticDto;
            IndexChangeTacticMapper mapper = IocUtils.getBean(IndexChangeTacticMapper.class);
            return mapper.toTactic(dto);
        }
        if (tacticDto instanceof MATacticDto) {
            MATacticDto dto = (MATacticDto) tacticDto;
            MATacticMapper mapper = IocUtils.getBean(MATacticMapper.class);
            return mapper.toTactic(dto);
        }
        if (tacticDto instanceof NavChangeTacticDto) {
            NavChangeTacticDto dto = (NavChangeTacticDto) tacticDto;
            NavChangeTacticMapper mapper = IocUtils.getBean(NavChangeTacticMapper.class);
            return mapper.toTactic(dto);
        }
        if (tacticDto instanceof PeriodBuyTacticDto) {
            PeriodBuyTacticDto dto = (PeriodBuyTacticDto) tacticDto;
            PeriodBuyTacticMapper mapper = IocUtils.getBean(PeriodBuyTacticMapper.class);
            return mapper.toTactic(dto);
        }
        if (tacticDto instanceof ProfitTakingTacticDto) {
            ProfitTakingTacticDto dto = (ProfitTakingTacticDto) tacticDto;
            ProfitTakingTacticMapper mapper = IocUtils.getBean(ProfitTakingTacticMapper.class);
            return mapper.toTactic(dto);
        }
        return null;
    }

    List<TacticDto> toTacticDtoList(List<? extends Tactic> tacticList);

    List<Tactic> toTacticList(List<TacticDto> tacticDtoList);

    // default List<TacticDto> toTacticDtoList(List<PlanTactic> plantacticList, List<PositionTactic> positionTacticList)
    // {
    // List<TacticDto> tacticDtoList = new ArrayList<>();
    // if (plantacticList != null) {
    // tacticDtoList.addAll(toTacticDtoList(plantacticList));
    // }
    // if (positionTacticList != null) {
    // tacticDtoList.addAll(toTacticDtoList(positionTacticList));
    // }
    // return tacticDtoList;
    // }

    default FlatTacticsDto toFlatTacticsDto(List<Tactic> tacticList) {
        FlatTacticsDto flatTacticsDto = new FlatTacticsDto();
        for (Tactic tactic : tacticList) {
            if (tactic instanceof GainLossTactic) {
                GainLossTactic t = (GainLossTactic) tactic;
                GainLossTacticDto dto = IocUtils.getBean(GainLossTacticMapper.class)
                    .toTacticDto(t, TacticTypeEnum.GAIN_LOSS_TACTIC);
                flatTacticsDto.setGainLossTactic(dto);
            }
            if (tactic instanceof IndexChangeTactic) {
                IndexChangeTactic t = (IndexChangeTactic) tactic;
                IndexChangeTacticDto dto = IocUtils.getBean(IndexChangeTacticMapper.class)
                    .toTacticDto(t, TacticTypeEnum.INDEX_CHANGE_TACTIC);
                flatTacticsDto.setIndexChangeTactic(dto);
            }
            if (tactic instanceof MATactic) {
                MATactic t = (MATactic) tactic;
                MATacticDto dto = IocUtils.getBean(MATacticMapper.class)
                    .toTacticDto(t, TacticTypeEnum.MA_TACTIC);
                flatTacticsDto.setMaTactic(dto);
            }
            if (tactic instanceof NavChangeTactic) {
                NavChangeTactic t = (NavChangeTactic) tactic;
                NavChangeTacticDto dto = IocUtils.getBean(NavChangeTacticMapper.class)
                    .toTacticDto(t, TacticTypeEnum.NAV_CHANGE_TACTIC);
                flatTacticsDto.setNavChangeTactic(dto);
            }
            if (tactic instanceof PeriodBuyTactic) {
                PeriodBuyTactic t = (PeriodBuyTactic) tactic;
                PeriodBuyTacticDto dto = IocUtils.getBean(PeriodBuyTacticMapper.class)
                    .toTacticDto(t, TacticTypeEnum.PERIOD_BUY_TACTIC);
                flatTacticsDto.setPeriodBuyTactic(dto);
            }
            if (tactic instanceof ProfitTakingTactic) {
                ProfitTakingTactic t = (ProfitTakingTactic) tactic;
                ProfitTakingTacticDto dto = IocUtils.getBean(ProfitTakingTacticMapper.class)
                    .toTacticDto(t, TacticTypeEnum.PROFIT_TAKING_TACTIC);
                flatTacticsDto.setProfitTakingTactic(dto);
            }
        }
        return flatTacticsDto;
    };

    default List<Tactic> toTactics(FlatTacticsDto flatTacticsDto) {
        List<Tactic> tactics = new ArrayList<>();
        tactics.add(toTactic(flatTacticsDto.getGainLossTactic()));
        tactics.add(toTactic(flatTacticsDto.getIndexChangeTactic()));
        tactics.add(toTactic(flatTacticsDto.getMaTactic()));
        tactics.add(toTactic(flatTacticsDto.getNavChangeTactic()));
        tactics.add(toTactic(flatTacticsDto.getPeriodBuyTactic()));
        tactics.add(toTactic(flatTacticsDto.getProfitTakingTactic()));
        return tactics;
    }

    default List<PlanTactic> toPlanTactics(FlatTacticsDto flatTacticsDto) {
        List<PlanTactic> tactics = new ArrayList<>();
        tactics.add((PlanTactic) toTactic(flatTacticsDto.getProfitTakingTactic()));
        return tactics;
    }

    default List<PositionTactic> toPositionTactics(FlatTacticsDto flatTacticsDto) {
        List<PositionTactic> tactics = new ArrayList<>();
        tactics.add((PositionTactic) toTactic(flatTacticsDto.getGainLossTactic()));
        tactics.add((PositionTactic) toTactic(flatTacticsDto.getIndexChangeTactic()));
        tactics.add((PositionTactic) toTactic(flatTacticsDto.getMaTactic()));
        tactics.add((PositionTactic) toTactic(flatTacticsDto.getNavChangeTactic()));
        tactics.add((PositionTactic) toTactic(flatTacticsDto.getPeriodBuyTactic()));
        tactics.add((PositionTactic) toTactic(flatTacticsDto.getProfitTakingTactic()));
        return tactics;
    }

}
