package com.xwguan.autofund.service.handler;

/**
 * 干净复制
 * 
 * @param <T>
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-29
 */
public interface CleanlyCopyable<T extends Handleable> {

    /**
     * 复制EntityHandler处理的实体对象, 清除历史并将本实体及具有从属关系的id设为-1, 将激活状态设为true.
     * 复制null时返回null.
     * 
     * @return 复制的实体
     */
    T cleanCopy();

}
