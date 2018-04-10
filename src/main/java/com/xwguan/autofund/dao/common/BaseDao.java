package com.xwguan.autofund.dao.common;

/**
 * 提供基本增删改查的dao, id必须为Integer或Long类型
 * 
 * @author XWGuan
 * @version 1.0.0
 * @param <T> 与数据库表对应的实体类
 * @date 2017-12-20
 */
public interface BaseDao<T> {

    /**
     * 根据id获取对应实体类, 默认是不包含Join
     * 
     * @return
     */
    T getById(long id);

    /**
     * 表中包含的条目数
     * 
     * @return
     */
    long count();

    /**
     * 插入实体类对象至对应的表, 并将主键设为实体类的id.
     * 实体类对象应当是完整的, 没有值的字段会赋值为默认值, 默认值不存在时可能会抛出异常.
     * 实体类应当有id的getter和setter, 否则抛出异常
     * 
     * @param entity 将要插入的实体类对象
     * 
     * @return 成功插入数量, 0或1
     */
    int insertAndSetId(T entity);

    // /**
    // * 插入对象, 若id已存在则执行更新.
    // * 将更新除主键, 唯一索引和所属对象id外的其他字段.
    // * 值为null的字段也会被更新, 因为很多情况下null也是允许的或有实际含义的值.
    // * 注意如果是插入的情况, 不会将自增主键设为实体类的id. 并且因为id也会被插入, 可能会破坏自增主键的增长规律.
    // * 实现上使用insert on duplicate key update.
    // * TODO 后加的方法, 部分maper还没实现
    // *
    // * @param entity 将要插入的实体类对象
    // * @return 成功修改数量, 更新为2, 插入为1, 未更改为0
    // */
    // int insertOrUpdate(T entity);

    /**
     * 更新实体类对象对应的表, 按照主键匹配.
     * TODO 将更新除主键, 唯一索引和所属对象id外的其他字段.
     * 值为null的字段也会被更新, 因为很多情况下null也是允许的或有实际含义的值.
     * 
     * @param entity 将要更新的实体类对象
     * 
     * @return 成功更新数量, 0或1
     */
    int update(T entity);

    /*
     * 根据实体类对象列表, 在对应的表中删除与id相同的数据
     * 
     * @param id 将要删除的id
     * 
     * @return 成功删除数量, 0或1
     */
    int deleteById(long id);
}
