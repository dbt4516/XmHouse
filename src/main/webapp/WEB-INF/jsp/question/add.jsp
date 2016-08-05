<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: suhq
  Date: 2016/7/23
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../common.jsp"></jsp:include>
</head>
<jsp:include page="../header.jsp"></jsp:include>
<div class="m-left">
    <jsp:include page="../sidebar.jsp"></jsp:include>
</div>
<div class="m-right">
    <div class="m-inner">
        <div id="success" class="alert alert-success m-success">
            <a href="#" class="close" data-dismiss="alert">&times;</a>
            <strong>添成成功！</strong>您现在可以继续添加。
        </div>
        <%--下拉框形式 传value即id到后台即可--%>
        <form class="m-width m-border-solid clearfix">
            <legend class="m-legend">添加题目</legend>
            <div class="form-inline m-margin m-border">
                <div class="form-group">
                    <label>选择课程：</label>
                    <select class="form-control" name="" id="course">
                        <c:forEach items="${courses}" var="course">
                            <option value="${course.id}">${course.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group m-adjust-left">
                    <label for="type">选择题型：</label>
                    <select class="form-control" name="type" id="type">
                        <c:forEach items="${questionType}" var="item">
                            <option value="${item.value}">${item.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group m-border">
                <label class="m-margin">编辑题目：</label>
                <script id="container" name="content" type="text/plain"></script>
            </div>
            <div class="form-group m-border" id="add-title">
                <button type="button" class="btn btn-info m-margin" id="add-options">
                    添加选择题选项
                </button>
                <button type="button" class="btn btn-danger m-margin" id="delete-options">
                    删除选择题选项
                </button>
                <span class="m-danger m-margin">选项最多添加10个</span>
                <div class="form-inline" id="options"></div>
            </div>
            <div class="form-inline m-margin m-border">
                <div class="form-group">
                    <label>题目答案：</label>
                    <input type="text" class="form-control" id="answer" placeholder="输入答案,多个答案以;分隔"/>
                </div>
                <div class="form-group m-margin">
                    <label class="radio-inline">是否上线：</label>
                    <label class="radio-inline">
                        <input type="radio" name="isOnline" value="1" checked> 上线
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="isOnline" value="0"> 下线
                    </label>
                </div>
            </div>
            <input type="button" class="btn btn-info m-fr" id="create-title" value="创建"/>
        </form>

        <form class="m-width m-border-solid m-margin-top clearfix"
              action="${pageContext.request.contextPath}/question/automatic" enctype="multipart/form-data"
              method="post">
            <legend class="m-legend">自动添加题目</legend>
            <div class="form-inline m-border">
                <div class="form-inline">
                    <div class="form-group">
                        <input accept=".txt" name="file" type="file" style="display:none" id="input-file">
                        <div class="input-group">
                            <input id="file-url" class="form-control" type="text" style="width:500px;">
                            <div class="input-group-addon btn btn-info" id="file">题目文件</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btn btn-info" value="提交"/>
                        <a href="${pageContext.request.contextPath}/question/output" class="btn btn-info">导出题库</a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"></script>
<script src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
    $(function () {
        $("#type").change(function () {
            var option = $(this).children('option:selected').val();
            if (option == 2 || option == 3) {
                $("#add-title").css("display", "none");
            } else {
                $("#add-title").css("display", "block");
            }
        });
        function dealOpt(){
            var len=$("#options").children("div").length+65;
            var obj={};
            if(len!=0){
                for(var i=65;i<len;i++) {
                    obj[String.fromCharCode(i)] = $("#option" + String.fromCharCode(i)).val();
                }
                return JSON.stringify(obj);
            }else{
                return {};
            }
        }
        var editor = UE.getEditor('container');
        $('#create-title').click(function () {
            option=dealOpt();
            $.post('${pageContext.request.contextPath}/question', {
                courseId: $('#course').val(),
                type: $('#type').val(),
                option: option,
                ans: $('#answer').val(),
                isOnline: $("input[name='isOnline']:checked").val(),
                title: UE.getEditor('container').getContent()
            }, function (status, data) {
                if (status && data) {
                        $('#success').slideDown();
                        setTimeout(function () {
                            $('#success').fadeOut(1000);
                        },5000);
                } else {
                    alert('添加失败,请稍后重试!');
                }
            });
        });
    });
</script>
</body>
</html>