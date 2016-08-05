<%--
  Created by IntelliJ IDEA.
  User: suhq
  Date: 2016/7/30
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="../common.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/question.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css"/>
</head>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="m-left">
    <jsp:include page="../sidebar.jsp"></jsp:include>
</div>
<div class="m-right">
    <div class="m-inner">
        <h4>${course.name}</h4>
        <div class="m-tab-title">
            讲师：<span class="label label-primary">${course.teacher}</span>
            课程分类：<span class="label label-info">${course.categoryName}</span>
            课程类型：<span class="label label-default"><c:if test="${course.courseType eq 1}">必修</c:if><c:if test="${course.courseType eq 0}">选修</c:if> </span>

            <sec:authorize ifAnyGranted="ROLE_SADMIN,ROLE_ADMIN">
                <span><a href="javascript:void(0)"  data-toggle="modal" data-target="#myModal">修改</a></span>
                <span><a href="javascript:void(0)" id="delete-course">删除</a></span>
            </sec:authorize>

        </div>
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form enctype="multipart/form-data" method="POST" id="course-form">
                        <input type="hidden" id="course_id" name="id" value="${course.id}"/>
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h5 class="modal-title" id="myModalLabel">修改课程</h5>
                        </div>
                        <div class="modal-body m-form">
                            <div class="form-group">
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label for="name">课程名称：</label>
                                        <input type="text" name="name" class="form-control" id="name" placeholder="课程名称" value="${course.name}">
                                    </div>
                                    <div class="form-group">
                                        <label for="teacher">讲师：</label>
                                        <input type="text" name="teacher" class="form-control" id="teacher" placeholder="讲师名称" value="${course.teacher}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label>课程分类：</label>
                                        <select class="form-control" name="categoryId" id="categoryId">
                                            <c:forEach items="${categories}" var="category">
                                                <option <c:if test="${category.id eq course.categoryId}">selected</c:if> value="${course.categoryId}">${category.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group m-adjust-left">
                                        <input id="input-file" type="file" name="img" style="display:none">
                                        <div class="input-group">
                                            <input id="file-url" name="imgSource" class="form-control" type="text" placeholder="图片不能超过2M"  disabled>
                                            <div class="input-group-addon btn btn-info" id="file">课程图片</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label class="radio-inline m-adjust" >课程类型：</label>
                                        <label class="radio-inline">
                                            <input type="radio" name="courseType" value="1" <c:if test="${course.courseType eq 1}">checked="checked"</c:if>> 必修
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="courseType" value="0" <c:if test="${course.courseType eq 0}">checked="checked"</c:if>> 选修
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label class="radio-inline">是否上线：</label>
                                        <label class="radio-inline">
                                            <input type="radio" name="isOnline" value="1"
                                                   <c:if test="${course.isOnline eq 1}">checked="checked"</c:if>> 上线
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="isOnline" value="0"
                                                   <c:if test="${course.isOnline eq 0}">checked="checked"</c:if>> 下线
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>课程大纲：</label>
                                <textarea class="form-control" name="outline" rows="3" id="outline" >${course.outline}</textarea>
                            </div>
                            <div class="form-group">
                                <label>课程目标：</label>
                                <textarea class="form-control" name="target" id="target" rows="3" >${course.target}</textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="reset" class="btn btn-default" data-dismiss="modal" value="取消">
                            <input type="button" class="btn btn-info" value="修改" id="edit-course">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="m-main">
            <div class="m-top clearfix">
                <img src='${pageContext.request.contextPath}/img/course/${course.imgSource}'/>
                <p>课程大纲：${course.outline}</p>
                <p>课程目标：${course.target}</p>
            </div>
            <div class="m-tab">
                <ul class="clearfix">
                    <li class="active"><a href="#tab1">课程留言</a></li>
                    <li><a href="#tab2">课程公告</a></li>
                    <li><a href="#tab3">评分标准</a></li>
                    <li><a href="#tab4">课件</a></li>
                </ul>
                <div class="m-tab-list">
                    <div class="m-tab-content" id="tab1">
                        <h4>留言</h4>
                        <form>
                            <input type="hidden" name="courseId" id="courseId" value=${course.id}>
                            <textarea name="content" id="content" class="form-control" rows="3" style="margin-bottom: 20px;"></textarea>
                            <input class="btn btn-info" type="button" id="submit-message" style="float:right;" value="提交留言"/>
                        </form>
                        <div class="m-message">
                            <label class="label label-info">总共有${count}条留言</label>
                            <div class="m-page m-page1"></div>
                        </div>
                    </div>
                    <div class="m-tab-content clearfix" id="tab2">
                        <h4>课程考试截止时间及规则提醒</h4>
                        <c:forEach items="${exams}" var="exam">
                            <p><c:if test="${exam.isMock eq 1}"><span class="label label-success">模拟考</span></c:if><c:if test="${exam.isMock eq 0}"><span class="label label-danger">正式考</span></c:if>${exam.name}</p>
                            <p>本课程考试从<span class="time-danger">${exam.startTime}</span>开始，截止日期为<span class="time-danger">${exam.endTime}</span>，请各位同事抓紧时间进入考试系统参与考试。谢谢配合</p>
                        </c:forEach>
                    </div>
                    <div class="m-tab-content" id="tab3">
                    </div>
                    <div class="m-tab-content" id="tab4">
                        <c:forEach items="${resources}" var="resource">
                            <span id="file-name">${resource.source}</span><a href="${pageContext.request.contextPath}/resource/download/${resource.id}">点击下载</a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="page-count" value="${count}"/>
    </div>

</div>
<jsp:include page="../footer.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/boostrap/bootstrap-modal.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.page.js"></script>
<script>
    //上传文件
    $("#file").click(function () {
        $("#input-file").click();
        $("#input-file").change(function () {
            $("#file-url").val($(this).val());
        });
    });

    $("#type").change(function () {
        var option = $(this).children('option:selected').val();
        if (option == 2 || option == 3) {
            $("#add-title").css("display", "none");
        } else {
            $("#add-title").css("display", "block");
        }
    });
    $(".m-tab-content").hide();
    $(".m-tab ul li:first").addClass("active").show();
    $(".m-tab-content:first").fadeIn();
    $(".m-tab ul li").click(function() {
        $(".m-tab ul li").removeClass("active");
        $(this).addClass("active");
        $(".m-tab-content").hide();
        var activeTab = $(this).find("a").attr("href");
        $(activeTab).fadeIn();
    });
    $("#delete-course").click(function(){
        $.post("${pageContext.request.contextPath}/course/remove/"+$("#course_id").val(),function(data){
            if(data.success){
                alert("删除成功");
                window.location.href="${pageContext.request.contextPath}/course/list";
            }else{
                alert("课程删除失败，原因可能为此课程下已有考试或者留言，请联系超级管理员");
            }
        });
    });
    $("#edit-course").click(function(){
        $("#course-form").submit(function(){
            $("#course-form").ajaxSubmit({
                url:"${pageContext.request.contextPath}/course/update",
                success:function(data){
                    if(data.success){
                        console.log(data);
                        alert("修改成功");
                        window.history.href="${pageContext.request.contextPath}/course/detail/"+$("#course_id");
                    }else{
                        alert("修改失败");
                    }
                },
            });
        });
        $("#course-form").submit();
    });
    $("#submit-message").click(function(){
       $.post("${pageContext.request.contextPath}/message/add",{
           courseId:$("#courseId").val(),
           content:$("#content").val()
       },function(data){
            if(data.success){
                alert("留言成功");
                window.location.href="${pageContext.request.contextPath}/course/detail/"+$("#courseId").val();
            }else{
                alert("留言失败");
                window.location.href="${pageContext.request.contextPath}/course/detail/"+$("#courseId").val();
            }
       });
    });
    function formatDate(date){
        return date.toLocaleDateString()+" "+((date.getHours()<9)?"0"+date.getHours():date.getHours())+":"+((date.getMinutes()<9)?"0"+date.getMinutes():date.getMinutes());
    }
    function UnixToDate(unixTime){
        var date=new Date(unixTime);
        return formatDate(date);
    }
    function setDiv(obj){
        $.each(obj,function(k,v){
            var str='<div class="m-message-list"><div class="message-content clearfix">'+v.content+'<br/><span class="content-user">'+v.userName+'</span>&nbsp;<span class="message-time">于'+UnixToDate(v.messageTime)+'发表</span></div></div>';
            $(".m-page").before(str);
        });
    }
    $.get("/message/course/"+$("#courseId").val()+"/0/9",function(data){
        setDiv(data);
    });
    var pageNum=$("#page-count").val();
    if(pageNum%9==0){
        pageNum=Math.floor(pageNum/9);
    }else{
        pageNum=Math.floor(pageNum/9)+1;
    }
    $(".m-page").createPage({
        pageCount:pageNum,
        current:1,
        backFn:function(num){
            num--;
            $.get("/message/course/"+$("#courseId").val()+"/"+num+"/9",function(data){
                $(".m-message-list").remove();
                setDiv(data);
            })
        }
    });
</script>
</body>
</html>

