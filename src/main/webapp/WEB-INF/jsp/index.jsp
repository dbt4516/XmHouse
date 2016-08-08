<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.cnc.xmhouse.service.HouseService" %>
<%@ page import="com.cnc.xmhouse.entity.THouse" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page trimDirectiveWhitespaces="true" %>
<head>

    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta charset="UTF-8">
    <c:set var="ctx" value="${pageContext.request.contextPath }"/>
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/online.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/css/index.css" rel="stylesheet" type="text/css"/>

<title>厦门房产成交信息</title>
</head>
<body>
<h1 style="margin-bottom: 30px;">
    今日房产成交列表
</h1>
<%
    ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
    HouseService houseService = (HouseService) ac.getBean("houseService");
%>
<div class="m-exam">
    <table class="table table-hover">
        <tr>
            <th>地点</th>
            <th>面积（M<sup>2</sup>）</th>
            <th>确认时间</th>
        </tr>
        <% for (THouse h : houseService.getToday()) {%>
        <tr>
            <td><%=h.getLocation()%>
            </td>
            <td><%=String.format("%.2f", h.getArea())%>
            </td>
            <td><%=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(h.getTs().getTime()))%>
            </td>
        </tr>
        <%}%>
    </table>
</div>
<div style="margin-top: 30px">
    访客IP： <%=request.getRemoteAddr() %>
</div>
</body>


