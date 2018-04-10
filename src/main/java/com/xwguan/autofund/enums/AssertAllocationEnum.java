package com.xwguan.autofund.enums;

/**
 * 资产分配枚举
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-05
 */
public enum AssertAllocationEnum {

    STOCK("股票"), DEBENTURE("债券"), CASH("现金");

    private String name;

    private AssertAllocationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
