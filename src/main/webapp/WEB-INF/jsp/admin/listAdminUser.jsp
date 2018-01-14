<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<title>管理员账号管理</title>
<script>
    //            $.post(
    //                page,
    //                {"auid":auid},
    //                function (data) {
    //                    var json = data;
    //                    $("#idInModal").html(json.id);
    //                    $("#nameInModal").html(json.name);
    //                    $("#timeInModal").html(json.createtime);
    //                    $("#editModal").modal("show");
    //                },
    //                "json"
    //            );
    $(function () {
        $("a span.glyphicon-edit").click(function (e) {
            var auid = $(this).attr("auid");
            var page = "admin_user_edit";
            $.ajax(
                {
                    url: page,
                    type: "GET",
                    data: {"auid": auid},
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    success: function (data) {
                        var json = data;
                        var date ;
                        $("#idInModal").val(json.id);
                        $("#nameInModal").val(json.name);
                        //创建日期不为空则格式化时间
                        if (json.createtime != null){
                            var createtime = new Date(json.createtime);
                            date = dateFtt("yyyy-MM-dd hh:mm:ss",createtime);
                        }
                        $("#timeInModal").val(date);
                        //实现自动勾选role
                        var roles = json.roles;
                        //将checkbox转为数组，转换后每个对象为dom对象
                        var roleArray = $("input#roleInModal").toArray();
                        //遍历数组
                        for(var i=0;i<roleArray.length;i++){
                            //转换为jQuery对象
                            var roleobj = $(roleArray[i]);
                            //初始化，全部为false
                            roleobj.prop("checked",false);
                            //遍历每个role,id与checkbox的rid相比较
                            $.each(roles,function (n,item) {
                               if(item.id == roleobj.attr("rid")){
                                   roleobj.prop("checked",true);
                                   return;
                               }
                            });
                        }
                        //下面的方法无效，因为在ajax中的$(this)为全局变量，代表ajax返回参数
//                        $("input#roleInModal").each(function () {
//                            $(this).prop("checked",false);
//                            $.each(roles,function (n,item) {
//                                if(item.id == $(this).attr("rid")){
//                                    $(this).prop("checked",true);
//                                    return;
//                                }
//                            });
//                        });
                        //显示modal
                        $("#editModal").modal("show");
                    }
                });
            e.stopPropagation();
            return false;
        });
        //显示添加Modal
        $("div button.btn-xs").click(function (e) {
            $("#addModal").modal("show");
            e.stopPropagation();
        });
    });
</script>
<div class="workingArea">
    <h1 class="label label-info">管理员账号管理</h1>
    <br>
    <br>
    <!--嵌套三个div：表格，page，新增分类-->
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>账号</th>
                <th>创建日期</th>
                <th>角色</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${adminUsers}" var="adminUser">
                    <tr>
                        <td>${adminUser.id}</td>
                        <td>${adminUser.name}</td>
                        <td><fmt:formatDate value="${adminUser.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td>
                            <c:forEach items="${adminUser.roles}" var="role" varStatus="index">
                                <c:if test="${index.last == true}">
                                    ${role.name}
                                </c:if>
                                <c:if test="${index.last != true}">
                                    ${role.name},
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <a href="#nowhere" ><span class="glyphicon glyphicon-edit" auid="${adminUser.id}"></span></a>
                        </td>
                        <td>
                            <a deleteLink="true" href="admin_user_delete?aucid=${adminUser.id}"><span class="glyphicon glyphicon-trash"></span></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
            <button class="btn btn-primary btn-xs pull-left">添加管理员</button>
        </div>
    </div>
    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp"%>
    </div>
<%@include file="../include/admin/adminFooter.jsp"%>
