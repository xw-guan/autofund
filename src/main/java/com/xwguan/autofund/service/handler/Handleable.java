package com.xwguan.autofund.service.handler;

/**
 * 表面一个实体对象是可处理的, 即拥有对应的Handler
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-23
 */
public interface Handleable {
    /**
     * @return 处理者class
     */
    Class<? extends EntityHandler<? extends Handleable>> handlerClass();

    /**
     * 返回包含实体对象的handler, 序列化时存在与实体类循环调用的导致StackOverFlow的风险
     */
    EntityHandler<? extends Handleable> handler();
}
