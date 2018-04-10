package com.xwguan.autofund.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xwguan.autofund.dao.stock.StockDao;
import com.xwguan.autofund.dto.common.Page;

/**
 * 分页拦截器, 实现Mybatis分页查询.
 * 
 * <p>dao方法被施加分页查询的充要条件为:
 * dao传入参数为{@code Map<String, Object>}类型(包含直接使用{@code Map<String, Object>}和使用{@code @param}注解标记参数),
 * 并且含有该Map中存在key为{@code "page"}, value为{@code Page}类型(不为null)的键值对.
 * <p>被拦截的对象为{@link StatementHandler}, 实际是{@link RoutingStatementHandler},
 * 方法为{@code Statement prepare(Connection connection, Integer transactionTimeout)}.
 * <p>缺陷: 仅仅是用LIMIT对查询结果进行条数限制, 查询包含collection的结果时会对collection进行条数限制, 而不是包含collection的对象,
 * 导致得到的结果并不是想要的. 因此, 要得到正确的分页查询结果, <b>必须限制包含collection的对象中, collection查询到的结果只有一条</b>,
 * 使用例子参见{@link StockDao}中包含参数Boolean includeData和Boolean includeLatestData的方法. // TODO
 * <p>只有MYSQL方言, 只支持MYSQL.
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-17
 */
@Intercepts({
    @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class PageInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String PAGE_KEY = "page";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 实际上是RoutingStatementHandler
        StatementHandler target = (StatementHandler) invocation.getTarget();
        MetaObject metaObj = SystemMetaObject.forObject(target);
        // 获取BaseStatementHandler
        StatementHandler delegate = (StatementHandler) metaObj.getValue("delegate");
        BoundSql boundSql = delegate.getBoundSql();
        Object paramObj = boundSql.getParameterObject();
        // mybatis将@param注解标记的参数转化成Map, key为@param的value, value为参数值
        if (paramObj instanceof Map) {
            Map<?, ?> paramMap = (Map<?, ?>) paramObj;
            // 只要paramMap中包含key为"page"的Page对象, 就认为需要被分页
            if (paramMap.containsKey(PAGE_KEY) && paramMap.get(PAGE_KEY) instanceof Page) {
                Page page = (Page) paramMap.get(PAGE_KEY);
                logger.debug("page in: {}", page); // TODO
                String sql = boundSql.getSql();
                ParameterHandler parameterHandler = delegate.getParameterHandler();
                page.setTotalItem(countTotalItem(invocation, parameterHandler, sql));
                // MappedStatement mappedStatement = (MappedStatement)
                // metaObj.getValue("delegate.mappedStatement");
                // page.setTotalItem(countTotalItem1(invocation, mappedStatement, boundSql));
                page.calculateFields();
                logger.debug("page calculated: {}", page); // TODO
                String pageSql = getPageSql(page, sql);
                metaObj.setValue("delegate.boundSql.sql", pageSql);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 获得当前查询的总条数
     */
    private int countTotalItem(Invocation invocation, ParameterHandler parameterHandler, String sql) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(*) FROM (").append(sql).append(") for_page");
        String countSql = sb.toString();
        Connection conn = (Connection) invocation.getArgs()[0];
        int countTotalItem = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(countSql);
            parameterHandler.setParameters(ps);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                countTotalItem = rs.getInt(1);
                // 下标从1开始, rs.getInt(0)报错java.sql.SQLException: Column Index out of range, 0 < 1
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.debug("TotalItem:{}", countTotalItem); // TODO
        return countTotalItem;
    }

    /**
     * 获得当前查询的总条数
     */
    @Deprecated
    @SuppressWarnings("unused")
    private int countTotalItem1(Invocation invocation, MappedStatement mappedStatement, BoundSql boundSql) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(*) FROM (").append(boundSql.getSql()).append(") for_page");
        String countSql = sb.toString();
        Connection conn = (Connection) invocation.getArgs()[0];
        int countTotalItem = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(countSql);
            BoundSql countboundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,
                boundSql.getParameterMappings(), boundSql.getParameterObject());
            ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,
                boundSql.getParameterObject(), countboundSql);
            parameterHandler.setParameters(ps);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                countTotalItem = rs.getInt(1);
                // 下标从1开始, rs.getInt(0)报错java.sql.SQLException: Column Index out of range, 0 < 1
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.debug("TotalItem:{}", countTotalItem);
        return countTotalItem;
    }

    /**
     * 获取分页SQL语句
     * 
     * @param page
     * @param sql
     * @param dialect
     * @return
     */
    private String getPageSql(Page page, String sql) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM (").append(sql).append(") originalsql LIMIT ").append(page.getOffset()).append(", ")
            .append(page.getItemPerPage());
        return sb.toString();
    }

}
