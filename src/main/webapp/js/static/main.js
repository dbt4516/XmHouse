/**
 * Created by suhq on 2016/7/22.
 */
    // 触发搜索功能
    $('.show-search').click(function () {
        $('.search-form').css('margin-top', '0');
    });

    $('.close-search').click(function () {
        $('.search-form').css('margin-top', '-60px');
    });

    // 触发全屏
    function toggleFullScreen() {
        if ((document.fullScreenElement && document.fullScreenElement !== null) ||
            (!document.mozFullScreen && !document.webkitIsFullScreen)) {
            if (document.documentElement.requestFullScreen) {
                document.documentElement.requestFullScreen();
            } else if (document.documentElement.mozRequestFullScreen) {
                document.documentElement.mozRequestFullScreen();
            } else if (document.documentElement.webkitRequestFullScreen) {
                document.documentElement.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
            }
        } else {
            if (document.cancelFullScreen) {
                document.cancelFullScreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitCancelFullScreen) {
                document.webkitCancelFullScreen();
            }
        }
    }

    $('.toggle-fullscreen').click(function () {
        toggleFullScreen();
    });

    //左边菜单切换
    $('.sidebar-menu ul li a').click(function () {
        $(this).addClass("active").parent("li").siblings("li").find("a").removeClass("active")
    });
    $('.sidebar-pusher a').click(function () {
        if ($(".m-left").css("display") == "none") {
            $(".m-left").css("display", "block");
        } else {
            $(".m-left").css("display", "none");
        }
    });
    var num = 65;
    $("#add-options").click(function () {
        addOption();
    });

    //添加option
    function addOption(val) {
        var str = null;
        if (val) {
            str = '<div class="input-group m-margin m-adjust-left"><div class="input-group-addon">选项' + String.fromCharCode(num) + '.</div><input class="form-control" id="option' + String.fromCharCode(num) + '" type="text" value="' + val + '"></div>';
        } else {
            str = '<div class="input-group m-margin m-adjust-left"><div class="input-group-addon">选项' + String.fromCharCode(num) + '.</div><input class="form-control" id="option' + String.fromCharCode(num) + '" type="text"></div>';
        }
        var len = $("#options").children("div").length;
        if (len < 10) {
            $("#options").append(str);
            num++;
        } else {
            $(".m-danger.m-margin").css("display", "inline-block");
        }
    }
    //初始化option JSON对象
    $.each($.parseJSON($('#question-option').val()),function(k,v){
        addOption(v);
    });

    $("#delete-options").click(function () {
        var len = $("#options").children("div").length;
        if (len == 1) {
            $(".input-group.m-margin.m-adjust-left").remove();
            num = 65;
        } else if (len > 1 && len <= 10) {
            $(".input-group.m-margin.m-adjust-left").last().remove();
            num--;
        } else {
            alert("当前没有选项，无法执行删除操作");
        }
    });









