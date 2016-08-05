<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/question.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="m-left">
    <jsp:include page="../sidebar.jsp"></jsp:include>
</div>
<div class="m-right">
    <div class="m-inner">
        <button type="button" id="btn" class="btn btn-info" data-toggle="modal" data-target="#myModal">
            添加考试
        </button>
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content" style="min-width: 660px">
                    <form id="form">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h5 class="modal-title" id="myModalLabel">新建考试</h5>
                        </div>
                        <div class="modal-body m-form">
                            <div class="form-group">
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label for="examName">考试名称：</label>
                                        <input type="text" name="examName" class="form-control" id="examName"
                                               placeholder="考试名称">
                                    </div>
                                    <input type="hidden" id="examId" value=""/>
                                    <div class="form-group m-form-left">
                                        <label>课程列表：</label>
                                        <select class="form-control" name="courseId" id="courseId">
                                            <c:forEach items="${course}" var="course">
                                                <option value="${course.id}">${course.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label for="questionCount">题数：</label>
                                        <input type="text" name="questionCount" class="form-control" style="width:60px;"
                                               placeholder="题数" id="questionCount">
                                    </div>
                                    &nbsp;
                                    <div class="form-group">
                                        <label for="duration">考试时间：</label>
                                        <input type="text" name="duration" class="form-control" style="width:90px;"
                                               placeholder="考试时间" id="duration">
                                    </div>
                                    &nbsp;
                                    <div class="form-group" style="width:170px;">
                                        <label for="passMark">通过分数：</label>
                                        <input type="text" name="passMark" class="form-control" style="width:90px;"
                                               placeholder="通过分数" id="passMark">
                                    </div>
                                    &nbsp;
                                    <div class="form-group">
                                        <label for="is_mock">模拟考试：</label>
                                        <input type="checkbox" name="is_mock" class="form-control" id="is_mock">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-inline">
                                    <div class="form-group" style="width:244px;">
                                        <label for="startTime">开始时间：</label>
                                        <input type="text" name="startTime" id="startTime" class="form-control"
                                               style="width:160px;" placeholder="开始时间">
                                    </div>
                                    <div class="form-group" style="width:230px;">
                                        <label for="endTime">结束时间：</label>
                                        <input type="text" name="endTime" id="endTime" class="form-control"
                                               style="width:160px;" placeholder="结束时间">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="reset" class="btn btn-default" data-dismiss="modal" value="取消">
                            <input type="button" id="add-exam" class="btn btn-info" value="添加">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="m-exam">
            <table id="example" class="table table-hover">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>名称</th>
                    <th>所属课程</th>
                    <th>时长（分钟）</th>
                    <th>题数</th>
                    <th>通过分数</th>
                    <th>通过率</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="m-tbody"></tbody>
            </table>
        </div>
        <div class="m-page"></div>
        <input type="hidden" id="page-count" value="${count}"/>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/boostrap/bootstrap-modal.js"></script>
<script src="${pageContext.request.contextPath}/js/datetimepicker/jquery.datetimepicker.full.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.page.js"></script>
<script>
    //    需要对时间进行相应的校验与约束
    var date = new Date();
    date = formatDate(date);
    function formatDate(date) {
        return date.toLocaleDateString() + " " + ((date.getHours() < 9) ? "0" + date.getHours() : date.getHours()) + ":" + ((date.getMinutes() < 9) ? "0" + date.getMinutes() : date.getMinutes());
    }
    $.datetimepicker.setLocale('zh');
    $('#startTime').datetimepicker({
        startDate: date.split(" ")[0].trim(),
        minDate: date.split(" ")[0].trim()
    });
    $('#startTime').datetimepicker({value: date, step: 1});
    $('#endTime').datetimepicker({
        startDate: date.split(" ")[0].trim(),
        minDate: date.split(" ")[0].trim()
    });
    $('#endTime').datetimepicker({value: date, step: 1});
    $('#startTime').change(function () {
        $('#endTime').datetimepicker({
            startDate: $("#startTime").val(),
            minDate: $("#startTime").val(),
            minTime: $("#startTime").val().split(" ")[1].trim()
        });
        $('#endTime').datetimepicker({value: $("#startTime").val(), step: 1});
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
    function UnixToDate(unixTime) {
        var date = new Date(unixTime);
        return formatDate(date);
    }
    $("#add-exam").click(function () {
        var option = {
            examId: $("#examId").val(),
            examName: $("#examName").val(),
            courseId: $("#courseId option:selected").val(),
            questionCount: $("#questionCount").val(),
            duration: $("#duration").val(),
            passMark: $("#passMark").val(),
            startTime: DateToUnix($("#startTime").val()),
            endTime: DateToUnix($("#endTime").val()),
            is_mock: $('#is_mock').is(':checked') ? 1 : 0
        };
        if ($("#examId").val()) {
            $.post("${pageContext.request.contextPath}/exam/ajax/editExam", option, function (data) {
                if (data.success) {
                    window.location.href = "${pageContext.request.contextPath}/exam/list";
                } else {
                    alert("修改失败");
                }
            });
        } else {
            $.post("${pageContext.request.contextPath}/exam/ajax/addExam", option, function (data) {
                if (data.success) {
                    window.location.href = "${pageContext.request.contextPath}/exam/list";
                } else {
                    alert("添加失败");
                }
            });
        }
    });
    $("#btn").click(function () {
        $("#examId").val("");
        $("#form")[0].reset();
        $("#add-exam").val("添加");
        $("#myModalLabel").text("新建考试");
        $("#courseId").attr("disabled", false);
    });
    function setTr(data, num) {
        $.each(data, function (key, value) {
            var str = '';
            var passRate = '';
            if (value.passRate) {
                passRate = value.passRate;
            } else {
                passRate = "-"
            }
            str += '<tr class="m-tr"><td>' + (num * 9 + key + 1) + '</td><td>' + value.name + '</td><td>' + value.courseName + '</td><td>' + value.duration + '</td><td>' + value.questionCount + '</td><td>' + value.passMark + '</td><td>' + passRate + '</td><td>' + UnixToDate(value.startTime) + '</td><td>' + UnixToDate(value.endTime) + '</td><td>' + UnixToDate(value.createTime) + '</td><td><a href="/question/list?courseId=' + value.courseId + '&examId=' + value.id + '">添加题目</a>&nbsp;&nbsp;<a href="javascript:void(0)" class="edit" data-toggle="modal" data-target="#myModal" id="' + value.id + '"><i class="fa fa-pencil" aria-hidden="true"></i></a>&nbsp;&nbsp;<a href="/exam/setExamUser/' + value.id + '"><i class="fa fa-plus" aria-hidden="true"></i></a>';
            $("#m-tbody").append(str);
        });
    }
    var pageNum = $("#page-count").val();
    if (pageNum % 9 == 0) {
        pageNum = Math.floor(pageNum / 9);
    } else {
        pageNum = Math.floor(pageNum / 9) + 1;
    }
    $(".m-page").createPage({
        pageCount: pageNum,
        current: 1,
        backFn: function (num) {
            num--;
            $.get("/exam/" + (num) + "/9", function (data) {
                $(".m-tr").remove();
                setTr(data, num);
            })
        }
    });
    $.get("/exam/0/9", function (data) {
        console.log(data);
        setTr(data, 0);
    });
    $('body').on('click', '.edit', function () {
        $.get("/exam/ajax/getExam?examId=" + $(this).attr("id"), function (res) {
            $("#examName").val(res.data.name);
//            $.each($("#courseId option"),function(k,v){
//                if(v.value==res.data.courseId){
//                    $("#courseId option").attr("selected",false);
//                    v.selected=true;
//                }
//            });
            $("#courseId").attr("disabled", true);
            $("#myModalLabel").text("修改考试");
            $("#questionCount").val(res.data.questionCount);
            $("#duration").val(res.data.duration);
            $("#passMark").val(res.data.passMark);
            $("#startTime").val(UnixToDate(res.data.startTime));
            $("#endTime").val(UnixToDate(res.data.endTime));
            $("#examId").val(res.data.id);
            $("#add-exam").val("修改");
            $('#is_mock').prop("checked", res.data.is_mock == 1)
        });
    });
    $.get("/userexam/ajax/userStartExam", {
        examId: 27
    }, function (data) {
        console.log(data);
    })
</script>
</body>
</html>
