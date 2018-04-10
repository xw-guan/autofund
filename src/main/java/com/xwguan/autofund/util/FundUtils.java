package com.xwguan.autofund.util;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.exception.io.InvalidParamException;

/**
 * Fund相关工具类
 * <ul>
 * <li> 提供{@code code}字段相关的排序, 比较
 * </ul>
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-20
 */
public class FundUtils {

    private static final Comparator<Fund> FUND_CODE_COMPARATOR = (f1, f2) -> {
        return StringUtils.compare(f1.getCode(), f2.getCode());
    };

    /**
     * 根据{@code Fund}的{@code code}字段对{@code List<Fund>}进行正向排序
     * 
     * @param fundList 被排序的Fund列表
     */
    public static void sortFundListByCode(List<Fund> fundList) {
        fundList.sort(FUND_CODE_COMPARATOR);
    }

    /**
     * 比较两个{@code Fund}对象的{@code String code}字段, {@code null}被认为是最小值
     * <ul>
     * <li>{@code int = 0}, if {@code f1.code} is equal to {@code f2.code} (or both {@code null})</li>
     * <li>{@code int < 0}, if {@code f1.code} is less than {@code f2.code}</li>
     * <li>{@code int > 0}, if {@code f1.code} is greater than {@code f2.code}</li>
     * </ul>
     * 
     * @see org.apache.commons.lang3.StringUtils
     * @param f1 不为null的Fund对象
     * @param f2 不为null的Fund对象
     * @return 当{@code f1.code}等于(或都为{@code null}), 小于, 大于{@code f2.code}时, 返回值分别等于0, 小于0, 大于0
     * @throws InvalidParamException 参数{@code f1.code}和{@code f2.code}为{@code null}
     */
    public static int compareFundCode(Fund f1, Fund f2) throws InvalidParamException {
        if (f1 == null || f2 == null) {
            throw new InvalidParamException("Funds to compare are not allowed to be null");
        }
        return StringUtils.compare(f1.getCode(), f2.getCode());
    }

}
