package com.xwguan.autofund.enums;

public enum TimeScopeEnum {
    
    LATEST_DAY("近一日"), 
    LATEST_MONTH("近一月"), 
    LATEST_YEAR("近一年"), 
    LATEST_THREE_YEAR("近三年"), 
    LATEST_FIVE_YEAR("近五年"), 
    THIS_YEAR("当年"), 
    ALL("所有");
    
    private String info;

    private TimeScopeEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
