package com.xwguan.autofund.dao.common;

import java.util.List;

/**
 * 批量增删操作的Dao
 * 
 * @author XWGuan
 * @version 1.0.0
 * @param <T> 与数据库表对应的实体类
 * @date 2017-12-20
 */
public interface CDBatchDao<T> {

    /**
     * 批量插入实体类对象至对应的表, 并将主键设为实体类的id.
     * 实体类对象应当是完整的, 没有值的字段会赋值为默认值, 默认值不存在时可能会抛出异常.
     * 实体类应当有id的getter和setter, 否则抛出异常
     * TODO 部分早期代码没有实现SetId
     * 
     * @param list 将要插入的实体类对象列表
     * 
     * @return 成功插入数量
     */
    int insertAndSetIdBatch(List<T> list);
    
    /**
     * <b>新增方法不允许使用, 也没有实现, 旧代码择时修改后删除. 应使用idList.</b>
     * 根据实体类对象列表, 在对应的表中批量删除与实体类对象id相同的数据
     * 
     * @param list 将要删除的实体类对象列表
     * 
     * @return 成功删除数量
     */
    @Deprecated
    int deleteBatch(List<T> list);

    /**
     * 根据id列表, 在对应的表中批量删除与id对应的数据
     * <p><b>WARNNING: </b>这是后加上的接口, 有一些dao没有实现, 使用时请查看对应dao, 没实现的补上 TODO
     * 
     * @param idList 将要删除的数据id
     * @return 成功删除数量
     */
    int deleteByIdListBatch(List<Long> idList);
}
