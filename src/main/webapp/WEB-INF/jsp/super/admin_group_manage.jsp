<%--
  Created by IntelliJ IDEA.
  User: zhuangjy
  Date: 2016/7/30
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/question.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="m-left">
    <jsp:include page="../sidebar.jsp"></jsp:include>
</div>
<div class="m-right">
    <div class="m-inner">
        <h4>${group.name}</h4>
        <p style="color:#bbbbbb;border-left:5px solid #22BAA0">&nbsp;${group.desc}</p>
        <h4>管理人员</h4>
        <div class="form-inline">
            <label for="user">添加人员: &nbsp;</label> <input type="text" class="form-control" id="user"
                                                          placeholder="请输入用户邮箱"/>
            <button type="button" class="btn btn-info" id="add-user">
                添加
            </button>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>管理员工号</th>
                <th>管理员邮箱</th>
                <th>管理员姓名</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.number}</td>
                    <td>${user.mail}</td>
                    <td>${user.userName}</td>
                    <td><a href="javascript:void(0)" class="del-user" id="${user.id}">移除</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <h4>管理权限</h4>
        <div class="form-inline">
            <label for="category">添加分类权限: &nbsp;</label>
            <select class="form-control" id="category">
                <c:forEach items="${category}" var="category">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
            <button type="button" class="btn btn-info" id="add-auth">
                添加
            </button>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>分类</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${categories}" var="category">
                <tr>
                    <td>${category.name}</td>
                    <td><a href="javascript:void(0)" class="del-auth" id="${category.id}">移除权限</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<input type="hidden" id="group-id" value="${group.id}"/>
<jsp:include page="../footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-ui.js"></script>
<script>
    $(function () {
        //jquery autocomplete自动填充
        var availableTags = [
            <c:forEach items="${mails}" var="mail">
            "${mail}",
            </c:forEach>
        ];
        $("#user").autocomplete({
            source: availableTags
        });

        $('#add-user').click(function () {
            $.post('${pageContext.request.contextPath}/super/add/user', {
                adminGroupId: $('#group-id').val(),
                mail: $('#user').val()
            }, function (data) {
                if (data) {
                    window.location.reload();
                }
            })
        });

        $('.del-user').click(function () {
            if (confirm('您确定要移除该管理员吗?')) {
                $.post('${pageContext.request.contextPath}/super/dismissal/user', {
                    adminGroupId: $('#group-id').val(),
                    userId: this.id
                }, function (data) {
                    if (data)
                        window.location.reload();
                });
            }
        });

        $('#add-auth').click(function () {
            $.post('${pageContext.request.contextPath}/super/add/auth', {
                adminGroupId: $('#group-id').val(),
                categoryId: $('#category').val()
            }, function (data) {
                if (data) {
                    window.location.reload();
                }
            })
        });

        $('.del-auth').click(function () {
            if (confirm('您确定要移除该分类的权限吗?')) {
                $.post('${pageContext.request.contextPath}/super/dismissal/auth', {
                    adminGroupId: $('#group-id').val(),
                    categoryId: this.id
                }, function (data) {
                    if (data)
                        window.location.reload();
                });
            }
        });
    });
</script>
</body>
</html>

