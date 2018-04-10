package com.xwguan.autofund.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwguan.autofund.dto.common.Result;
import com.xwguan.autofund.dto.plan.PositionDto;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.exception.io.InvalidFundCodeException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;
import com.xwguan.autofund.service.api.PositionService;
import com.xwguan.autofund.service.mapper.plan.PositionMapper;

@Controller
@RequestMapping("/position")
public class PositionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionMapper positionMapper;

    /**
     * url like
     * <code>
     * /position/template?fund=000001&index=000001%2ESH
     * </code>
     * 
     * @param fundCode 基金代码, eg. 000001
     * @param refIndexSymbol 参考指数代码, eg. 000001.SH
     * @return
     */
    @RequestMapping(value = "/template", method = RequestMethod.GET)
    @ResponseBody
    public Result<PositionDto> getPositionTemplate(@RequestParam("fund") String fundCode,
        @RequestParam("index") String refIndexSymbol) {
        try {
            Position position = positionService.getPositionTemplate(fundCode, refIndexSymbol);
            PositionDto positionDto = positionMapper.toPositionDto(position);
            return new Result<>(true, positionDto);
        } catch (InvalidFundCodeException e) {
            return new Result<>(false, "无效的基金代码: " + fundCode);
        } catch (InvalidTickerSymbolException e) {
            return new Result<>(false, "无效的指数代码: " + refIndexSymbol);
        } catch (Exception e) {
            logger.error(e.toString() + ", " + e.getMessage());
            return new Result<>(false, "更新失败");
        }
    }

    /**
     * url like
     * <code>
     * /position/1
     * </code>
     * 
     * @param postionId 持仓id
     * @param refIndexSymbol 参考指数代码, eg. 000001.SH
     * @return
     */
    @RequestMapping(value = "/{postionId}", method = RequestMethod.PUT)
    @ResponseBody
    public Result<String> updateRefIndexId(@PathVariable("postionId") Long postionId,
        @RequestParam("index") String refIndexSymbol) {
        try {
            int cntUpd = positionService.changeRefIndex(postionId, refIndexSymbol);
            return cntUpd > 0
                ? new Result<>(true, "成功更新参考指数")
                : new Result<>(false, "更新失败, id对应的持仓不存在");
        } catch (InvalidTickerSymbolException e) {
            return new Result<>(false, "无效的指数代码: " + refIndexSymbol);
        } catch (Exception e) {
            logger.error(e.toString() + ", " + e.getMessage());
            return new Result<>(false, "更新失败");
        }
    }

    /**
     * url like
     * <code>
     * /position/
     * </code>
     * 
     * @param id 持仓id
     * @param refIndexSymbol 参考指数代码, eg. 000001.SH
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Result<String> insertPosition(@Valid @ModelAttribute PositionDto positionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result<>(false, "存在无效字段");
        }
        Position position = positionMapper.toPosition(positionDto);
        try {
            int cntIns = positionService.insertPosition(position);
            return cntIns > 0
                ? new Result<>(true, "成功插入")
                : new Result<>(false, "插入失败, 已存在基金代码为" + "" + "的持仓");
        } catch (Exception e) {
            logger.error(e.toString() + ", " + e.getMessage());
            return new Result<>(false, "插入失败");
        }
    }

}
