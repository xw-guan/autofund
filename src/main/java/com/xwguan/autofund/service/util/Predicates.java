package com.xwguan.autofund.service.util;

import java.util.Optional;

import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.enums.TradeStateEnum;

/**
 * 可用于{@link java.util.function.Predicate<T>}的断言
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-22
 */
public class Predicates {

    /**
     * @return false if null
     */
    public static boolean validTradeDetail(TradeDetail tradeDetail) {
        return Optional.of(tradeDetail)
            .map(TradeDetail::getTradeState)
            .map(TradeStateEnum::isValidTradeState)
            .orElse(false);
    }
    
    /**
     * @return false if null
     */
    public static boolean nonZeroValidTradeDetail(TradeDetail tradeDetail) {
        return Optional.of(tradeDetail)
            .map(TradeDetail::getTradeState)
            .map(TradeStateEnum::isNonzeroValidTradeState)
            .orElse(false);
    }
    
    /**
     * @return false if null
     */
    public static boolean successTradeDetail(TradeDetail tradeDetail) {
        return Optional.of(tradeDetail)
            .map(TradeDetail::getTradeState)
            .map(TradeStateEnum::isSuccessTradeState)
            .orElse(false);
    }
    
    /**
     * @return false if null
     */
    public static boolean greaterThanZero(Long num) {
        return Optional.of(num).map(n -> n > 0).orElse(false);
    }
    
    /**
     * @return false if null
     */
    public static boolean notGreaterThanZero(Long num) {
        return !greaterThanZero(num);
    }
    
    /**
     * @return false if null
     */
    public static boolean greaterThanZero(Integer num) {
        return Optional.of(num).map(n -> n > 0).orElse(false);
    }
    
    /**
     * @return false if null
     */
    public static boolean notGreaterThanZero(Integer num) {
        return !greaterThanZero(num);
    }
    
    /**
     * @return false if null
     */
    public static boolean greaterThanZero(Double num) {
        return Optional.of(num).map(n -> n > 0).orElse(false);
    }
    
    /**
     * @return false if null
     */
    public static boolean notGreaterThanZero(Double num) {
        return !greaterThanZero(num);
    }
    
}
