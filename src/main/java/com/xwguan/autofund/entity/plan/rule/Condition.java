package com.xwguan.autofund.entity.plan.rule;

import com.xwguan.autofund.service.handler.Handleable;

/**
 * 触发条件, 若Condition为null则表示满足条件
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-05
 */
public abstract class Condition implements Handleable {
    /**
     * 条件id
     */
    private Long id;

    public Condition() {
        super();
    }

    public Condition(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Condition [id=" + id + "]";
    }

}
