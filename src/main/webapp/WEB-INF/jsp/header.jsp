<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<header class="m-header">
    <%--<form class="search-form" action="#" method="GET">--%>
        <%--<div class="input-group">--%>
            <%--<input type="text" name="search" class="form-control search-input" placeholder="Search...">--%>
            <%--<span class="input-group-btn">--%>
                          <%--<button class="btn btn-default close-search waves-effect waves-button waves-classic" type="button"><i class="fa fa-times"></i></button>--%>
                      <%--</span>--%>
        <%--</div><!-- Input Group -->--%>
    <%--</form><!-- Search Form -->--%>
    <div class="header">
        <div class="navbar">
            <div>
                <div class="sidebar-pusher">
                    <a href="javascript:void(0);" class="waves-effect waves-button waves-classic push-sidebar">
                        <i class="fa fa-bars"></i>
                    </a>
                </div>
                <div class="logo-box">
                    <a href="${pageContext.request.contextPath}/index" class="logo-text"><span>网宿慕课</span></a>
                </div><!-- Logo Box -->
                <%--<div class="search-button">--%>
                    <%--<a href="javascript:void(0);" class="waves-effect waves-button waves-classic show-search"><i class="fa fa-search"></i></a>--%>
                <%--</div>--%>
                <div class="topmenu-outer">
                    <div class="top-menu">
                        <ul class="nav navbar-nav navbar-left">
                            <li>
                                <a href="javascript:void(0);" class="waves-effect waves-button waves-classic toggle-fullscreen"><i class="fa fa-expand"></i></a>
                            </li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li class="dropdown">
                                <a href="javascript:void(0)" class="dropdown-toggle waves-effect waves-button waves-classic" data-toggle="dropdown" id="notify"><i class="fa fa-bell"></i><span class="badge badge-success pull-right" id="notification"></span></a>
                                <ul class="dropdown-menu title-caret dropdown-lg" role="menu">
                                    <li><p class="drop-title">最近消息</p></li>
                                    <li class="dropdown-menu-list slimscroll tasks">
                                        <ul class="list-unstyled">

                                        </ul>
                                    </li>
                                    <li class="drop-all"><a href="${pageContext.request.contextPath}/notification/list" class="text-center">所有消息</a></li>
                                </ul>
                            </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle waves-effect waves-button waves-classic" data-toggle="dropdown">
                                    <span class="user-name"><sec:authentication property="principal.name"></sec:authentication></span>
                                    <img class="img-circle avatar" src="${pageContext.request.contextPath}/img/suhq.jpg" width="40" height="40" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/j_spring_security_logout_" class="log-out waves-effect waves-button waves-classic">
                                    <span><i class="fa fa-sign-out m-r-xs"></i>退出</span>
                                </a>
                            </li>
                        </ul><!-- Nav -->
                    </div><!-- Top Menu -->
                </div>
            </div>
        </div><!-- Navbar -->
    </div>
</header>
