<%--
  Created by IntelliJ IDEA.
  User: zhuangjy
  Date: 2016/8/2
  Time: 22:54
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
        <h4>查询用户考试结果</h4>
        <div class="form-group">
            <form id="form" method="post"
                  action="${pageContext.request.contextPath}/examStatistics/ajax/baseQueryReport">
                <div class="form-inline form-group">
                    <label for="name">姓名：</label>
                    <input type="name" name="name" id="name" class="form-control">&nbsp;&nbsp;
                    <label for="course">课程: </label>
                    <select id="course" class="form-control" name="course">
                        <option value="">请选择</option>
                        <c:forEach items="${courses}" var="item">
                            <option value="${item.id}">${item.name}</option>
                        </c:forEach>
                    </select>&nbsp;&nbsp;
                    <label for="type">课程类型：</label>
                    <select class="form-control" name="type" id="type" name="courseType">
                        <option value="">请选择</option>
                        <option value="0">选修</option>
                        <option value="1">必修</option>
                    </select>
                    &nbsp;&nbsp;
                    <label for="status">考试情况</label>
                    <select class="form-control" name="status" id="status" name="status">
                        <option value="">请选择</option>
                        <option value="1">通过</option>
                        <option value="-1">不通过</option>
                    </select>
                    &nbsp;&nbsp;
                </div>

                <div class="form-inline form-group">
                    <label for="startTime">开始时间：</label>
                    <input type="text" name="st" id="startTime" class="form-control" style="width:160px;"
                           placeholder="开始时间">&nbsp;&nbsp;
                    <label for="endTime">结束时间：</label>
                    <input type="text" name="et" id="endTime" class="form-control" style="width:160px;"
                           placeholder="结束时间">&nbsp;&nbsp;
                    <button type="button" class="btn btn-info" id="search">
                        搜索
                    </button>
                    &nbsp;&nbsp;
                    <button type="button" class="btn btn-info" id="report">
                        查询结果导出
                    </button>
                </div>
            </form>

            <hr/>

            <div class="m-exam">
                <table id="example" class="table table-hover">
                    <thead>
                    <tr>
                        <th>姓名</th>
                        <th>部门</th>
                        <th>课程</th>
                        <th>考试名称</th>
                        <th>考试成绩</th>
                        <th>考试情况</th>
                        <th>考试时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="m-tbody"></tbody>
                </table>
            </div>
            <div class="m-page">
            </div>
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
        $.datetimepicker.setLocale('zh');
        $('#startTime').datetimepicker({
            startDate: '2016/07/29',
            minDate: '2016/07/29',
            format: 'Y/m/d h:m'
        });
        $('#startTime').datetimepicker({
            value: '',
            step: 1
        });
        $('#endTime').datetimepicker({
            startDate: '2016/07/29',
            minDate: '2016/07/29',
            format: 'Y/m/d h:m'
        });
        $('#endTime').datetimepicker({
            value: '',
            step: 1
        });

        $('#search').click(function () {
            load(0);
            $.post('${pageContext.request.contextPath}/examStatistics/ajax/baseQuery', {
                st: DateToUnix($('#startTime').val()),
                et: DateToUnix($('#endTime').val()),
                name: $('#name').val(),
                course: $('#course').val(),
                courseType: $('#type').val(),
                status: $('#status').val(),
                page: 0,
                perpage: 9
            }, function (data) {
                $('#page-count').val(data.count);
                var pageNum = data.count;
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
        $('#report').click(function () {
            $('#form').submit();
        });
        function DateToUnix(str) {
            if (!str)
                return str;
            var f = str.split(' ', 2);
            var d = (f[0] ? f[0] : '').split('/', 3);
            var t = (f[1] ? f[1] : '').split(':', 3);
            return (new Date(
                    parseInt(d[0], 10) || null,
                    (parseInt(d[1], 10) || 1) - 1,
                    parseInt(d[2], 10) || null,
                    parseInt(t[0], 10) || null,
                    parseInt(t[1], 10) || null,
                    parseInt(t[2], 10) || null
            )).getTime();
        }

        //加载分页数据
        function load(num) {
            $.post("${pageContext.request.contextPath}/examStatistics/ajax/baseQuery", {
                st: DateToUnix($('#startTime').val()),
                et: DateToUnix($('#endTime').val()),
                name: $('#name').val(),
                course: $('#course').val(),
                courseType: $('#type').val(),
                status: $('#status').val(),
                page: num,
                perpage: 9
            }, function (data) {
                $(".m-list").remove();
                $('#page-count').val(data.count);
                setOptions(data.data);
            });
        }

        function setOptions(data) {
            $(".m-tr").remove();
            $.each(data, function (key, value) {
                var uName = judgeNull(value.uName);
                var dName = judgeNull(value.dName);
                var cName = judgeNull(value.cName);
                var eName = judgeNull(value.eName);
                var score = judgeNull(value.score);
                var status = judgeStatus(value.status);
                var est = judgeTime(value.ust);
                str = '<tr class="m-tr"><td>' + uName + '</td><td>' + dName + '</td><td>' + cName + '</td><td>' + eName + '</td><td>' + score + '</td><td>' + status + '</td><td>' + est + '</td><td>'+
                '<a href="${pageContext.request.contextPath}/exam/detail/'+value.ueid+'">详情</a>';
                $("#m-tbody").append(str);
            });
        }

        //判断如果为null返回空字符
        function judgeNull(value) {
            if (!value)
                return '';
            else
                return value;
        }

        //判断是否为NULL 否则返回格式化时间
        function judgeTime(value) {
            if (value)
                return UnixToDate(value);
            else
                return '';
        }

        function judgeStatus(value) {
            if (value == 0)
                return "未参加";
            else if (value == 1)
                return "通过";
            else if (value == 2)
                return "免考";
            else if (value == 3)
                return "请假";
            else return "不合格";
        }

        function UnixToDate(unixTime) {
            var date = new Date(unixTime);
            return formatDate(date);
        }

        function formatDate(date) {
            return date.toLocaleDateString() + " " + ((date.getHours() < 9) ? "0" + date.getHours() : date.getHours()) + ":" + ((date.getMinutes() < 9) ? "0" + date.getMinutes() : date.getMinutes());
        }

    });
</script>
</body>
</html>

