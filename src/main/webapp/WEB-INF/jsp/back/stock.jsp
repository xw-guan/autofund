<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>" />
<title>管理页面</title>
</head>
<body>
  <!-- 导航栏 -->
  <%@ include file="/WEB-INF/jsp/back/common/navbar.jsp"%>

  <div class="container">
    <!-- 状态显示面板 -->
    <div class="row">
      <div class="col-xs-6">
        <div class="panel panel-primary">
          <div class="panel-heading">股票数据</div>
          <div class="panel-body">
            <table class="table table-hover">
              <thead>
                <tr>
                  <th class="col-xs-2">属性</th>
                  <th class="col-xs-3">状态</th>
                  <th class="col-xs-7">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>股票数据</td>
                  <td>
                                   共<span id="countStock">${countStock}</span>条
                  </td>
                  <td>
                    <form class="form-inline" role="form">
                      <div class="form-group">
                        <input id="symbolOrName" class="form-control input-sm" type="text"
                          placeholder="指数代码/名称, eg.000001">
                      </div>
                      <button type="button" id="searchBtn" class="btn btn-default btn-sm glyphicon glyphicon-search"
                        data-toggle="tooltip" title="在数据库中查询"></button>
                      <button type="button" id="listAllBtn" class="btn btn-default btn-sm glyphicon glyphicon-list"
                        data-toggle="tooltip" title="列出所有股票的最近的历史数据"></button>
                    </form>
                  </td>
                </tr>
                <tr>
                  <td>更新状态</td>
                  <td>
                    <span id="updateState"> <c:if test="${countUpdateRequired > 0}">${countUpdateRequired}需要更新</c:if> 
                      <c:if test="${countUpdateRequired == 0}">已是最新</c:if>
                    </span>
                  </td>
                  <td>
                    <button type="button" id="updateBtn" class="btn btn-default btn-sm glyphicon glyphicon-refresh"
                      data-toggle="tooltip" title="更新所有股票数据"></button>
                    <button type="button" id="updateDetailBtn" class="btn btn-default btn-sm glyphicon glyphicon-list"
                      data-toggle="tooltip" title="列出更新详细信息"></button>
                    <span id="updateProcess"></span>
                    <a id="updateDetailBtn"></a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="panel panel-primary">
          <div class="panel-heading">结果列表</div>
          <div class="panel-body">
            <table class="table table-hover">
              <tbody>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <!-- 列表面板 -->
    </div>
  </div>
  
</body>
<script type="text/javascript" src="/autofund/resources/js/stock.js"></script>
<script type="text/javascript">
    $(function() {
        stock.init();
    });
</script>
</html>
<!-- @Author XWGuan @version 1.0.0 -->