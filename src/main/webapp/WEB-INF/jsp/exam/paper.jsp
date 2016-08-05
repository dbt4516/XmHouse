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
        <h4><c:if test="${exam.isMock eq 1}">${exam.name}&nbsp;<label class="label label-info">模拟考</label></c:if><c:if test="${exam.isMock eq 0}">${exam.name}<label class="label label-info">正式考</label></c:if></h4>
        <button class="btn btn-info" id="submit-ans">提交答案</button>
        <div class="m-time">
            <span id="m-h">00时</span>
            <span id="m-m">00分</span>
            <span id="m-s">00秒</span>
        </div>
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
                str += '<div class="m-duo"><span>多选题</span></div><div class="m-title" id="' + value.id + '">' + value.title + '</div></div></div>';
                str += options(value.option);
            } else if (value.type == 0) {
                str += '<div class="m-dan"><span>单选题</span></div><div class="m-title" id="' + value.id + '">' + value.title + '</div></div></div>';
                str += options(value.option);
            } else if (value.type == 3) {
                str += '<div class="m-pan"><span>判断题</span></div><div class="m-title" id="' + value.id + '">' + value.title + '</div></div></div>';
            } else {
                str += '<div class="m-pan"><span>填空题</span></div><div class="m-title" id="' + value.id + '">' + value.title + '</div></div></div>';
            }
            str += '<div class="m-user-ans">答案：<input class="form-control m-que-ans" type="text" id="'+value.id+'"/></div></div>';
            $("#submit-ans").before(str);
        });
    }
    $.get("/userexam/ajax/userStartExam",{
        examId:$("#exam-id").val()
    },function(data){
        console.log(data);

        if(data.code==0 || data.code==-1){
            if(data.questions){
                setOptions(data.questions);
            }else{
                alert("当前考试无考题，请联系管理员");
                window.location.href="/userexam/detail";
            }
        }else if(data.code==1){
            alert("您已经通过这次考试，不需要重复考试！");
            window.location.href="/userexam/detail";
        }else if(data.code==2){
            alert("这次考试您不用参与!");
            window.location.href="/userexam/detail";
        }else if(data.code==3){
            alert("由于您请假，请参加下一次课程考试！");
            window.location.href="/userexam/detail";
        }else{
            alert("还未到考试时间，请耐心等候！")
            window.location.href="/userexam/detail";
        }

    });
    function collectionAns(obj){
        var arr=[];
        $.each(obj,function(){
            var obj1={};
            if($(this).attr("id")){
                obj1.questionId=$(this).attr("id");
            }else{
                obj1.questionId="";
            }
            if($(this).val()){
                obj1.ans=$(this).val();
            }else{
                obj1.ans="";
            }
            arr.push(JSON.stringify(obj1));
            console.log(arr);
        });
        return arr;
    }

    $("#submit-ans").click(function(){
        $.post("/userexam/ajax/userFinishExam",{
            examId:$("#exam-id").val(),
            list:collectionAns($(".m-que-ans"))
        },function(data){
            if(data.success){
                alert("提交成功");
                window.location.href="/userexam/detail";
            }else{
                alert("提交失败");
            }
        })
    })
    var NowTime = new Date();
    var EndTime =new Date(NowTime.getTime()+1000*$("#exam-duration").val()*60);
    function GetRTime(){
        NowTime = new Date();
        var t =EndTime.getTime() - NowTime.getTime();
        var h=Math.floor(t/1000/60/60%24);
        var m=Math.floor(t/1000/60%60);
        var s=Math.floor(t/1000%60);
        //触发事件
        if(h==0 && m<=30){
            $(".m-time").css("background-color","red");
        }
        if(h==0 && m==0 && s==0){
            $.post("/userexam/ajax/userFinishExam",{
                examId:$("#exam-id").val(),
                list:collectionAns($(".m-que-ans"))
            },function(data){
                if(data.success){
                    alert("自动提交成功");
                    window.location.href="/userexam/detail";
                }
            });
            clearInterval(timer);
        }
        document.getElementById("m-h").innerHTML = h + "时";
        document.getElementById("m-m").innerHTML = m + "分";
        document.getElementById("m-s").innerHTML = s + "秒";
    }
    var timer=setInterval(GetRTime,0);
</script>
</body>
</html>
