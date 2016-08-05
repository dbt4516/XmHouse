<%--
  Created by IntelliJ IDEA.
  User: linsy1
  Date: 2016/7/30
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>分类列表</title>
</head>
<body>
<c:forEach items="${categories}" var="category">
    <option value="${category.name}">${category.name}</option>
    <a href="${pageContext.request.contextPath}/detail/${category.id}">分类下课程</a>
    <a href="${pageContext.request.contextPath}/remove/${category.id}">删除</a>
    <a href="${pageContext.request.contextPath}/showUpdate/${category.id}">修改</a>
</c:forEach>
<a href="${pageContext.request.contextPath}/add">添加</a>
</body>
</html>
