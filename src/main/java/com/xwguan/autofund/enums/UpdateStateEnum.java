package com.xwguan.autofund.enums;

/**
 * 更新状态枚举类, 标识更新是否成功
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-23
 */
public enum UpdateStateEnum {

    SUCCESS(1, "更新成功"),

    ALREADY_NEWEST(0, "已经是最新"),

    EMPTY_DATA(-1, "从网络获取的数据为空"),

    FAIL_TO_UPDATE_DB(-2, "更新数据库失败"),

    EMPTY_UPDATED_LIST(-3, "待更新数据列表为空"),
    
    ABNORMAL_DATA_SOURCE(-4, "数据源异常");

    private int state;
    private String stateInfo;

    private UpdateStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static UpdateStateEnum stateOf(int state) {
        for (UpdateStateEnum us : UpdateStateEnum.values()) {
            if (us.getState() == state) {
                return us;
            }
        }
        return null;
    }

}
