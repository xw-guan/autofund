package com.xwguan.autofund.service.util;

import com.xwguan.autofund.entity.fund.AssetAllocation;
import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.entity.fund.FundCompany;
import com.xwguan.autofund.entity.fund.FundManager;

/**
 * FieldChecker的工厂类, 返回值true表示所选字段都equal, false表示存在不equal的字段
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-22
 */
public class FieldCheckers {
    /**
     * 方法isSelectedFieldsEqual总是返回false
     */
    public static final FieldChecker<Object> ALWAYS_FALSE = (o1, o2) -> {
        return false;
    };

    /**
     * 方法isSelectedFieldsEqual总是返回true
     */
    public static final FieldChecker<Object> ALWAYS_TRUE = (o1, o2) -> {
        return true;
    };

    /**
     * Fund对象的基金的基本信息(名称, 拼音, 类型)是否相同
     */
    public static final FieldChecker<Fund> FUND_BASIC_INFO_CHECKER = (t1, t2) -> {
        if (t1 == t2) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.getName() == null) {
            if (t2.getName() != null) {
                return false;
            }
        } else if (!t1.getName().equals(t2.getName())) {
            return false;
        }
        if (t1.getPinyinName() == null) {
            if (t2.getPinyinName() != null) {
                return false;
            }
        } else if (!t1.getPinyinName().equals(t2.getPinyinName())) {
            return false;
        }
        if (t1.getAbbrPinyinName() == null) {
            if (t2.getAbbrPinyinName() != null) {
                return false;
            }
        } else if (!t1.getAbbrPinyinName().equals(t2.getAbbrPinyinName())) {
            return false;
        }
        if (t1.getType() == null) {
            if (t2.getType() != null) {
                return false;
            }
        } else if (!t1.getType().equals(t2.getType())) {
            return false;
        }
        return true;
    };

    /**
     * FundCompany对象的公司的基本信息(简称, 全称, 简称拼音, 简称拼音首字母)是否相同
     */
    public static final FieldChecker<FundCompany> FUND_COMPANY_BASIC_INFO_CHECKER = (t1, t2) -> {
        if (t1 == t2) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.getSname() == null) {
            if (t2.getSname() != null) {
                return false;
            }
        } else if (!t1.getSname().equals(t2.getSname())) {
            return false;
        }
        if (t1.getFname() == null) {
            if (t2.getFname() != null) {
                return false;
            }
        } else if (!t1.getFname().equals(t2.getFname())) {
            return false;
        }
        if (t1.getPinyinSname() == null) {
            if (t2.getPinyinSname() != null) {
                return false;
            }
        } else if (!t1.getPinyinSname().equals(t2.getPinyinSname())) {
            return false;
        }
        if (t1.getAbbrPinyinSname() == null) {
            if (t2.getAbbrPinyinSname() != null) {
                return false;
            }
        } else if (!t1.getAbbrPinyinSname().equals(t2.getAbbrPinyinSname())) {
            return false;
        }
        return true;
    };

    /**
     * FundManager对象的基本信息()是否相同
     */
    public static final FieldChecker<FundManager> FUND_MANAGER_BASIC_INFO_CHECKER = (t1, t2) -> {
        if (t1 == t2) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.getName() == null) {
            if (t2.getName() != null) {
                return false;
            }
        } else if (!t1.getName().equals(t2.getName())) {
            return false;
        }
        if (t1.getCompanyCode() == null) {
            if (t2.getCompanyCode() != null) {
                return false;
            }
        } else if (!t1.getCompanyCode().equals(t2.getCompanyCode())) {
            return false;
        }
        if (t1.getCurrentFundCodes() == null) {
            if (t2.getCurrentFundCodes() != null) {
                return false;
            }
        } else if (!t1.getCurrentFundCodes().equals(t2.getCurrentFundCodes())) {
            return false;
        }
        if (t1.getCurrentFundNames() == null) {
            if (t2.getCurrentFundNames() != null) {
                return false;
            }
        } else if (!t1.getCurrentFundNames().equals(t2.getCurrentFundNames())) {
            return false;
        }
        if (t1.getCareerDays() == null) {
            if (t2.getCareerDays() != null) {
                return false;
            }
        } else if (!t1.getCareerDays().equals(t2.getCareerDays())) {
            return false;
        }
        if (t1.getCurrentTotalAsset() == null) {
            if (t2.getCurrentTotalAsset() != null) {
                return false;
            }
        } else if (!t1.getCurrentTotalAsset().equals(t2.getCurrentTotalAsset())) {
            return false;
        }
        return true;
    };

    /**
     * AssetAllocation对象的日期是否相同
     */
    public static final FieldChecker<AssetAllocation> ASSET_ALLOCATION_DATE_CHECKER = (t1, t2) -> {
        if (t1 == t2) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.getDate() == null) {
            if (t2.getDate() != null) {
                return false;
            }
        } else if (!t1.getDate().equals(t2.getDate())) {
            return false;
        }
        return true;
    };

}
