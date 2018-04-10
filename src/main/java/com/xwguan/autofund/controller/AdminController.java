package com.xwguan.autofund.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xwguan.autofund.service.api.FundService;
import com.xwguan.autofund.service.api.StockService;

/**
 * 管理员Controller
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-01
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockService stockService;
    
    @Autowired
    private FundService fundService;

    @RequestMapping("/state")
    public String admin(Model model) {
        return "back/state";
    }

    @RequestMapping("/stock")
    public String stockAdmin(Model model) {
        int countUpdateRequired = 0;
        try {
            countUpdateRequired = stockService.countUpdateRequired();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        int countStock = stockService.countStock();
        model.addAttribute("countUpdateRequired", countUpdateRequired);
        model.addAttribute("countStock", countStock);
        return "back/stock";
    }
    
    @RequestMapping("/fund")
    public String fundAdmin(Model model) {
        int countUpdateRequired = 0;
        try {
            countUpdateRequired = fundService.countUpdateRequired();
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
            logger.error(e.getMessage());
        }
        int countStock = stockService.countStock();
        model.addAttribute("countUpdateRequired", countUpdateRequired);
        model.addAttribute("countStock", countStock);
        return "back/fund";
    }
    
    @RequestMapping("/user")
    public String userAdmin(Model model) {
        // TODO
        return "back/user";
    }

}
