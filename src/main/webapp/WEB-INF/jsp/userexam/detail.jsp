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
    <input type="hidden" id="exam-id" value="${exam.id}"/>
    <input type="hidden" id="exam-duration" value="${exam.duration}"/>
    <div class="m-inner">
        <h4 class="m-course-name">考试</h4>
    </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
<script>
    function UnixToDate(unixTime) {
        var date = new Date(unixTime);
        return formatDate(date);
    }
    function formatDate(date) {
        return date.toLocaleDateString() + " " + ((date.getHours() < 9) ? "0" + date.getHours() : date.getHours()) + ":" + ((date.getMinutes() < 9) ? "0" + date.getMinutes() : date.getMinutes());
    }
    function setExamResult(data){
        var str='',status;
        if(data.status==1){
            status="通过";
        }else if(data.status==-1){
            status="不及格";
        }else if(data.status==0){
            status="未考试";
        }else if(data.status==2){
            status="免考";
        }else{
            status="请假";
        }
        var score = data.score;
        if(!score)
            score = '';
        str+='<div class="m-exam-list"><div class="m-exam-title clearfix"><div class="m-exam-left">'+data.cName+'<span>总分:100</span></div><div class="m-exam-right">考试成绩:<span>&nbsp;'+score+'&nbsp;</span>分</div></div><div class="m-exam-result"><p class="exam-date clearfix">试卷提交截止时间：<span>'+UnixToDate(data.eet)+'<br/><span class="m-notice">请务必在截止时间前提交，截止时间后提交不再计分</span></span></p><p class="exam-time">限定时间：<span>90</span>分钟</p><p class="exam-result">考试情况：<span>'+status+'</span></p></div><a href="/exam/detail/'+data.ueid+'" class="btn btn-info">查看详情</a><a href="/exam/paper/'+data.eid+'" class="btn btn-info" style="float:right;">进入考试</a></div>';
        $(".m-inner").append(str);
    }
    $.get("ajax/getUserExam?page=0&perpage=9",function(data){
        console.log(data.data);
        $.each(data.data,function(k,v){
            setExamResult(v);
//            $(".m-course-name").text(v.cName);
        });
    });
</script>
</body>

</html>

