<%--
  Created by IntelliJ IDEA.
  User: zhuangjy
  Date: 2016/8/2
  Time: 15:51
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
        <h4>消息管理 <small>消息只能保留十天</small></h4>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>消息内容</th>
                <th>创建时间</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${notifications}" var="notification">
            <tr>
                <td>${notification.content}</td>
                <td>${notification.time}</td>
            </tr>
            </c:forEach>
        </table>
    </div>
 </div>

<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>
