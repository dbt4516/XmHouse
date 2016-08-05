<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: suhq
  Date: 2016/7/28
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <h4 class="clearfix">${course.name}课程题库
            <button class="btn btn-info generate-test-paper">生成试卷</button><button class="btn btn-info generate-test-paper" id="add-question">添加题目</button>
        </h4>
        <input type="hidden" id="page-count" value="${count}"/>
        <input type="hidden" id="course-id" value="${course.id}"/>
        <input type="hidden" id="exam-id" value="${examId}"/>
        <c:choose>
            <c:when test="${count gt 0}">
                <div class="alert alert-info" role="alert">鼠标放置于题目可对题目进行修改和删除</div>
                <div class="m-page"></div>
            </c:when>
            <c:otherwise>
                <p>当前没有任何题目~</p>
            </c:otherwise>
        </c:choose>
    </div>

</div>
<jsp:include page="../footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.page.js"></script>

<script>
    $(function () {
        function options(obj) {
            var str1 = '<div class="m-choiceBox"><ul class="m-choiceList clearfix">';
            $.each($.parseJSON(obj), function (k, v) {
                str1 += '<li><label><div class="m-option">' + k + '.&nbsp;&nbsp;</div><div class="m-choice"><p>' + v + '</p></div></label></li>';
            });
            str1 += '</ul></div>';
            return str1;
        }

        function setOptions(data, num) {
            $.each(data, function (key, value) {
                var str = '', isOnline = '';
                str += '<div class="m-list clearfix"><div class="m-total clearfix"><div class="m-queNum">' + (num * 9 + key + 1) + '</div><div class="m-description">';
                if (value.isOnline == 1) {
                    isOnline = '上线';
                } else {
                    isOnline = '下线';
                }
                if (value.type == 1) {
                    str += '<div class="m-duo"><span>多选题</span></div><div class="m-title" id="' + value.id + '">' + value.title + '</div></div><p class="m-ans">答案：' + value.ans + '</p><p class="m-online">状态：' + isOnline + '</p><p class="add-exam-question"><button class="btn btn-info" id="'+value.id+'">添加到考试</button></p></div>';
                    str += options(value.option);
                } else if (value.type == 0) {
                    str += '<div class="m-dan"><span>单选题</span></div><div class="m-title" id="' + value.id + '">' + value.title + '</div></div><p class="m-ans">答案：' + value.ans + '</p><p class="m-online">状态：' + isOnline + '</p><p class="add-exam-question"><button class="btn btn-info" id="'+value.id+'">添加到考试</button></p></div>';
                    str += options(value.option);
                } else if (value.type == 3) {
                    str += '<div class="m-pan"><span>判断题</span></div><div class="m-title" id="' + value.id + '">' + value.title + '</div></div><p class="m-ans">答案：' + value.ans + '</p><p class="m-online">状态：' + isOnline + '</p><p class="add-exam-question"><button class="btn btn-info" id="'+value.id+'">添加到考试</button></p></div>';
                } else {
                    str += '<div class="m-pan"><span>填空题</span></div><div class="m-title" id="' + value.id + '">' + value.title + '</div></div><p class="m-ans">答案：' + value.ans + '</p><p class="m-online">状态：' + isOnline + '</p><p class="add-exam-question"><button class="btn btn-info" id="'+value.id+'">添加到考试</button></p></div>';
                }
                str += '</div>';
                $(".m-page").before(str);
            });
        }

        $.get("${pageContext.request.contextPath}/question/0/9", {
            courseId: $('#course-id').val()
        }, function (data) {
            setOptions(data, 0);
            setAlert();
        });
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
                $.get("/question/" + (num) + "/9", {
                    courseId: $('#course-id').val()
                }, function (data) {
                    $(".m-list").remove();
                    setOptions(data, num);
                    setAlert();
                })
            }
        });
        function setAlert() {
            $(".m-title p").attr("data-toggle", "popover");
            $('[data-toggle="popover"]').each(function () {
                var element = $(this);
                var txt = element.html();
                element.popover({
                    trigger: 'manual',
                    placement: 'bottom', //top, bottom, left or right
                    title: '<div class="m-short">' + txt + '</div>',
                    html: 'true',
                    content: function () {
                        return '<div id="operator"><button class="btn btn-info" id="upd-title" name="' + $(this).parent().attr("id") + '">修改</button>&nbsp;<button class="btn btn-info" id="del-title" name="' + $(this).parent().attr("id") + '">删除</button> </div>';
                    }
                }).on("mouseenter", function () {
                    var _this = this;
                    $(this).popover("show");
                    $(this).siblings(".popover").on("mouseleave", function () {
                        $(_this).popover('hide');
                    });
                }).on("mouseleave", function () {
                    var _this = this;
                    setTimeout(function () {
                        if (!$(".popover:hover").length) {
                            $(_this).popover("hide")
                        }
                    }, 100);
                });
            });
        }

        $("body").on("click", "#del-title", function () {
            $.post("/question/dismissal", {
                questionId: $(this).attr("name")
            }, function (data) {
                if (data) {
                    alert("删除成功");
                    window.location.href = "${pageContext.request.contextPath}/question/list?courseId=" + $('#course-id').val();
                } else {
                    alert("删除失败");
                    window.location.href = "${pageContext.request.contextPath}/question/list?courseId=" + $('#course-id').val();
                }
            });
        });
        function contains(arr, obj) {
            var i = arr.length;
            while (i--) {
                if (arr[i] === obj) {
                    return true;
                }
            }
            return false;
        }
        var examQuestion=[];
        $("body").on("click", ".add-exam-question button", function () {
            if(contains(examQuestion,$(this).attr("id"))){
                alert("已添加到该课程考试中，请不要重复添加");
            }else{
                examQuestion.push($(this).attr("id")*1);
                alert("添加成功！");
            }
            console.log(examQuestion);
        });
        $(".generate-test-paper").click(function(){
            $.post("/exam/ajax/setExamQuestion",{
                examId:$("#exam-id").val(),
                questionIds:examQuestion
            },function(data){
                if(data.success){
                    alert("生成试卷成功");
                    window.location.href = "${pageContext.request.contextPath}/exam/paper/"+$("#exam-id").val();
                }else{
                    alert("生成试卷失败");
                }
            });
        });

        $("body").on("click", "#upd-title", function () {
            window.location.href = "${pageContext.request.contextPath}/question/editor/" + $(this).attr("name");
        });

        $('#add-question').click(function () {
           window.location.href = '${pageContext.request.contextPath}/question/add';
        });
    });
</script>
</body>
</html>
