package com.xwguan.autofund.dto.plan;

import javax.validation.constraints.NotNull;

import com.xwguan.autofund.enums.PlanExecutionModeEnum;

public class PlanInfoDto {

    /**
     * 计划id, 对应数据库中的id
     */
    private Long id;

    /**
     * 从属用户的id;
     */
    @NotNull
    private Long userId;

    /**
     * 计划名称
     */
    @NotNull
    private String name;

    /**
     * 计划执行模式
     */
    @NotNull
    private PlanExecutionModeEnum executionMode;

    /**
     * 计划是否为激活状态
     */
    @NotNull
    private Boolean activated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlanExecutionModeEnum getExecutionMode() {
        return executionMode;
    }

    public void setExecutionMode(PlanExecutionModeEnum executionMode) {
        this.executionMode = executionMode;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return "PlanInfoDto [id=" + id + ", userId=" + userId + ", name=" + name + ", executionMode=" + executionMode
            + ", activated=" + activated + "]";
    }

}
