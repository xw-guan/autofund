package com.xwguan.autofund.service.api;

import java.util.List;

import com.xwguan.autofund.enums.UpdateStateEnum;

/**
 * 定时自动更新数据
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-29
 */
public interface AutoUpdateService {

    List<UpdateStateEnum> autoUpdateAllStock();

    List<UpdateStateEnum> autoUpdateAllStockData();

    List<UpdateStateEnum> autoUpdateAllFund();

    List<UpdateStateEnum> autoUpdateAllFundHistory();

    List<UpdateStateEnum> autoUpdateAllFundDetail();
}
