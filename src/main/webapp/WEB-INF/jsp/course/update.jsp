<%--
  Created by IntelliJ IDEA.
  User: linsy1
  Date: 2016/7/25
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
</head>
<body>

<form name="course" method="post" action="${pageContext.request.contextPath}/course/update">
    <input type="hidden" name="id" id="id" value=${course.id} />
    <table>
        <tr>
            <td><label for="name">课程名</label></td>
            <td><input type="text" name="name" id="name" value=${course.name}/></td>

        </tr>
        <tr>
            <td>课程类型:</td>
            <td>

                <input type="radio" name="courseType" value="0"/>选修
                &nbsp;
                <input type="radio" name="courseType"  value="1"/>必修
            </td>

        </tr>
        <tr>
            <td><label name="outline">大纲</label></td>
            <td><textarea name="outline">${course.outline}</textarea></td>
        </tr>
        <tr>
            <td><label name="target">目标</label></td>
            <td><textarea name="target">${course.target}</textarea></td>
        </tr>
        <tr>
            <td><label for="teacher">老师</label></td>
            <td><input type="text" name="teacher" id="teacher" value=${course.teacher} /></td>
        </tr>
        </tr>
        <tr>
            <td>是否上线:</td>
            <td> <input type="radio" name="isOnline" value="0"/>否 &nbsp; <input type="radio" name="isOnline" value="1"/>是
            </td>
        </tr>
        <tr>
            <td><label for="category">属于分类:</label></td>
            <td><input type="text" id="category" value=${course.categoryName}/></td>
        </tr>
        <tr>
            <td >
                <input type="submit" value="修改"/>
            </td>
        </tr>

    </table>
    <c:forEach items="${resources}" var="resource">
    <a href="${pageContext.request.contextPath}/download/${resource.id}">${resource.source}</a>
    <a href="${pageContext.request.contextPath}/remove/${resource.id}"
       onclick="return confirm('Are you sure you want to delete this document?')">删除</a>
    </c:forEach>
</form>
</body>
</html>
