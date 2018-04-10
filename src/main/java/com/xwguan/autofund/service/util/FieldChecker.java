package com.xwguan.autofund.service.util;

/**
 * 判断类T中的某些字段是否相同
 * 
 * @param <T>
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-21
 */
public interface FieldChecker<T> {
    /**
     * 判断类T中的某些字段是否相同
     * 
     * @param t1
     * @param t2
     * @return 指定字段相同返回true, 否则false
     */
    boolean isSelectedFieldsEqual(T t1, T t2);
}
