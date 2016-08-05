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
        <h4>管理员组管理</h4>
        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">
            创建小组
        </button>

        <table class="table table-hover">
            <thead>
            <tr>
                <th>小组名称</th>
                <th>小组描述</th>
                <th>其他操作</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${groups}" var="group">
            <tr>
                <td>${group.name}</td>
                <td>${group.desc}</td>
                <td><a href="javascript:void(0)" class="del" id="${group.id}">删除</a>&nbsp;&nbsp;
                    <a href="${pageContext.request.contextPath}/super/manage/${group.id}">管理小组</a>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h5 class="modal-title" id="myModalLabel">创建管理员组</h5>
                </div>
                <div class="modal-body m-form">
                    <div class="form-group">
                        <div class="form-inline">
                            <div class="form-group">
                                <label for="groupName">管理员组名称：</label>
                                <input type="text" name="examName" class="form-control" id="groupName"
                                       placeholder="管理员组名称" style="width: 370px;">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-inline">
                            <div class="form-group">
                                <label for="groupDesc">管理员组描述：</label>
                                <input type="text" name="questionCount" class="form-control" placeholder="管理员组描述"
                                       id="groupDesc" style="width: 370px;">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="reset" class="btn btn-default" data-dismiss="modal" value="取消">
                    <input type="button" id="add-group" class="btn btn-info" value="添加">
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.page.js"></script>



<script type="text/javascript" src="${pageContext.request.contextPath}/js/boostrap/bootstrap-modal.js"></script>
<script>
    $("#add-group").click(function () {
        $.post("${pageContext.request.contextPath}/super/add/group", {
            name: $('#groupName').val(),
            desc: $('#groupDesc').val()
        }, function (status, data) {
            if (data && status) {
                window.location.href = '${pageContext.request.contextPath}/super';
            } else {
                alert("添加失败");
            }
        });
    });

    $('.del').click(function () {
        if (confirm('您确定要删除该小组吗?')) {
            $.post('${pageContext.request.contextPath}/super/dismissal/group', {
                groupId: this.id
            }, function (data) {
                if (data)
                    window.location.href = '${pageContext.request.contextPath}/super';
            });
        }
    });
</script>
</body>
</html>
