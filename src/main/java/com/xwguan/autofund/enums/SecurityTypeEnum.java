package com.xwguan.autofund.enums;

/**
 * 证券类型枚举
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-10
 */
public enum SecurityTypeEnum {

    STOCK("股票"), DEBENTURE("债券"), FUND("基金");

    private String name;

    private SecurityTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
