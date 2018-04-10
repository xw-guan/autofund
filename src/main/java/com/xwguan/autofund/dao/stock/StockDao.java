package com.xwguan.autofund.dao.stock;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.stock.Stock;
import com.xwguan.autofund.enums.HistoryScopeEnum;

/**
 * 股票dao, 提供对stock表的CRUD操作
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-10-10
 */
public interface StockDao extends BaseDao<Stock>, CUDBatchDao<Stock> {

    // TODO 修改includeData, includeLatest等为HistoryScopeEnum

    /**
     * 根据id查询股票代码
     * 
     * @param id
     * @return
     */
    String getSymbolById(@Param("id") Integer id);

    /**
     * 根据股票代码查询id
     * 
     * @param symbol
     * @return
     */
    Integer getIdBySymbol(@Param("symbol") String symbol);

    /**
     * 根据数据表中的id字段精确查询股票
     * 
     * @param id 股票id
     * @param historyScope 历史数据范围
     * @param page 分页对象, 分页对股票数据进行, 值为null时不进行分页
     * @return 对应id的Stock对象
     */
    Stock getById(@Param("id") Integer id, @Param("historyScope") HistoryScopeEnum historyScope,
        @Param("page") Page page);

    /**
     * 根据股票代码精确查询股票
     * 
     * @param symbol 股票代码
     * @param historyScope 历史数据范围
     * @return 股票代码为symbol的股票对象
     */
    Stock getBySymbol(@Param("symbol") String symbol, @Param("historyScope") HistoryScopeEnum historyScope,
        @Param("page") Page page);

    /**
     * 查询所有股票
     * 
     * @param includeLatest 是否包含最近的详细数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对股票数据进行, 值为null时不进行分页
     * @return Stock对象列表
     */
    List<Stock> listAll(@Param("includeLatest") Boolean includeLatest, @Param("page") Page page);
    
    /**
     * 查询所有完整股票
     * 
     * @param page 分页对象, 分页对股票数据进行, 值为null时不进行分页
     * @return Stock对象列表
     */
    List<Stock> listAllFull(@Param("page") Page page);

    /**
     * 根据股票代码模糊查询
     * <p>考虑到实际存储的股票代码为如下格式000001.sh, 而用户输入很可能只是000001, 这里无法确定需要的是上证指数还是平安银行
     * 
     * @param symbol 股票代码
     * @param includeLatest 是否包含最近的详细数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Stock对象进行, 值为null时不进行分页
     * @return 相关股票代码的Stock对象列表
     */
    List<Stock> listBySymbol(@Param("symbol") String symbol, @Param("includeLatest") Boolean includeLatest,
        @Param("page") Page page);

    /**
     * 根据股票名称模糊查询
     * 
     * @param name 股票名称
     * @param includeLatest 是否包含最近的详细数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Stock对象进行, 值为null时不进行分页
     * @return 相关股票名称的Stock对象列表
     */
    List<Stock> listByName(@Param("name") String name, @Param("includeLatest") Boolean includeLatest,
        @Param("page") Page page);

    /**
     * 根据股票代码或股票名称模糊查询
     * 
     * @param symbolOrName 查询条件, 对应股票代码或名称
     * @param includeLatest 是否包含最近的详细数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Stock对象进行, 值为null时不进行分页
     * @return 相关股票代码或股票名称Stock对象列表
     */
    List<Stock> listBySymbolOrName(@Param("symbolOrName") String symbolOrName,
        @Param("includeLatest") Boolean includeLatest, @Param("page") Page page);

//    /**
//     * 根据股票id查询最近日期的股票数据
//     * 
//     * @param id 股票id
//     * @return 对应股票id的最近日期的股票数据
//     */
//    Stock getLatestById(@Param("id") Integer id);
//
//    /**
//     * 查询最近日期的股票数据
//     * 
//     * @param page 分页对象, 分页对Stock对象进行, 值为null时不进行分页
//     * @return 最近日期的股票数据列表
//     */
//    List<Stock> listLatest(@Param("page") Page page);

    /**
     * 查询所有指数
     * 
     * @param includeLatest 是否包含最近的详细数据. 值为true时包含, 值为false或null时不包含
     * @param page 分页对象, 分页对Stock对象进行, 值为null时不进行分页
     * @return 所有指数列表
     */
    List<Stock> listIndex(@Param("includeLatest") Boolean includeLatest, @Param("page") Page page);

    /**
     * 根据股票代码删除对应的股票
     * 
     * @param symbol
     * @return 成功删除数量
     */
    int deleteStockBySymbol(@Param("symbol") String symbol);
}
