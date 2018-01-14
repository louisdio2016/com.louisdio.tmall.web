<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" autoFlush="false" buffer="500kb" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<title>角色管理</title>
<script>
    $(function () {
        $("div button.btn-xs").click(function (e) {
            $("#addModal").modal("show");
            e.stopPropagation();
        });
        //重复绑定了click,所以先解绑一个click
        $("a[rid]").unbind("click").click(function (e) {
            var deleteLink = $(this).attr("deleteLink");
            if("true"==deleteLink){
                var confirmDelete = confirm("确认要删除");
                if(confirmDelete){
                    var rid = $(this).attr("rid");
                    var page = $(this).attr("href");
                    $.post(
                            page,
                            {"rid":rid},
                            function (result) {
                                if("success" == result){
                                    //刷新当前页面，用户体验更好
                                    location.reload();
                                }else{
                                    alert("删除失败，请确认没有用户具有该角色。");
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
        //弹出editModal
        $("a span.glyphicon-edit").click(function (e) {
            //ajax请求，将checkbox勾选
            var rid = $(this).attr("rid");
            var page = "admin_role_edit";
            $.post(
                page,
                {"rid":rid},
                function (data) {
                    $("#idInModal").val(data.id);
                    $("#nameInModal").val(data.name);
                    var date ;
                    if (data.createtime != null){
                        var createtime = new Date(data.createtime);
                        date = dateFtt("yyyy-MM-dd hh:mm:ss",createtime);
                    }
                    $("#timeInModal").val(date);
                    //实现自动勾选
                    var menus = data.menus;
                    var menuArray = $("input#menuInModal").toArray();
                    for(var i=0;i<menuArray.length;i++){
                        var menuEle = $(menuArray[i]);
                        menuEle.prop("checked",false);
                        $.each(menus,function (n,item) {
                            if(item.id == menuEle.attr("mid")){
                                menuEle.prop("checked",true);
                                return;
                            }
                        });
                    }
                    //显示editModal
                    $("div#editModal").modal("show");
                },
                "json"
            );
        });
    });

</script>
<div class="workingArea">
    <h1 class="label label-info">角色管理</h1>
    <br>
    <br>
    <!--嵌套三个div：表格，page，新增分类-->
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>角色名称</th>
                <th>创建日期</th>
                <th>所属用户</th>
                <th>权限</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${roles}" var="role">
                <tr>
                    <td>${role.id}</td>
                    <td>${role.name}</td>
                    <td><fmt:formatDate value="${role.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                    <td>
                        <c:forEach items="${role.adminUsers}" var="au" varStatus="index">
                            <c:if test="${index.last == true}">
                                ${au.name}
                            </c:if>
                            <c:if test="${index.last != true}">
                                ${au.name},
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach items="${role.menus}" var="menu" varStatus="index">
                            <c:if test="${index.last == true}">
                                ${menu.name}
                            </c:if>
                            <c:if test="${index.last != true}">
                                ${menu.name},
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <a href="#nowhere" ><span class="glyphicon glyphicon-edit" rid="${role.id}"></span></a>
                    </td>
                    <td>
                        <a deleteLink="true" href="admin_role_delete" rid="${role.id}" ><span class="glyphicon glyphicon-trash"></span></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div>
            <button class="btn btn-primary btn-xs pull-left">添加角色</button>
        </div>
    </div>
<div class="pageDiv">
    <%@include file="../include/admin/adminPage.jsp"%>
</div>
<%@include file="../include/admin/RoleFooter.jsp"%>