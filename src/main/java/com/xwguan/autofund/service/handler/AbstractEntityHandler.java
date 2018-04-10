package com.xwguan.autofund.service.handler;

/**
 * 抽象实体处理者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @param <T>
 * @date 2018-02-17
 */
public abstract class AbstractEntityHandler<T extends Handleable> implements EntityHandler<T> {

    /**
     * 待处理的实体对象
     */
    private T entity;

    @Override
    public EntityHandler<T> handle(T entity) {
        this.entity = entity;
        return this;
    }

    /**
     * Alwayse false
     */
    @Override
    public boolean needNotHandle() {
        return false;
    }

    @Override
    public T getEntity() {
        return entity;
    }

    protected boolean isEntityNull() {
        return entity == null;
    }

    // protected String getNullEntityExceptionMessage(Object entity) {
    // return new StringBuilder("The entity [").append(entity.getClass().getName())
    // .append("] to be handled cannot be null").toString();
    // }
    //
    // protected void throwExceptionIfNull(Object entity) throws EntityHandlerException {
    // if (isEntityNull()) {
    // throw new EntityHandlerException(getNullEntityExceptionMessage(entity));
    // }
    // }

}
