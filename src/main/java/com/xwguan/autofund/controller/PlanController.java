package com.xwguan.autofund.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwguan.autofund.dto.account.LatestAccountDto;
import com.xwguan.autofund.dto.common.Result;
import com.xwguan.autofund.dto.plan.LatestPlanDto;
import com.xwguan.autofund.dto.plan.LatestPositionDto;
import com.xwguan.autofund.dto.plan.PlanDto;
import com.xwguan.autofund.dto.plan.PlanInfoDto;
import com.xwguan.autofund.dto.plan.tactic.TacticDto;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.backtest.PlanBackTestResult;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.tactic.PlanTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.HistoryScopeEnum;
import com.xwguan.autofund.exception.FailGettingRealTimeDataException;
import com.xwguan.autofund.exception.NotTradeDayException;
import com.xwguan.autofund.exception.account.DeleteAccountException;
import com.xwguan.autofund.exception.io.InvalidFundCodeException;
import com.xwguan.autofund.exception.io.InvalidParamException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;
import com.xwguan.autofund.exception.plan.BackTestServiceException;
import com.xwguan.autofund.exception.plan.UnknownTemplateCodeException;
import com.xwguan.autofund.service.api.AccountService;
import com.xwguan.autofund.service.api.BackTestService;
import com.xwguan.autofund.service.api.PlanService;
import com.xwguan.autofund.service.api.PositionService;
import com.xwguan.autofund.service.api.TacticService;
import com.xwguan.autofund.service.mapper.account.AccountMapper;
import com.xwguan.autofund.service.mapper.plan.PlanMapper;
import com.xwguan.autofund.service.mapper.plan.PositionMapper;
import com.xwguan.autofund.service.mapper.tactic.TacticsMapper;

@Controller
@RequestMapping("/plan")
public class PlanController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlanService planService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private TacticService tacticService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BackTestService backTestService;

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private TacticsMapper tacticsMapper;

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 返回包含回测结果的回测页面
     * <code>
     * /plan/12/backTest?from=2018-01-01&to=2018-03-01
     * </code>
     * 
     * @param planId
     * @param from
     * @param to
     * @return
     */
    @RequestMapping(value = "/{planId}/backTest", method = RequestMethod.GET)
    public String backTest(Model model,
        @PathVariable("planId") Long planId,
        @RequestParam(value = "from", required = false) String from,
        @RequestParam(value = "to", required = false) String to) {

        LocalDate startDate = from == null ? LocalDate.now().minusYears(3) : LocalDate.parse(from);
        LocalDate endDate = to == null ? LocalDate.now() : LocalDate.parse(to);

        Result<PlanBackTestResult> result;
        try {
            PlanBackTestResult backTestResult = backTestService.backTest(planId, startDate, endDate);
            result = new Result<>(true, backTestResult);
        } catch (BackTestServiceException e) {
            logger.error("BackTest Error", e);
            result = new Result<>(false, e.getMessage());
        } catch (Exception e) {
            logger.error(e.toString() + ", " + e.getMessage());
            result = new Result<>(false, "回测错误");
        }
        model.addAttribute(result); // 或者是不在这里初始化数据, 只返回页面, 由前端ajax初始化? TODO
        return "user/backTest";
    }

    /**
     * 获取回测结果, 用于在回测页面中修改时间重新回测, 用js根据Result刷新回测页面
     * <code>
     * /plan/12/backTestResult?from=2018-01-01&to=2018-03-01
     * </code>
     * 
     * @param planId
     * @param from
     * @param to
     * @return
     */
    @RequestMapping(value = "/{planId}/backTestResult", method = RequestMethod.GET)
    @ResponseBody
    public Result<PlanBackTestResult> getBackTestResult(@PathVariable("planId") Long planId,
        @RequestParam(value = "from", required = false) String from,
        @RequestParam(value = "to", required = false) String to) {

        LocalDate startDate = from == null ? LocalDate.now().minusYears(3) : LocalDate.parse(from);
        LocalDate endDate = to == null ? LocalDate.now() : LocalDate.parse(to);

        Result<PlanBackTestResult> result;
        try {
            PlanBackTestResult backTestResult = backTestService.backTest(planId, startDate, endDate);
            result = new Result<>(true, backTestResult);
        } catch (BackTestServiceException e) {
            logger.error("BackTest Error", e);
            result = new Result<>(false, e.getMessage());
        } catch (Exception e) {
            logger.error(e.toString() + ", " + e.getMessage());
            result = new Result<>(false, "回测错误");
        }
        return result;
    }

    /**
     * 获取交易方案
     * <code>
     * /plan/12/tradeSchemeResult/2018-03-01
     * </code>
     * 
     * @param planId
     * @param date
     * @return
     */
    @RequestMapping(value = "/{planId}/tradeSchemeResult/{date}", method = RequestMethod.GET)
    @ResponseBody
    public Result<PlanTradeScheme> getTradeScheme(@PathVariable("planId") Long planId,
        @PathVariable("date") String date) {

        // TODO TradeSchemeDto

        LocalDate tradeDate = LocalDate.parse(date);
        try {
            PlanTradeScheme tradeScheme = planService.getTradeScheme(planId, tradeDate);
            tradeScheme.setPlan(null); // TODO use dto
            return new Result<>(true, tradeScheme);
        } catch (NotTradeDayException e) {
            return new Result<>(false, "该日期不是交易日");
        } catch (FailGettingRealTimeDataException e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "无法获取实时值, 请重试");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "策略获取错误");
        }
    }

    /**
     * 获取计划模板
     * <code>
     * /plan/templateResult/MA
     * </code>
     * templateCode取值:
     * <ul>
     * <li>PB: 普通定投计划
     * <li>MA: 均线计划
     * <li>GL: 盈亏计划
     * <li>MAPT: 止盈均线计划
     * </ul>
     * 
     * @param templateCode
     * @return
     */
    @RequestMapping(value = "/templateResult/{templateCode}", method = RequestMethod.GET)
    @ResponseBody
    public Result<PlanDto> getTemplate(@PathVariable("templateCode") String templateCode) {
        try {
            Plan plan = planService.getTemplate(templateCode);
            PlanDto planDto = planMapper.toPlanDto(plan);
            return new Result<>(true, planDto);
        } catch (UnknownTemplateCodeException e) {
            return new Result<>(false, "未知模板代码");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "无法获取计划模板");
        }
    }

    /**
     * 复制计划
     * <code>
     * /plan/12/copyResult
     * </code>
     * 
     * @param planId
     * @return
     */
    @RequestMapping(value = "/{planId}/copyResult", method = RequestMethod.GET)
    @ResponseBody
    public Result<PlanDto> copyPlan(@PathVariable("planId") Long planId) {
        try {
            Plan plan = planService.cleanCopyAndResetPlan(planId, LocalDate.now());
            PlanDto planDto = planMapper.toPlanDto(plan);
            return new Result<>(true, planDto);
        } catch (InvalidParamException e) {
            return new Result<>(false, "被复制的计划为空");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "复制计划失败");
        }
    }

    /**
     * 获取计划, 包含策略和持仓
     * <code>
     * /plan/12
     * </code>
     * 
     * @param planId
     * @return
     */
    @RequestMapping(value = "/{planId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<PlanDto> getPlan(@PathVariable("planId") Long planId) {
        try {
            Plan plan = planService.getFullPlanByPlanId(planId, HistoryScopeEnum.NONE);
            if (plan == null) {
                return new Result<>(false, "id为" + planId + "的计划不存在");
            }
            PlanDto planDto = planMapper.toPlanDto(plan);
            return new Result<>(true, planDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "获取计划失败");
        }
    }

    /**
     * 获取计划, 包含策略, 持仓和最新的账户历史
     * <code>
     * /plan/12
     * </code>
     * 
     * @param planId
     * @return
     */
    @RequestMapping(value = "/{planId}/latest", method = RequestMethod.GET)
    @ResponseBody
    public Result<LatestPlanDto> getLatestPlan(@PathVariable("planId") Long planId) {
        try {
            Plan plan = planService.getFullPlanByPlanId(planId, HistoryScopeEnum.LATEST);
            if (plan == null) {
                return new Result<>(false, "id为" + planId + "的计划不存在");
            }
            LatestPlanDto latestPlanDto = planMapper.toLatestPlanDto(plan);
            return new Result<>(true, latestPlanDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "获取计划失败");
        }
    }

    /**
     * 获取属于计划的持仓, 带有最新账户历史
     * <code>
     * /plan/12/positions/latest
     * </code>
     * 
     * @param planId
     * @return
     */
    @RequestMapping(value = "/{planId}/positions/latest", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<LatestPositionDto>> listLatestPositions(@PathVariable("planId") Long planId) {
        try {
            List<Position> positions = positionService.listByPlanId(planId, true, null);
            System.out.println(positions); // TODO
            List<LatestPositionDto> latestPositionDtoList = positionMapper.toLatestPositionDtoList(positions);
            return new Result<>(true, latestPositionDtoList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "获取持仓失败");
        }
    }

    /**
     * 获取属于计划的计划策略
     * <code>
     * /plan/12/planTactics
     * </code>
     * 
     * @param planId
     * @return
     */
    @RequestMapping(value = "/{planId}/planTactics", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TacticDto>> listPlanTactics(@PathVariable("planId") Long planId) {
        try {
            List<PlanTactic> planTactics = tacticService.listPlanTacticByPlanId(planId);
            List<TacticDto> tacticDtoList = tacticsMapper.toTacticDtoList(planTactics);
            return new Result<>(true, tacticDtoList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "获取计划策略失败");
        }
    }

    /**
     * 获取属于计划的持仓策略
     * <code>
     * /plan/12/positionTactics
     * </code>
     * 
     * @param planId
     * @return
     */
    @RequestMapping(value = "/{planId}/positionTactics", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TacticDto>> listPositionTactics(@PathVariable("planId") Long planId) {
        try {
            List<PositionTactic> positionTactics = tacticService.listPositionTacticByPlanId(planId);
            List<TacticDto> tacticDtoList = tacticsMapper.toTacticDtoList(positionTactics);
            return new Result<>(true, tacticDtoList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "获取持仓策略失败");
        }
    }

    /**
     * 获取属于计划的账户, 带有最新账户历史
     * <code>
     * /plan/12/account/latest
     * </code>
     * 
     * @param planId
     * @return
     */
    @RequestMapping(value = "/{planId}/account/latest", method = RequestMethod.GET)
    @ResponseBody
    public Result<LatestAccountDto> getLatestAccount(@PathVariable("planId") Long planId) {
        try {
            Account account = accountService.getAccount(planId, AccountOwnerTypeEnum.PLAN, HistoryScopeEnum.LATEST,
                null);
            LatestAccountDto latestAccountDto = accountMapper.toLatestAccountDto(account);
            return new Result<>(true, latestAccountDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "获取账户失败");
        }
    }

    // 未完全测试
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result<String> createPlan(@ModelAttribute PlanDto planDto) {
        Plan plan = planMapper.toPlan(planDto);
        try {
            int cntInsPlan = planService.insertPlan(plan);
            if (cntInsPlan > 0) {
                return new Result<>(true, "新建计划成功");
            }
            return new Result<>(false, "新建计划失败, 请重试");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "新建计划失败, 请重试");
        }
    }

    // 未完全测试
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Result<String> updatePlan(@ModelAttribute PlanInfoDto planInfoDto) {
        Plan plan = planMapper.toPlan(planInfoDto);
        try {
            int cntUpdPlan = planService.updatePlan(plan);
            return cntUpdPlan > 0
                ? new Result<>(true, "更新计划成功")
                : new Result<>(false, "计划不存在");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "更新计划成功, 请重试");
        }
    }

    // 未完全测试
    /**
     * 删除计划, 计划账户内还有资产是不能删除
     * <code>
     * /plan/12
     * </code>
     * 
     * @param planId
     * @return
     */
    @RequestMapping(value = "/{planId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<String> deletePlan(@PathVariable("planId") Long planId) {
        try {
            int cntDelPlan = planService.deletePlan(planId);
            return cntDelPlan > 0
                ? new Result<>(true, "删除计划成功")
                : new Result<>(false, "不存在或已被删除");
        } catch (DeleteAccountException e) {
            return new Result<>(false, "计划内还有资产, 无法删除");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "删除计划失败, 请重试");
        }
    }

    /**
     * url like
     * <code>
     * /{planId}/position
     * </code>
     * 
     * @param fundCode 基金代码, eg. 000001
     * @param refIndexSymbol 参考指数代码, eg. 000001.SH
     * @return
     */
    @RequestMapping(value = "/{planId}/position", method = RequestMethod.PUT)
    @ResponseBody
    public Result<String> insertPosition(@PathVariable("planId") Long planId, String fundCode, String refIndexSymbol) {
        try {
            Position position = positionService.getPositionTemplate(fundCode, refIndexSymbol);
            position.setPlanId(planId);
            int cntIns = positionService.insertPosition(position);
            return cntIns > 0
                ? new Result<>(true, "成功插入")
                : new Result<>(false, "插入失败, 已存在基金代码为" + "" + "的持仓");
        } catch (InvalidFundCodeException e) {
            return new Result<>(false, "无效的基金代码");
        } catch (InvalidTickerSymbolException e) {
            return new Result<>(false, "无效的指数代码");
        } catch (Exception e) {
            logger.error(e.toString() + ", " + e.getMessage());
            return new Result<>(false, "插入失败");
        }
    }

}
