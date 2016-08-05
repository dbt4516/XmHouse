<%--
  Created by IntelliJ IDEA.
  User: suhq
  Date: 2016/8/3
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/question.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="m-left">
    <jsp:include page="../sidebar.jsp"></jsp:include>
</div>
<div class="m-right">
    <input type="hidden" id="userExamId" value="${userExamId}"/>
    <div class="m-inner">
        <h4>考卷详细情况</h4>
        <%--<button class="btn btn-info" id="submit-ans">提交答案</button>--%>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
<script>
    function options(obj) {
        var str1 = '<div class="m-choiceBox"><ul class="m-choiceList clearfix">';
        $.each($.parseJSON(obj), function (k, v) {
            str1 += '<li><label><div class="m-option">' + k + '.&nbsp;&nbsp;</div><div class="m-choice"><p>' + v + '</p></div></label></li>';
        });
        str1 += '</ul></div>';
        return str1;
    }

    function setOptions(data) {
        $.each(data, function (key, value) {
            var str = '';
            str += '<div class="m-list clearfix"><div class="m-total clearfix"><div class="m-queNum">' + (key + 1) + '</div><div class="m-description">';
            if (value.type == 1) {
                str += '<div class="m-duo"><span>多选题</span></div><div class="m-title">' + value.title + '</div></div></div>';
                str += options(value.option);
            } else if (value.type == 0) {
                str += '<div class="m-dan"><span>单选题</span></div><div class="m-title">' + value.title + '</div></div></div>';
                str += options(value.option);
            } else if (value.type == 3) {
                str += '<div class="m-pan"><span>判断题</span></div><div class="m-title">' + value.title + '</div></div></div>';
            } else {
                str += '<div class="m-pan"><span>填空题</span></div><div class="m-title">' + value.title + '</div></div></div>';
            }
            if(value.isRight){
                str += '<div class="m-user-ans">用户答案：<span style="color:green;">'+value.userAns+'</span></div></div>';
            }else{
                str += '<div class="m-user-ans">用户答案：<span style="color:red;">'+value.userAns+'</span>&nbsp;&nbsp;正确答案：<span style="color:green;font-weight: bold;">'+value.correctAns+'</span></div></div>';
            }
            $(".m-inner").append(str);
        });
    }

    $.get("/exam/ajax/getUserExamQuestions",{
        userExamId:$("#userExamId").val()
    },function(data){
        setOptions(data.data);
    });
</script>
</body>
</html>

