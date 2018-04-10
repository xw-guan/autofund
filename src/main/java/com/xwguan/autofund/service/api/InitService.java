package com.xwguan.autofund.service.api;

/**
 * 初始化服务
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-23
 */
public interface InitService {
    /**
     * 根据DefaultIndexEnum初始化stock表
     * 
     * @return int 成功插入条目数
     * @exception FailInitializationException 初始化数据表失败, 可能是数据库相关原因
     */
    int initStockTable();

}
