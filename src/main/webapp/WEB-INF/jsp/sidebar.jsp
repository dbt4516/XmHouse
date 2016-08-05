<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="m-user">
    <div class="user-img"><img class="img-circle img-thumbnail" src="${pageContext.request.contextPath}/img/suhq.jpg"/>
    </div>
    <h5><a href="#"><sec:authentication property="principal.name"></sec:authentication></a></h5>
</div>
<div class="sidebar-menu">
    <ul>
        <%--<li><a href="#" class="active"><i class="fa fa-hand-o-right"></i><span>个人信息</span></a></li>--%>
        <li><a href="${pageContext.request.contextPath}/userexam/detail
" <c:if test="${current eq 'myexam'}">class="active"</c:if>><i class="fa fa-edit"></i><span>我的考试</span></a></li>
        <sec:authorize ifAnyGranted="ROLE_SADMIN,ROLE_ADMIN">
            <li><a href="${pageContext.request.contextPath}/exam/list"
                   <c:if test="${current eq 'exam'}">class="active"</c:if>><i
                    class="fa fa-list"></i><span>考试管理</span></a>
            </li>
        </sec:authorize>
        <sec:authorize ifAnyGranted="ROLE_SADMIN,ROLE_ADMIN">
            <li><a href="${pageContext.request.contextPath}/course/list"
                   <c:if test="${current eq 'course'}">class="active"</c:if>><i
                    class="fa fa-list-alt"></i><span>课程管理</span></a>
            </li>
        </sec:authorize>
        <sec:authorize ifAnyGranted="ROLE_SADMIN,ROLE_ADMIN">
            <li><a href="${pageContext.request.contextPath}/examStatistics/manage"
                   <c:if test="${current eq 'examManage'}">class="active"</c:if>><i
                    class="fa fa-list-alt"></i><span>成绩管理</span></a>
            </li>
        </sec:authorize>
        <%--<li><a href="#"><i class="fa fa-bar-chart"></i><span>下属成绩</span></a></li>--%>
        <sec:authorize ifAllGranted="ROLE_SADMIN">
            <li><a href="${pageContext.request.contextPath}/super"
                   <c:if test="${current eq 'admin'}">class="active"</c:if>><i
                    class="fa fa-user"></i><span>&nbsp;管理员</span></a>
            </li>
        </sec:authorize>
        <sec:authorize ifAllGranted="ROLE_SADMIN">
            <li><a href="${pageContext.request.contextPath}/log"
                   <c:if test="${current eq 'sadmin'}">class="active"</c:if>><i
                    class="fa fa-circle-thin"></i><span>系统日志</span></a></li>
        </sec:authorize>
    </ul>
</div>
