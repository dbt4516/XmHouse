<!doctype html>
<%--
  Created by IntelliJ IDEA.
  User: hongzhan
  Date: 2016/5/18
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta charset="UTF-8">
    <c:set var="ctx" value="${pageContext.request.contextPath }"/>
    <link rel="stylesheet" type="text/css"  href="${ctx}/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css"  href="${ctx}/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css"  href="${ctx}/css/jquery-ui.css" />
    <link rel="stylesheet" type="text/css"   href="${ctx}/css/jquery.datetimepicker.css">

    <script src="${ctx}/js/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/statis/jquery.datetimepicker.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery/jquery-ui.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/statis/house_report.js" type="text/javascript"></script>
    <script>
        var ctx= "${ctx}";
    </script>
    <title>数据报告</title>
</head>
<body>
<h1 style="margin-bottom: 30px;">
    数据报告
</h1>
<div class="nav-search" id="nav-search" style="left:2px">
    <form class="form-search" onsubmit="return false;">
        <label for="start"  >时间段：</label>
        <input id="start" datepicker class="nav-search-input" >
        <span style="margin:0px 5px;">—</span>
        <input id="end" datepicker class="nav-search-input">

        <button class="btn btn-xs btn-info" onclick="search()" style="margin-bottom: 4px;">
            生成
        </button>
    </form>
</div>

<div style="margin-top: 30px">
    访客IP： <%=request.getRemoteAddr() %>
</div>
</body>


</html>