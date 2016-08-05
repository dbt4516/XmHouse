<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/seajs/sea.js"></script>--%>
<%--<script>--%>
    <%--seajs.config({--%>
        <%--base: "${pageContext.request.contextPath}/js/",--%>
        <%--alias: {--%>
            <%--"jquery": "jquery/jquery.js"--%>
        <%--},--%>
    <%--});--%>
    <%--seajs.use("${pageContext.request.contextPath}/js/static/main");--%>
<%--</script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/static/main.js"></script>
<script src="${pageContext.request.contextPath}/js/boostrap/bootstrap.min.js"></script>
<script>
    $('#notify').click(function () {
        $.get("${pageContext.request.contextPath}/notification/last", function (data, status) {
            if (status) {
                if (data) {
                    //数据返回成功将未读数据置空
                    $('#notification').text("");
                    $('.list-unstyled').html('');
                    $.each(data,function (k,v) {
                        debugger;
                        var diffTime = new Date().getTime() - v.time;
                        var time = calcDifference(diffTime);
                        var s = '';
                        if(time[0]!=0)
                            s = time[0] + '天以前';
                        else if(time[1]!=0)
                            s += time[1] + '小时以前';
                        else if(time[2]!=0)
                            s += time[2] + '分钟以前';
                        else if(time[3]!=0)
                            s += time[3] + '秒以前';
                        $('.list-unstyled').append('<li> <a href="javascript:void(0)"> <div class=""><i class="icon-user"></i></div> <span class="badge badge-roundless badge-default pull-right">' + s + '</span> <p class="task-details">' + v.content + '.</p> </a> </li>');
                    });
                }
            } else {
                console.log("消息加载失败...")
            }
        });
    });

//    //预览未读信息
//    $('#notify').hover(function () {

//    });

    //定时加载当前用户未读消息
    function unReadNum() {
        $.get("/notification/unread", function (data, status) {
            if (status) {
                if (data) {
                    $('#notification').text(data);
                }
            } else {
                console.log("消息加载失败...")
            }
        });
    }
    unReadNum();
    setInterval(unReadNum, 10000);


    /**
     * 计算差距的具体时间
     * @param  {[type]} day [description]
     * @return {[type]}     [description]
     */
    function calcDifference(day) {
        var days = Math.floor(day / (24 * 3600 * 1000));
        var leave1 = day % (24 * 3600 * 1000);
        var hours = Math.floor(leave1 / (3600 * 1000));
        var leave2 = leave1 % (3600 * 1000);
        var minutes = Math.floor(leave2 / (60 * 1000));
        var leave3 = leave2 % (60 * 1000);
        var seconds = Math.round(leave3 / 1000);
        var res = new Array(days, hours, minutes, seconds);
        return res;
    }
</script>
