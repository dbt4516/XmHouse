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
                        <th>考试名称</th>
                        <th>部门名称</th>
                        <th>考试总人数</th>
                        <th>未通过人数</th>
                        <th>通过人数</th>
                        <th>通过率</th>
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
        var date = new Date();
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
            $.get('${pageContext.request.contextPath}/examStatistics/ajax/deptQuery', {
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
            $.get("${pageContext.request.contextPath}/examStatistics/ajax/deptQuery", {
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
            $(".m-tr").remove();
            $.each(data, function (key, value) {
                str ='<tr class="m-tr"><td>'+value.eName+'</td><td>'+value.dName+'</td><td>'+value.count +'</td><td>'+value.failCount+'</td><td>'+value.passCount+'</td><td>'+value.passRate+'</td>';
                $("#m-tbody").append(str);
            });
        }

        function formatDate(date){
            return date.toLocaleDateString()+" "+((date.getHours()<9)?"0"+date.getHours():date.getHours())+":"+((date.getMinutes()<9)?"0"+date.getMinutes():date.getMinutes());
        }

    });
</script>
</body>
</html>

