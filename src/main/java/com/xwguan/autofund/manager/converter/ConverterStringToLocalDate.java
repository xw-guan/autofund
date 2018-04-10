package com.xwguan.autofund.manager.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

/**
 * 将String类型转为Double类型, 无法解析成数字的String转为null
 * 
 * @param <T> 处理的bean类型
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-02
 */
@Deprecated
public class ConverterStringToLocalDate<T> extends AbstractBeanField<T> {

    public ConverterStringToLocalDate() {
    }

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        Converter dc = new DoubleConverter();
        try {
            return dc.convert(Double.class, value);
        } catch (ConversionException e) {
            return null;
        }
    }

}
