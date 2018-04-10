package com.xwguan.autofund.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwguan.autofund.dto.common.Result;
import com.xwguan.autofund.dto.plan.tactic.TacticDto;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.exception.plan.TacticTemplateException;
import com.xwguan.autofund.service.api.TacticService;
import com.xwguan.autofund.service.mapper.tactic.TacticsMapper;

@Controller
@RequestMapping("/tactic")
public class TacticController {

    @Autowired
    private TacticService tacticService;
    
    @Autowired
    private TacticsMapper tacticsMapper;

    @RequestMapping(value = "/template/all", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TacticDto>> getAllTacticTemplate() {
        try {
            List<Tactic> tactics = tacticService.listAllTacticTemplate();
            List<TacticDto> tacticDtos = tacticsMapper.toTacticDtoList(tactics);
            return new Result<>(true, tacticDtos);
        } catch (Exception e) {
            return new Result<>(false, "获取策略模板异常");
        }
    }

    /**
     * url like
     * <code>
     * /tactic/template/MA250
     * </code>
     * templateCode取值
     * <ul>
     * <li>PB: 普通定投策略
     * <li>MA250: 均线策略
     * <li>GL: 盈亏策略
     * <li>IC22: 指数变化策略
     * <li>NC22: 净值变化策略
     * <li>PTPL: 计划止盈策略
     * <li>PTPST: 持仓止盈策略
     * </ul>
     * 
     * @param templateCode
     * @return
     */
    @RequestMapping(value = "/template/{templateCode}", method = RequestMethod.GET)
    @ResponseBody
    public Result<TacticDto> getTacticTemplate(@PathVariable("templateCode") String templateCode) {
        try {
            Tactic tactic = tacticService.getTacticTemplate(templateCode);
            TacticDto tacticDto = tacticsMapper.toTacticDto(tactic);
            return new Result<>(true, tacticDto);
        } catch (TacticTemplateException e) {
            return new Result<>(false, "找不到对应的策略模板");
        } catch (Exception e) {
            return new Result<>(false, "获取策略模板异常");
        }

    }

    /**
     * url like
     * <code>
     * /tactic/MAT/668
     * </code>
     * @see TacticTypeEnum
     * @return
     */
    @RequestMapping(value = "/{tacticType}/{tacticId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<String> deleteTactic(@PathVariable("tacticId") Long tacticId,
        @PathVariable("tacticType") String tacticTypeCode) {
        
        TacticTypeEnum tacticType = TacticTypeEnum.of(tacticTypeCode);
        try {
            int cntDelTactic = tacticService.deleteTactic(tacticId, tacticType);
            return cntDelTactic > 0
                ? new Result<>(true, "成功删除")
                : new Result<>(false, "不存在或已被删除");
        } catch (Exception e) {
            return new Result<>(false, "删除策略失败");
        }
    }

    /**
     * url like
     * <code>
     * /tactic/MAT/668
     * </code>
     * @see TacticTypeEnum
     * @return
     */
    @RequestMapping(value = "/{tacticType}/{tacticId}", method = RequestMethod.PUT)
    @ResponseBody
    public Result<String> setActivated(@PathVariable("tacticId") Long tacticId,
        @PathVariable("tacticType") String tacticTypeCode, 
        @RequestParam("activated") boolean activated) {
        
        TacticTypeEnum tacticType = TacticTypeEnum.of(tacticTypeCode);
        try {
            int cntSetActivated = tacticService.setActivated(tacticId, tacticType, activated);
            return cntSetActivated > 0
                ? new Result<>(true, "成功修改激活状态")
                : new Result<>(false, "策略不存在或已被删除");
        } catch (Exception e) {
            return new Result<>(false, "修改激活状态失败");
        }
    }

}
