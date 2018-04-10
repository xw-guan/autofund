package com.xwguan.autofund.dao.common;

import java.util.List;

/**
 * 批量更新操作的Dao
 * 
 * @author XWGuan
 * @version 1.0.0
 * @param <T> 与数据库表对应的实体类
 * @date 2017-12-20
 */
public interface FundAndStockCUDBatchDao<T> extends CUDBatchDao<T> {

    /*
     * 批量更新实体类对象对应的表, 按照唯一索引code字段匹配, 若不存在唯一索引code字段(或类似的唯一索引字段), 则按主键id匹配.
     * 若数据库中不存在则会进行插入操作. 实体类对象应当是完整的, 没有值的字段会赋值为默认值, 默认值不存在时可能会抛出异常
     * <p>实现中使用了REPLACE INTO, 其过程是先DELETE再INSERT, 因此返回值是成功删除数量和成功插入数量之和
     * 
     * @param list 将要更新的实体类对象列表
     * 
     * @return 成功删除数量和成功插入数量之和
     */
    int updateBatchByCode(List<T> list);

}
