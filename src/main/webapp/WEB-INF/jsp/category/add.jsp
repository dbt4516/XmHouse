<%--
  Created by IntelliJ IDEA.
  User: linsy1
  Date: 2016/7/26
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form  method="post" action="${pageContext.request.contextPath}/category/save">

            <label for="name">分类名</label>
            <input type="text" name="name" id="name"/>

            <input type="submit" value="添加"/>
    </form>
</body>
</html>
