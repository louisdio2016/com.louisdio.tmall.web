<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" autoFlush="false" buffer="500kb" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<title>权限管理</title>
<script>
    $(function () {
        //显示添加Modal
        $("div button.btn-xs").click(function (e) {
            $("#addModal").modal("show");
            e.stopPropagation();
        });
        //ajax删除
        $("a[mid]").unbind("click").click(function (e) {
            var deleteLink = $(this).attr("deleteLink");
            if("true"==deleteLink){
                var confirmDelete = confirm("确认要删除");
                if(confirmDelete){
                    var mid = $(this).attr("mid");
                    var page = $(this).attr("href");
                    $.post(
                        page,
                        {"mid":mid},
                        function (result) {
                            if("success" == result){
                                //刷新当前页面，用户体验更好
                                location.reload();
                            }else{
                                alert("删除失败，请确认没有角色具有该权限。");
                            }
                        }
                    );
                    return false;
                }else {
                    e.stopPropagation();
                    return false;
                }
            }

        });
        //点击edit,显示modal,ajax传值
        $("a span.glyphicon-edit").click(function (e) {
            var mid = $(this).attr("mid");
            var page = "admin_menu_edit";
            $.post(
                page,
                {"mid":mid},
                function (data) {
                    $("#nameInModal").val(data.name);
                    $("#idInModal").val(data.id);
                    var date;
                    //创建日期不为空则格式化时间
                    if (data.createtime != null){
                        var createtime = new Date(data.createtime);
                        date = dateFtt("yyyy-MM-dd hh:mm:ss",createtime);
                    }
                    $("#timeInModal").val(date);
                    $("#signInModal").val(data.sign);
                    $("#editModal").modal("show");
                },
                "json"
            );
            e.stopPropagation();
            return false;
        });
    });
</script>
<div class="workingArea">
    <h1 class="label label-info">权限管理</h1>
    <br>
    <br>
    <!--嵌套三个div：表格，page，新增分类-->
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>权限名称</th>
                <th>创建日期</th>
                <th>所属角色</th>
                <th>SIGN</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${menuList}" var="m">
                <tr>
                    <td>${m.id}</td>
                    <td>${m.name}</td>
                    <td><fmt:formatDate value="${m.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                    <td>
                        <c:forEach items="${m.roles}" var="mr" varStatus="index">
                            <c:if test="${index.last == true}">
                                ${mr.name}
                            </c:if>
                            <c:if test="${index.last != true}">
                                ${mr.name},
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        ${m.sign}
                    </td>
                    <td>
                        <a href="#nowhere" ><span class="glyphicon glyphicon-edit" mid="${m.id}"></span></a>
                    </td>
                    <td>
                        <a deleteLink="true" href="admin_menu_delete" mid="${m.id}" ><span class="glyphicon glyphicon-trash"></span></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div>
            <button class="btn btn-primary btn-xs pull-left">添加权限</button>
        </div>
    </div>
    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp"%>
    </div>
    <%@include file="../include/admin/MenuFooter.jsp"%>
