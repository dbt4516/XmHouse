<%--
  Created by IntelliJ IDEA.
  User: zhuangjy
  Date: 2016/7/31
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/question.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="m-left">
    <jsp:include page="../sidebar.jsp"></jsp:include>
</div>
<div class="m-right">
        <div class="m-inner">
        <h4>日志管理</h4>
        <div class="form-inline">
            <form id="form" action="${pageContext.request.contextPath}/log/">
                <label for="user">用户: </label> <input type="text" class="form-control" id="user"
                                                      placeholder="请输入查找的用户邮箱"/>&nbsp;&nbsp;
                <label for="startTime">开始时间：</label>
                <input type="text" name="startTime" id="startTime" class="form-control" style="width:160px;"
                       placeholder="开始时间">&nbsp;&nbsp;
                <label for="endTime">结束时间：</label>
                <input type="text" name="endTime" id="endTime" class="form-control" style="width:160px;"
                       placeholder="结束时间">&nbsp;&nbsp;
                <label for="type">日志类型：</label>
                <select class="form-control" name="type" id="type"
                <c:forEach items="${type}" var="item">
                    <option value="${item.value}">${item.name}</option>
                </c:forEach>
                </select>
                &nbsp;&nbsp;
                <button type="button" class="btn btn-info" id="search">
                    搜索
                </button>
            </form>

            <hr/>

            <div class="m-page"></div>
        </div>

    </div>

    <input type="hidden" id="page-count" value=""/>
</div>

<jsp:include page="../footer.jsp"></jsp:include>

<script src="${pageContext.request.contextPath}/js/jquery/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/datetimepicker/jquery.datetimepicker.full.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.page.js"></script>
<script>
    $(function () {
        var date = new Date();
        $.datetimepicker.setLocale('zh');
        $('#startTime').datetimepicker({
            startDate: '2016/07/29',
            minDate: '2016/07/29',
            format: 'Y-m-d h:m:s'
        });
        $('#startTime').datetimepicker({
            value: date.toLocaleDateString() + " " + date.toLocaleTimeString().substr(2, 5),
            step: 1
        });
        $('#endTime').datetimepicker({
            startDate: '2016/07/29',
            minDate: '2016/07/29',
            format: 'Y-m-d h:m:s'
        });
        $('#endTime').datetimepicker({
            value: date.toLocaleDateString() + " " + date.toLocaleTimeString().substr(2, 5),
            step: 1
        });

        //jquery autocomplete自动填充
        var availableTags = [
            <c:forEach items="${mails}" var="mail">
            "${mail}",
            </c:forEach>
        ];
        $("#user").autocomplete({
            source: availableTags
        });


        $('#search').click(function () {
            load(0);
            $.get('${pageContext.request.contextPath}/log/count', {
                user: $('#user').val(),
                type: $('#type').val(),
                startTime: $('#startTime').val(),
                endTime: $('#endTime').val()
            },function (data) {
                $('#page-count').val(data);
                var pageNum = data;
                if (pageNum % 9 == 0) {
                    pageNum = Math.floor(pageNum / 9);
                } else {
                    pageNum = Math.floor(pageNum / 9) + 1;
                }

                $(".m-page").createPage({
                    pageCount: pageNum,
                    backFn: function (num) {
                        num--;
                        load(num);
                    }
                });
            });
        });

        //加载分页数据
        function load(num) {
            $.get("${pageContext.request.contextPath}/log/" + (num) + "/9", {
                user: $('#user').val(),
                type: $('#type').val(),
                startTime: $('#startTime').val(),
                endTime: $('#endTime').val()
            }, function (data) {
                $(".m-list").remove();
                $('#page-count').val(data);
                setOptions(data);
            });
        }

        function setOptions(data) {
            $.each(data, function (key, value) {
                var str = '<div class="m-list"><p>' + value.content + '</p><hr/></div>';
                $(".m-page").before(str);
            });
        }
    });
</script>
</body>
</html>

