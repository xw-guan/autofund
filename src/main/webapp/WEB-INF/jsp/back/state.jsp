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
    <div class="panel panel-default">
    </div>
  </div>
</body>
</html>