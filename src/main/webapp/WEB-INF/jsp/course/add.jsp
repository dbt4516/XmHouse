<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common.jsp"></jsp:include>
<jsp:include page="../header.jsp"></jsp:include>

<main class="m-main">
    <div class="m-left">
        <jsp:include page="../sidebar.jsp"></jsp:include>
    </div>
    <div class="m-right">
        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModa2">
            添加课程分类
        </button>
        <div class="modal fade bs-example-modal-sm" id="myModa2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <form method="post" action="${pageContext.request.contextPath}/category/save" enctype="multipart/form-data">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h5 class="modal-title">新建课程分类</h5>
                        </div>
                        <div class="modal-body m-form">
                            <div class="form-inline">
                                <div class="form-group">
                                    <label for="courseName">分类名称：</label>
                                    <input type="text" name="name" class="form-control" placeholder="分类名称">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="reset" class="btn btn-default" data-dismiss="modal" value="取消">
                            <input type="submit" class="btn btn-info" value="添加">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">
            添加课程
        </button>
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form method="post" action="${pageContext.request.contextPath}/course/save" enctype="multipart/form-data">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h5 class="modal-title" id="myModalLabel">新建课程</h5>
                        </div>
                        <div class="modal-body m-form">
                                <div class="form-group">
                                    <div class="form-inline">
                                        <div class="form-group">
                                            <label for="courseName">课程名称：</label>
                                            <input type="text" name="name" class="form-control" id="courseName" placeholder="课程名称">
                                        </div>
                                        <div class="form-group">
                                            <label for="courseTeacher">讲师：</label>
                                            <input type="text" name="teacher" class="form-control" id="courseTeacher" placeholder="讲师名称">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="form-inline">
                                        <div class="form-group">
                                            <label for="category">课程分类：</label>
                                            <select class="form-control" name="categoryId" id="category">
                                                <c:forEach items="${categories}" var="category">
                                                    <option value="${category.id}">${category.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group m-adjust-left">
                                            <input id="input-file" type="file" name="img" style="display:none">
                                            <div class="input-group">
                                                <input id="file-url" name="imgSource" class="form-control" type="text">
                                                <div class="input-group-addon btn btn-info" id="file">课程图片</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="form-inline">
                                        <div class="form-group">
                                            <label class="radio-inline m-adjust" >课程类型：</label>
                                            <label class="radio-inline">
                                                <input type="radio" name="courseType" value="1"> 必修
                                            </label>
                                            <label class="radio-inline">
                                                <input type="radio" name="courseType" value="0"> 选修
                                            </label>
                                        </div>
                                        <div class="form-group">
                                            <label class="radio-inline">是否上线：</label>
                                            <label class="radio-inline">
                                                <input type="radio" name="isOnline" value="1"> 上线
                                            </label>
                                            <label class="radio-inline">
                                                <input type="radio" name="isOnline" value="0"> 下线
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>课程大纲：</label>
                                    <textarea class="form-control" name="outline" rows="3"></textarea>
                                </div>
                                <div class="form-group">
                                    <label>课程目标：</label>
                                    <textarea class="form-control" name="target" rows="3"></textarea>
                                </div>

                        </div>
                            选择文件:<input type="file" name="resources">

                            选择文件:<input type="file" name="resources">
                        <div class="modal-footer">
                            <input type="reset" class="btn btn-default" data-dismiss="modal" value="取消">
                            <input type="submit" class="btn btn-info" value="添加">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%--<form name="course" method="post" action="${pageContext.request.contextPath}/course/save">--%>

            <%--<table>--%>
                <%--<tr>--%>
                    <%--<td><label name="name">课程名</label></td>--%>
                    <%--<td><input name="name" /></td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td>课程类型:</td>--%>
                    <%--<td>--%>
                        <%--<input type="radio" name="courseType" value="0"/>选修 &nbsp; <input type="radio" name="courseType" value="1"/>必修--%>
                    <%--</td>--%>

                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td><label name="outline">大纲</label></td>--%>
                    <%--<td><textarea name="outline"></textarea></td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td><label name="target">目标</label></td>--%>
                    <%--<td><textarea name="target"></textarea></td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td><label name="teacher">老师</label></td>--%>
                    <%--<td><input name="teacher" /></td>--%>
                <%--</tr>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td>是否上线:</td>--%>
                    <%--<td> <input type="radio" name="isOnline" value="0"/>否 &nbsp; <input type="radio" name="isOnline" value="1"/>是--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td >--%>
                        <%--<input type="submit" value="添加"/>--%>
                    <%--</td>--%>
                <%--</tr>--%>
            <%--</table>--%>
        <%--</form>--%>
    </div>
</main>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>
