<!doctype html>
<%--
  Created by IntelliJ IDEA.
  User: hongzhan
  Date: 2016/5/18
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta charset="UTF-8">
    <c:set var="ctx" value="${pageContext.request.contextPath }"/>
    <link rel="stylesheet" type="text/css"  href="${ctx}/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css"  href="${ctx}/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css"  href="${ctx}/css/jquery-ui.css" />
    <link rel="stylesheet" type="text/css"   href="${ctx}/css/jquery.datetimepicker.css">

    <script src="${ctx}/js/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/statis/jquery.datetimepicker.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery/jquery-ui.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/statis/house_report.js" type="text/javascript"></script>
    <script src="${ctx}/js/statis/highcharts.js" type="text/javascript"></script>
    <script>
        var ctx= "${ctx}";
    </script>
    <style>
        div{
            margin-bottom: 10px;
        }
        span{
            font-weight: 600;
        }
    </style>
    <title>数据报告</title>
</head>
<body style="margin: 15px;max-width: 650px">
<div id="loading"
     style="display: none; overflow: auto; z-index: 9999; background-color: rgba(0,0,0,.3);padding:0;
             margin:0;top:0;left:0;background-position: center center; background-repeat: no-repeat;
             height: 100%; width: 100%; position: fixed; background-image: url(${ctx}/img/loading.gif;)">
</div>

<h1 style="margin-bottom: 30px;">
    数据报告
</h1>
<div class="nav-search" id="nav-search" style="left:2px;">
    <form class="form-search" onsubmit="return false;">
        <label for="start"  >时间段：</label>
        <input id="start" datepicker class="nav-search-input" >
        <span style="margin:0px 5px;">—</span>
        <input id="end" datepicker class="nav-search-input">

        <button class="btn btn-xs btn-info" onclick="search()" style="margin-bottom: 4px;">
            生成报告
        </button>
    </form>
</div>
<div>
    各位，爬虫是8月5日中午开始跑的，8月8日上午为纪念我国与印度尼西亚恢复外交关系26周年，彰显本站大国观，爬虫停机两个钟[手动再见]。如不见弃就姑且用数据一唠吧。<br>
    先向诸位汇报整体成交情况，这段时间厦门房市成交 <span id="saleSuite"></span> 套，总成交面积 <span id="saleArea"></span> 。<br>
    之后带大家看到的是成交趋势。在 <span id="mostSaleDay"></span> 这天厦门售出的房子最多，达到 <span id="mostSaleDaySuite"></span> 套。分区域的走势见下图。
</div>
<div class="col-xs-12">
    <h3 class="header smaller lighter blue">
        <span>成交量趋势图</span>
    </h3>
    <div id="trendLine"style="height: 300px;width: 100%">
    </div>
</div>

<div>
    接下来带大家看到的是售房的区域分布情况。可以看到 <span id="mostSaleLoc"></span> 售出的房子最多[共体时艰]，卖出了 <span id="mostSaleLocSuite"></span> 套，占到这阵子售出房产的 <span id="mostInAllRatio"></span>% 。<br>
    房产专家友情提醒购房不宜扎堆，建议大家优先选择当前较为冷门的 <span id="leastSaleLoc"></span> 的房子，避免不动产市场局部过热[有理有据令人信服]。<br>
    详细比例情况大家可以在下面的饼图中具体看到。
</div>
<div class="col-xs-12">
    <h3 class="header smaller lighter blue">
        <span>六区售楼比例图</span>
    </h3>
    <div id="salePie"style="height: 300px;">
    </div>
</div>

<div>
    买房一事除了地点之外，面积大小也是大家关心的重点。这段时间内厦门售出的最大套的房子是 <span id="biggesDate"></span> 于 <span id="biggestLoc"></span> 售出的，面积达到 <span id="biggestArea"></span> M<sup>2</sup>。<br>
    而在该段时间内成交的房子平均面积仅为 <span id="avgArea"></span> M<sup>2</sup>，最大房的面积是它的 <span id="bigAvgRatio"></span> 倍。<br>
    一位不具姓名的评论员表示 “<span id="areaCommet"></span>”
</div>
<div class="col-xs-12">
    <h3 class="header smaller lighter blue">
        <span>成交面积分布图</span>
    </h3>
    <div id="areaDis"style="height: 300px;">
    </div>
</div>
<div>
    <a href="${ctx}/index" style="margin-right: 10px">今日成交</a> <a href="${ctx}/statis"style="margin-right: 10px">数据报告</a>
    <a href="${ctx}/history" style="margin-right: 10px">往日数据查询</a><a href="http://www.xmtfj.gov.cn/"style="margin-right: 10px">厦门房产网</a>
</div>

<div style="margin-top: 30px">
    访客IP： <%=request.getRemoteAddr() %>
</div>
</body>


</html>