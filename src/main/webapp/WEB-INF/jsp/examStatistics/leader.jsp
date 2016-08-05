<%--
  Created by IntelliJ IDEA.
  User: zhuangjy
  Date: 2016/8/2
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
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
        <h4>部门统计查询</h4>
        <div class="form-inline">
            <form id="form">
                <label for="course">课程: </label>
                <select id="course" class="form-control">
                    <option value="">请选择</option>
                    <c:forEach items="${courses}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>&nbsp;&nbsp;
                &nbsp;&nbsp;
                <label for="startTime">开始时间：</label>
                <input type="text" name="startTime" id="startTime" class="form-control" style="width:160px;"
                       placeholder="开始时间">&nbsp;&nbsp;
                <label for="endTime">结束时间：</label>
                <input type="text" name="endTime" id="endTime" class="form-control" style="width:160px;"
                       placeholder="结束时间">&nbsp;&nbsp;
                <button type="button" class="btn btn-info" id="search">
                    搜索
                </button>
            </form>

            <hr/>

            <div class="m-exam">
                <table id="example" class="table table-hover">
                    <thead>
                    <tr>
                        <td>考试名称</td>
                        <td>课程名称</td>
                        <td>成绩</td>
                        <td>用户开始考试时间</td>
                        <td>用户考试结束时间</td>
                        <td>部门</td>
                        <td>状态</td>
                        <td>考试开始时间</td>
                        <td>考试结束时间</td>
                        <td>课程类型</td>
                        <td>用户</td>
                        <td>邮箱</td>
                        <td>考试创建时间</td>
                        <td>操作</td>
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
            $.get('${pageContext.request.contextPath}/examStatistics/ajax/leaderQuery', {
                st: DateToUnix($('#startTime').val()),
                et: DateToUnix($('#endTime').val()),
                course: $('#course').val(),
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

        function DateToUnix(str) {
            if(!str)
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
            $.get("${pageContext.request.contextPath}/examStatistics/ajax/leaderQuery", {
                st: DateToUnix($('#startTime').val()),
                et: DateToUnix($('#endTime').val()),
                course: $('#course').val(),
                page: num,
                perpage: 9
            }, function (data) {
                $(".m-list").remove();
                $('#page-count').val(data.count);
                setOptions(data.data);
            });
        }

        function setOptions(data) {
            $.each(data, function (key, value) {
                var score = judgeNull(value.score);
                var ust = judgeTime(value.ust);
                var uet = judgeTime(value.uet);
                var status = judgeStatus(value.status);
                var est = judgeTime(value.est);
                var eet = judgeTime(value.eet);
                var cType;
                if(value.cType==0)
                    cType = '选修';
                else
                    cType = '必修';
                var eCreateTime = judgeTime(value.eCreateTime);


                str = '<tr class="m-tr"><td>' + value.eName + '</td><td>' + value.cName + '</td><td>' + score + '</td><td>' + ust + '</td><td>' + uet + '</td><td>' + value.dName + '</td><td>' +  status + '</td><td>' + est + '</td><td>' + eet + '</td><td>' + cType + '</td><td>' + value.uName + '</td><td>' + value.mail + '</td><td>' + eCreateTime + '</td>';
                $(".m-tr").remove();
                $("#m-tbody").append(str);
            });
        }

        //判断是否为NULL 否则返回格式化时间
        function judgeTime(value) {
            if(value)
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


        //判断如果为null返回空字符
        function judgeNull(value) {
            if (!value)
                return '';
            else
                return value;
        }

        function formatDate(date) {
            return date.toLocaleDateString() + " " + ((date.getHours() < 9) ? "0" + date.getHours() : date.getHours()) + ":" + ((date.getMinutes() < 9) ? "0" + date.getMinutes() : date.getMinutes());
        }

        function UnixToDate(unixTime) {
            var date = new Date(unixTime);
            return formatDate(date);
        }
    });
</script>
</body>
</html>

