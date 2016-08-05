<%--
  Created by IntelliJ IDEA.
  User: suhq
  Date: 2016/7/31
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/question.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="m-left">
    <jsp:include page="../sidebar.jsp"></jsp:include>
</div>
<div class="m-right">
    <div class="m-inner">
        <h4>添加考试人员</h4>
        <div class="m-dept-user">
            <div class="form-inline">
                <div class="form-group">
                    <label for="m-dept" class="label label-warning">方式一：部门</label>
                    <select class="form-control" id="m-dept">
                    </select>
                </div>
            </div>
            <div class="m-checkbox clearfix">
                <div class="m-checkbox-header">
                    <div class="m-select-all">
                        <input type="checkbox" id="m-select-all"> 全选/取消全选
                    </div>
                </div>

                <div class="m-checkbox-body clearfix" id="m-checkbox-body">
                </div>
                <button id="submit-user" class="btn btn-info" style="float:right;">提交</button>
            </div>
            <hr/>
            <div class="form-inline" style="margin-top:20px;">
                <div class="form-group">
                    <label for="m-search-user" class="label label-warning">方式二：搜索添加</label>
                    <input type="text" class="form-control m-search-user" id="m-search-user" placeholder="请输入用户邮箱"/>
                    <button type="button" class="btn btn-info" id="add-user">
                        添加
                    </button>
                </div>
            </div>
            <div class="m-checkbox" id="search-box"></div>
            <hr/>
            <form enctype="multipart/form-data" method="post" id="user-form">
                <div class="form-inline" style="margin-top:20px;">
                    <div class="form-group">
                        <label class="label label-warning">方式三：文件批量导入（只支持xls）</label>
                        <div class="form-group">
                            <input id="exam-user-file" type="file" name="resources" style="display:none">
                            <div class="input-group">
                                <input id="exam-user-url" class="form-control" type="text" placeholder="请按照格式上传文件"
                                       disabled>
                                <div class="input-group-addon btn btn-info" id="exam-user">文件上传考试人员</div>
                            </div>
                            <button class="btn btn-info" id="submit-exam-user">上传</button>
                        </div>
                        <input type="hidden" id="exam-id" name="examId" value="${exam.id}"/>
                    </div>
                </div>
            </form>
            <hr/>
            <div class="form-inline" style="margin-top:20px;">
                <div class="form-group">
                    <label for="m-search-user" class="label label-warning">添加免考人员</label>
                    <input type="text" class="form-control m-search-user" id="exam-exempt-users" placeholder="请输入用户邮箱"/>
                    <button type="button" class="btn btn-info" id="add-exempt-user">
                        添加
                    </button>
                </div>
            </div>
            <div class="m-checkbox" id="search-exempt-box"></div>
            <hr/>
            <div class="form-inline" style="margin-top:20px;">
                <div class="form-group">
                    <label for="m-search-user" class="label label-warning">添加请假人员</label>
                    <input type="text" class="form-control m-search-user" id="exam-leave-users" placeholder="请输入用户邮箱"/>
                    <button type="button" class="btn btn-info" id="add-leave-user">
                        添加
                    </button>
                </div>
            </div>
            <div class="m-checkbox" id="search-leave-box"></div>
        </div>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.form.js"></script>
<script>
    $("#exam-user").click(function () {
        $("#exam-user-file").click();
        $("#exam-user-file").change(function () {
            $("#exam-user-url").val($(this).val());
        });

    });
//    $("#submit-exam-user").click(function(){
//        $.ajax({
//            type: "POST",
//            url: "/user/ajax/uploadXls",
//            enctype: 'multipart/form-data',
//            data: {
//                examId:$("#exam-id").val(),
//                origin:$("#submit-exam-user").val()
//            },
//            success: function (data) {
//                if(data.success){
//                    alert("上传成功");
//                }else{
//                    alert('请点击上传文件！')
//                }
//            }
//        });
    $("#submit-exam-user").click(function () {
        $("#user-form").submit(function () {
            $("#user-form").ajaxSubmit({
                url: "${pageContext.request.contextPath}/user/ajax/uploadXls",
                success: function (data) {
                    debugger;
                    if (data) {
                        alert("上传成功");
                        window.history.href = "${pageContext.request.contextPath}/course/list";
                    } else {
                        alert("上传失败");
                    }
                }
            });
        });
        $("#user-form").submit();
    });
//       $.post("/user/ajax/uploadXls",{
//           examId:$("#exam-id").val(),
//           origin:$("#submit-exam-user").val()
//       },function(data){
//            if(data.success){
//                alert("上传成功");
//            }else{
//                alert('请点击上传文件！')
//            }
//       });
//    });
    var availableTags = [
        <c:forEach items="${mails}" var="mail">
        "${mail}",
        </c:forEach>
    ];
    $(".m-search-user").autocomplete({
        source: availableTags
    });
    $("#search-box").hide();
    $("#add-user").click(function(){
        $("#search-box").show();
        var obj=$("#search-box a"),state=1;
        console.log(obj);
        $.each(obj,function(){
            if($(this).attr("data")==$('#m-search-user').val()){
                alert("已添加了该考试人员");
                state=0;
            }
        });
        if(state){
            var arr=[];
            arr.push($('#m-search-user').val());
            $.post("/userexam/ajax/setExamUsersByEmails",{
                examId:$("#exam-id").val(),
                emails:arr
            },function(data){
                if(data.success){
                    $("#search-box").append('<a data="'+$('#m-search-user').val()+'">'+$('#m-search-user').val()+'&nbsp;<i class="fa fa-times" aria-hidden="true"></a>');
                }else{
                    alert("添加失败");
                }
            });
        }
    });
    $("#search-exempt-box").hide();
    $("#add-exempt-user").click(function(){
        $("#search-exempt-box").show();
        var obj=$("#search-exempt-box a"),state=1;
        $.each(obj,function(){
            if($(this).attr("data")==$('#exam-exempt-users').val()){
                alert("已添加了该考试人员");
                state=0;
            }
        });
        if(state){
            var arr=[];
            arr.push($('#exam-exempt-users').val());
            $.post("/userexam/ajax/setExamExemptUsersByEmails",{
                examId:$("#exam-id").val(),
                emails:arr
            },function(data){
                if(data.success){
                    $("#search-exempt-box").append('<a data="'+$('#exam-exempt-users').val()+'">'+$('#exam-exempt-users').val()+'&nbsp;<i class="fa fa-times" aria-hidden="true"></a>');
                }else{
                    alert("添加失败");
                }
            });
        }
    });
    $("#search-leave-box").hide();
    $("#add-leave-user").click(function(){
        $("#search-leave-box").show();
        var obj=$("#search-leave-box a"),state=1;
        $.each(obj,function(){
            if($(this).attr("data")==$('#exam-leave-users').val()){
                alert("已添加了该考试人员");
                state=0;
            }
        });
        if(state){
            var arr=[];
            arr.push($('#exam-leave-users').val());
            $.post("/userexam/ajax/setExamLeaveUsersByEmails",{
                examId:$("#exam-id").val(),
                emails:arr
            },function(data){
                if(data.success){
                    $("#search-leave-box").append('<a data="'+$('#exam-leave-users').val()+'">'+$('#exam-leave-users').val()+'&nbsp;<i class="fa fa-times" aria-hidden="true"></a>');
                }else{
                    alert("添加失败");
                }
            });
        }
    });
    $.get("/user/ajax/getDepts",function(data){
       $.each(data.data,function(k,v){
          $("#m-dept").append('<option id="'+v.id+'">'+v.name+'</option>');
       });
    });
    $("#m-dept").change(function () {
        var id=$(this).children("option:selected").attr("id");
        $.get("/user/ajax/getDeptUser?deptId="+id,function(obj){
            $("#m-checkbox-body").empty();
            $.each(obj.data,function(k,v){
                $("#m-checkbox-body").append('<p class="checkbox-line"><input type="checkbox"  name="userName" id="'+v.id+'">&nbsp;'+v.userName+ '</p>');
            });
        });
    });
    $.get("/user/ajax/getDeptUser?deptId=1",function(obj){
        $.each(obj.data,function(k,v){
            $("#m-checkbox-body").append('<p class="checkbox-line"><input type="checkbox"  name="userName" id="'+v.id+'">&nbsp;'+v.userName+ '</p>');
        });
    });

//    $("#m-select-all").bind('change',function(){
//        if($(this).attr("checked")==true){
//            $(".j-checkbox").attr("checked",true);
//        }else{
//            $(".j-checkbox").attr("checked",false);
//        }
//
//    })
    $("#m-select-all").click(function(){
        $("input[name='userName']").prop("checked",$(this).prop("checked"));
    });
    $("#submit-user").click(function(){
        var obj=$("input[name='userName']:checked");
        if(obj.length!=0){
            $.post("/userexam/ajax/setExamUsers",{
                examId:$("#exam-id").val(),
                userIds:getUserId(obj)
            },function(data){
                if(data.success){
                    alert("设置考试人员成功");
                }else{
                    alert("设置考试人员失败");
                }
            });
        }else{
            alert("当前考试人员为0，请设置考试人员");
        }
    });
    function getUserId(obj) {
        var arr=[];
        obj.each(function(){
            arr.push(($(this)[0].id)*1);
        });
        return arr;
    }



</script>
</body>
</html>

