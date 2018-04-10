package com.xwguan.autofund.service.handler;

/**
 * 实体对象处理者
 * <p>所有EntityHandler除了业务逻辑相关的null值外, 不做NPE检查, 由调用者负责NPE
 * <p>所有EntityHandler不保证线程安全
 * 
 * 
 * @param <T> 待处理对象类型, 需要实现Handleable接口
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
public interface EntityHandler<T extends Handleable> {

    /**
     * 处理实体对象
     * 
     * @param entity 待处理的实体对象, 可否为null依据具体实体类null是否有逻辑上的意义, 默认为非null
     * @return 本对象
     */
    EntityHandler<T> handle(T entity);

    /**
     * 是否不需要处理
     * 
     * @return
     */
    boolean needNotHandle();

    /**
     * 获取被处理的实体对象
     * 
     * @return
     */
    T getEntity();

}
