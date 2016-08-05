<%--
  Created by IntelliJ IDEA.
  User: zhuangjy
  Date: 2016/7/28
  Time: 23:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/question.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="m-left">
    <jsp:include page="../sidebar.jsp"></jsp:include>
</div>
<div class="m-right">
    <div class="m-inner">
        <h4>考试成绩详情</h4>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>用户名</th>
                <th>邮箱</th>
                <th>课程</th>
                <th>得分</th>
                <th>开始考试时间</th>
                <th>结束考试时间</th>
                <th>部门名</th>
                <th>状态</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${exams}" var="exam">
            <tr>
                <td>${exam.uName}</td>
                <td>${exam.mail}</td>
                <td>${exam.cName}</td>
                <td>${exam.score}</td>
                <td>${exam.ust}</td>
                <td>${exam.uet}</td>
                <td>${exam.dName}</td>
                <td><c:choose>
                    <c:when test="${exam.status eq 0}">未参加</c:when>
                    <c:when test="${exam.status eq 1}">通过</c:when>
                    <c:when test="${exam.status eq 2}">免考</c:when>
                    <c:when test="${exam.status eq 3}">请假</c:when>
                    <c:when test="${exam.status eq -1}">不合格</c:when>
                </c:choose></td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/boostrap/bootstrap-modal.js"></script>
</body>
</html>
