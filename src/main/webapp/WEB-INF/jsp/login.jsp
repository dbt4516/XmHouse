<%--
  Created by IntelliJ IDEA.
  User: suhuoqiang
  Date: 2016/7/22
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>网宿慕课在线考试系统</title>
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta charset="UTF-8">
    <meta name="description" content="网宿在线考试系统" />
    <meta name="keywords" content="网宿，考试，CDN" />
    <meta name="author" content="考试系统第4小组" />
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/online.min.css" rel="stylesheet" type="text/css"/>
</head>
<body class="page-login">
<main class="page-content">
    <div class="page-inner">
        <div id="main-wrapper">
            <div class="row">
                <div class="col-md-3 center">
                    <div class="login-box">
                        <a href="javascript:void(0)" class="logo-name text-lg text-center">网宿慕课</a>
                        <p class="text-center m-t-md">在线考试平台</p>
                        <form class="m-t-md" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
                            <span class="m-error">${authError}</span>
                            <div class="form-group">
                                <input type="text" class="form-control" name="j_username" placeholder="请输入您的邮箱"
                                       pattern="^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$" title="邮箱正确格式：xxx@xxx.xxx" required>
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" name="j_password" placeholder="请输入您的密码" pattern="^[a-zA-Z]\w{5,17}$" title="以字母开头，长度在6~18之间，只能包含字符、数字和下划线" required>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-success btn-block">登陆</button>
                            </div>
                            <div class="form-group">
                                <label class="checkbox">
                                    <input id="remember_me" name="_spring_security_remember_me" type="checkbox"/> 记住密码
                                    <a href="forgot.html">忘记密码?</a>
                                </label>
                            </div>
                        </form>
                        <p class="text-center m-t-xs text-sm">2016 <i class="fa fa-copyright"></i> 考试系统第四小组.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>
