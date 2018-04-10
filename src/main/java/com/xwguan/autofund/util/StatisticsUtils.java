package com.xwguan.autofund.util;

import java.util.Collection;

/**
 * 统计相关工具
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-01
 */
public class StatisticsUtils {
    /**
     * 数字集合的平均数
     * 
     * @param collection Double类型的数字集合, 数字不能为null, 否则NPE
     * @return 数字集合的平均数
     */
    public static double average(Collection<Double> collection) {
        double sum = sum(collection);
        double n = collection.size();
        return sum / n;
    }

    /**
     * 数字集合的和
     * 
     * @param collection Double类型的数字集合, 数字不能为null, 否则NPE
     * @return 数字集合的和
     */
    public static double sum(Collection<Double> collection) {
        double sum = 0;
        for (double num : collection) {
            sum += num;
        }
        return sum;
    }

    /**
     * 数字集合的和平方和(SUM of SQuares)
     * 
     * @param collection Double类型的数字集合, 数字不能为null, 否则NPE
     * @return 数字集合的平方和
     */
    public static double sumsq(Collection<Double> collection) {
        double sum = 0;
        for (double num : collection) {
            sum += num * num;
        }
        return sum;
    }

    /**
     * 数字集合的和样本标准差(STandard DEViation of Sample)
     * 
     * @param collection Double类型的数字集合, 数字不能为null, 否则NPE
     * @return 数字集合的和样本方差
     */
    public static double stdevs(Collection<Double> collection) {
        return Math.sqrt(vars(collection));
    }
    
    /**
     * 数字集合的总体标准差(STandard DEViation of Population)
     * 
     * @param collection Double类型的数字集合, 数字不能为null, 否则NPE
     * @return 数字集合的总体方差
     */
    public static double stdevp(Collection<Double> collection) {
        return Math.sqrt(varp(collection));
    }

    /**
     * 数字集合的和样本方差(VARiance of Sample)
     * 
     * @param collection Double类型的数字集合, 数字不能为null, 否则NPE
     * @return 数字集合的和样本方差
     */
    public static double vars(Collection<Double> collection) {
        int n = collection.size();
        return devsq(collection) / (n - 1);
    }
    
    /**
     * 数字集合的总体方差(VARiance of Population)
     * 
     * @param collection Double类型的数字集合, 数字不能为null, 否则NPE
     * @return 数字集合的总体方差
     */
    public static double varp(Collection<Double> collection) {
        int n = collection.size();
        return devsq(collection) / n;
    }
    
    /**
     * 数字集合的离差平方和(sum of SQuares of DEViations)
     * 
     * @param collection Double类型的数字集合, 数字不能为null, 否则NPE
     * @return 数字集合的离差平方和
     */
    public static double devsq(Collection<Double> collection) {
        int n = collection.size();
        double average = average(collection);
        double sumOfSquares = sumsq(collection);
        return sumOfSquares - n * average * average;
    }
}
