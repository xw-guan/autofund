package com.xwguan.autofund.util;

import java.math.BigDecimal;
import java.util.function.Function;

import com.xwguan.autofund.enums.RoundScaleEnum;
import com.xwguan.autofund.exception.OverMaxIterationNumberException;

/**
 * 数学相关工具
 * 
 * @author XWGuan
 * @version 1.0.0 2017-11-1
 */
public class MathUtils {

    /**
     * 在accuracy的精度下, double类型的两个数是否相等
     * 
     * @param num1
     * @param num2
     * @param accuracy 精度
     * @return true if abs(num1 - num2) < abs(accuracy)
     */
    public static boolean isApproximatelyEqual(double num1, double num2, double accuracy) {
        return Math.abs(num1 - num2) < Math.abs(accuracy);
    }

    /**
     * 在accuracy的精度下是否等于0
     * 
     * @param num
     * @param accuracy 精度
     * @return true if abs(num - 0) < abs(accuracy)
     */
    public static boolean isApproximatelyEqualZero(double num, double accuracy) {
        return isApproximatelyEqualZero(num, accuracy);
    }

    /**
     * double类型数字保留n位小数, 如果是NaN或Infinity, 则直接返回
     * 
     * @param num 需要保留n位小数处理的数字
     * @param scale 要保留的小数位数
     * @return 保留n位小数的数字
     */
    public static double round(double num, int scale) {
        if (Double.isNaN(num) || Double.isInfinite(num)) {
            return num;
        }
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * double类型数字保留n位小数, 如果是NaN或Infinity, 则直接返回
     * 
     * @param num 需要保留n位小数处理的数字
     * @param roundScale 小数保留位数枚举
     * @return 保留n位小数的数字
     */
    public static double round(double num, RoundScaleEnum roundScale) {
        return round(num, roundScale.getScale());
    }

    /**
     * 小数转为百分数, 保留两位小数, eg. 0.0012345 -> 0.12(%), 如果是NaN或Infinity, 则直接返回
     */
    public static double decimalToPct(double decimal) {
        return MathUtils.round(decimal * 100, RoundScaleEnum.PCT_ROUND_SCALE);
    }

    /**
     * 百分数转为小数, eg. 0.12(%) -> 0.0012, 如果是NaN或Infinity, 则直接返回
     * 
     * @param pct 百分数
     */
    public static double pctToDecimal(double pct) {
        return pct / 100;
    }

    /**
     * 百分数转为小数, eg. 0.12(%) -> 0.0012, 如果是NaN或Infinity, 则直接返回
     * 
     * @param pct 百分数
     * @param roundScale 保留小数位数
     */
    public static double pctToDecimal(double pct, int roundScale) {
        return round(pctToDecimal(pct), roundScale);
    }

    /**
     * 计算值相对于参照值的偏差率, (value - refValue) / refValue
     * 
     * @param value 值
     * @param refValue 参照值
     * @param roundAsPct 按照百分比保留小数, eg. 0.0012345 -> 0.12(%)
     * @return 值相对于参照值的偏差率
     */
    public static double deviationRate(double value, double refValue, boolean roundAsPct) {
        double deviationRate = (value - refValue) / refValue;
        return roundAsPct ? MathUtils.decimalToPct(deviationRate) : deviationRate;
    }

    /**
     * 牛顿迭代法求函数零点的近似值
     * 
     * @param guess 猜测值
     * @param accuracy 精度
     * @param maxIterationNum 最大迭代次数
     * @param function 待求零点的函数
     * @param derivative 函数的导函数
     * @return 函数零点的近似值
     * @throws OverMaxIterationNumberException 超过最大迭代次数时
     */
    public static double searchZeroPoint(double guess, double accuracy, int maxIterationNum,
        Function<Double, Double> function, Function<Double, Double> derivative) throws OverMaxIterationNumberException {
        double x = guess;
        double fx = function.apply(x);
        int cntIterationNum = 0;
        while (Math.abs(fx) > accuracy) {
            x = x - function.apply(x) / derivative.apply(x);
            fx = function.apply(x);
            if (++cntIterationNum > maxIterationNum) {
                throw new OverMaxIterationNumberException(
                    "Over max iteration number: " + maxIterationNum + ". Current x = " + x + ", fx = " + fx);
            }
        }
        return x;
    }

    /**
     * 牛顿迭代法求函数零点的近似值, 默认猜测值为0, 精度为0.000001, 最大迭代次数100
     * 
     * @param function 待求零点的函数
     * @param derivative 函数的导函数
     * @return 函数零点的近似值
     * @throws OverMaxIterationNumberException 超过最大迭代次数时
     */
    public static double searchZeroPoint(Function<Double, Double> function, Function<Double, Double> derivative)
        throws OverMaxIterationNumberException {
        double guess = 0;
        double accuracy = 0.000001;
        int maxIterationNum = 1000;
        return searchZeroPoint(guess, accuracy, maxIterationNum, function, derivative);
    }

}
