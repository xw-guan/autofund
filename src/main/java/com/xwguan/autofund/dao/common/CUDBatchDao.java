package com.xwguan.autofund.dao.common;

import java.util.List;

/**
 * 批量增删改操作的Dao
 * 
 * @author XWGuan
 * @version 1.0.0
 * @param <T> 与数据库表对应的实体类
 * @date 2017-12-20
 */
public interface CUDBatchDao<T> extends CDBatchDao<T> {

    /**
     * 批量更新实体类对象对应的表, 按照主键匹配. 若主键在数据库中不存在或则不操作.
     * 将更新除主键, 唯一索引和所属对象id外的其他字段.
     * 值为null的字段也会被更新, 因为很多情况下null也是允许的或有实际含义的值.
     * TODO 目前很多dao中是用replace into实现的, 待修改为CASE WHEN的更新
     * 
     * @param list 将要更新的实体类对象列表
     * 
     * @return 成功更新数量
     */
    int updateBatch(List<T> list);
    
    /**
     * <b>尽量不使用</b><br>
     * 批量更新实体类对象对应的表, 按照主键匹配. 若数据库中不存在则会进行插入操作.
     * 将更新除主键, 唯一索引和所属对象id外的其他字段.
     * 值为null的字段也会被更新, 因为很多情况下null也是允许的或有实际含义的值.
     * 注意如果是插入的情况, 不会将自增主键设为实体类的id. 
     * 并且在没有其他唯一索引的情况下, 因为id也需要被插入, 可能会破坏自增主键的增长规律.
     * 实现上使用insert on duplicate key update.
     * TODO 目前很多dao中是用replace into实现的, 主键和唯一索引冲突时可能影响多行数据, 
     * 待修改为insert on duplicate key update
     * 
     * @param list 将要更新的实体类对象列表
     * 
     * @return 影响行数
     */
    int updateOrInsertBatch(List<T> list);
    
}
