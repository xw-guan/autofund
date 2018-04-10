package com.xwguan.autofund.enums;

/**
 * 用户类型
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-05
 */
public enum UserTypeEnum {

    DELETED(0, "已删除"),
    
    /**
     * 只有登录等少量权限
     */
    NON_ACTIVATED(1, "未激活用户"),

    /**
     * 拥有用户权限
     */
    ORDINARY(2, "普通用户"),

    /**
     * 拥有管理权限
     */
    ADMIN(3, "管理员"),

    /**
     * 拥有完全权限
     */
    SUPER_ADMIN(4, "超级管理员");

    private int authorityLevel;

    private String info;

    private UserTypeEnum(int authorityLevel, String info) {
        this.authorityLevel = authorityLevel;
        this.info = info;
    }

    public static UserTypeEnum of(int authorityLevel) {
        for (UserTypeEnum userType : values()) {
            if (userType.getAuthorityLevel() == authorityLevel) {
                return userType;
            }
        }
        return null;
     }
    
    public int getAuthorityLevel() {
        return authorityLevel;
    }

    public String getInfo() {
        return info;
    }

}
