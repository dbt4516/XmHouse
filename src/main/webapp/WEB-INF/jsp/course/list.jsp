<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="../common.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/question.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css"/>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="m-left">
    <jsp:include page="../sidebar.jsp"></jsp:include>
</div>
<div class="m-right">
    <div class="m-inner">
        <sec:authorize ifAnyGranted="ROLE_SADMIN">
            <button type="button" id="add-button" class="btn btn-info" data-toggle="modal" data-target="#myModa2">
                添加课程分类
            </button>
        </sec:authorize>
        <div class="modal fade bs-example-modal-sm" id="myModa2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <form method="post" id="category-form" enctype="multipart/form-data">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h5 class="modal-title" id="category-title">新建课程分类</h5>
                        </div>
                        <div class="modal-body m-form">
                            <div class="form-inline">
                                <div class="form-group">
                                    <label for="courseName">分类名称：</label>
                                    <input type="text" id="courseName" name="name" class="form-control" placeholder="分类名称">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="reset" class="btn btn-default" data-dismiss="modal" value="取消">
                            <input type="button" class="btn btn-info" id="add-category" value="添加">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="btn-group" style="margin-top:40px;">
            <sec:authorize ifAnyGranted="ROLE_SADMIN,ROLE_ADMIN">
                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    管理课程分类 <span class="caret"></span>
                </button>
            </sec:authorize>
            <ul class="dropdown-menu">
                <c:forEach items="${categories}" var="category">
                    <li><a href="javascript:void(0)" id="${category.id}" data="${category.name}">${category.name}&nbsp;&nbsp;<i class="fa fa-times delete-category" aria-hidden="true"></i>&nbsp;<i class="fa fa-pencil edit-category" data-toggle="modal" data-target="#myModa2" aria-hidden="true"></i></a></li>
                </c:forEach>
            </ul>
        </div>
        <sec:authorize ifAnyGranted="ROLE_SADMIN,ROLE_ADMIN">
            <button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">
                添加课程
            </button>
        </sec:authorize>
        <div class="modal fade" style="overflow: auto;" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form enctype="multipart/form-data" method="POST" id="course-form">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h5 class="modal-title" id="myModalLabel">新建课程</h5>
                        </div>
                        <div class="modal-body m-form">
                            <div class="form-group">
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label for="name">课程名称：</label>
                                        <input type="text" name="name" class="form-control" id="name" placeholder="课程名称">
                                    </div>
                                    <div class="form-group">
                                        <label for="teacher">讲师：</label>
                                        <input type="text" name="teacher" class="form-control" id="teacher" placeholder="讲师名称">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label>课程分类：</label>
                                        <select class="form-control" name="categoryId" id="categoryId">
                                            <c:forEach items="${categories}" var="category">
                                                <option value="${category.id}">${category.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group m-adjust-left">
                                        <input id="input-file" type="file" name="img" style="display:none">
                                        <div class="input-group">
                                            <input id="file-url" name="imgSource" class="form-control" type="text" placeholder="图片不能超过2M" disabled>
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
                                            <input type="radio" name="courseType" value="1"> 必修
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="courseType" value="0"> 选修
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label class="radio-inline">是否上线：</label>
                                        <label class="radio-inline">
                                            <input type="radio" name="isOnline" value="1"> 上线
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="isOnline" value="0"> 下线
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>课程大纲：</label>
                                <textarea class="form-control" name="outline" rows="3" id="outline"></textarea>
                            </div>
                            <div class="form-group">
                                <label>课程目标：</label>
                                <textarea class="form-control" name="target" id="target" rows="3"></textarea>
                            </div>
                            <div class="form-group">
                                <input id="course-file" type="file" name="resources" style="display:none">
                                <div class="input-group">
                                    <input id="course-url" class="form-control" type="text" placeholder="请打包后一起上传" disabled>
                                    <div class="input-group-addon btn btn-info" id="course">课程课件</div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="reset" class="btn btn-default" data-dismiss="modal" value="取消">
                            <input type="button" class="btn btn-info" value="添加" id="add-course">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="m-content clearfix">

        </div>

        <div class="m-page"></div>
        <input type="hidden" id="page-count" value="${count}"/>
        <input type="hidden" id="category-id"/>
    </div>

</div>
</body>
</html>
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
    $("#course").click(function () {
        $("#course-file").click();
        $("#course-file").change(function () {
            $("#course-url").val($(this).val());
        });
    });
    $("#add-button").click(function(){
        $("#category-id").val("")
        $("#courseName").val("");
        $("#category-title").text("新建分类");
        $("#add-category").val("添加");
    });
    $("#add-course").click(function(){
        $("#course-form").submit(function(){
            $("#course-form").ajaxSubmit({
                url:"${pageContext.request.contextPath}/course/save",
                success:function(data){
                    if(data.success){
                        alert("添加成功");
                        window.location.href="${pageContext.request.contextPath}/course/list";
                    }else{
                        alert("添加失败");
                    }
                }
            });
        });
        $("#course-form").submit();
    });
    $("#add-category").click(function(){
        var option={
            id:$("#category-id").val(),
            name:$("#courseName").val(),
        };
        if($("#category-id").val()){
            $.post("${pageContext.request.contextPath}/category/update",option,function(data){
               if(data.success){
                   alert("修改成功");
                   window.location.href="${pageContext.request.contextPath}/course/list";
               }else{
                   alert("修改失败");
               }
            });
        }else{
            $.post("${pageContext.request.contextPath}/category/save",option,function(data){
                if(data.success){
                    alert("添加成功");
                    window.location.href="${pageContext.request.contextPath}/course/list";
                }else{
                    alert("添加失败");
                    window.location.href="${pageContext.request.contextPath}/course/list";
                }
            });
        }

    });
    function setOption(data){
        $.each(data,function(k,v){
           var str='',courseType='',img='';
           if(v.courseType==1){
               courseType="必修课";
           }else{
               courseType="选修课";
           }
           if(v.imgSource){
               img="${pageContext.request.contextPath}/img/course/"+v.imgSource;
           }else{
               img="http://img0.ph.126.net/Xwrumbg9jCBNRqeLEz41RQ==/6597179717821344651.png";
           }
           str+='<div class="m-course"><img src="'+img+'"/><p class="courseName">'+v.name+'<div class="teacher">讲师：'+v.teacher+'</div><strong>'+courseType+'</strong><a href="/course/detail/'+v.id+'"><img src="'+img+'"/><h3>'+v.name+'</h3><p class="categoryName">讲师：'+v.teacher+'<br/>分类：'+v.categoryName+'</p><p class="description">课程大纲：'+v.outline+'<br/>课程目标：'+v.target+'</p></a></div>';
            $(".m-content").append(str);
        });
    }
    $.get("/course/0/9",function(data){
        setOption(data);
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
            $.get("/course/"+(num)+"/9",function(data){
                $(".m-course").remove();
                setOption(data);
            })
        }
    });
    $('body').on('click', '.edit-category', function() {
        var obj=$(this).parent();
        $("#courseName").val(obj.attr("data"));
        $("#category-title").text("修改分类");
        $("#add-category").val("修改");
        $("#category-id").val(obj.attr("id"));
    });
    $('body').on('click', '.delete-category', function() {
        var obj=$(this).parent();
        $.post("${pageContext.request.contextPath}/category/remove/"+obj.attr("id"),function(data){
            if(data.success){
                alert("删除成功");
                window.location.href="${pageContext.request.contextPath}/course/list";
            }else{
                alert("删除失败");
                window.location.href="${pageContext.request.contextPath}/course/list";
            }
        });
    });
</script>
</body>
</html>
