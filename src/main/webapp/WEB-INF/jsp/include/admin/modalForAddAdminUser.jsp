<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
//    验证至少具有一种角色
    function submitAddForm(){
        //验证用户名
        var name = $("#idInAddModal").val();
        name = $.trim(name);
        if (0 == name.length){
            alert("用户名不能为空");
            return;
        }

        var password = $("#passwordInAddModal").val();
        password = $.trim(password);
        if(0 == name.length){
            alert("密码不能为空");
            return;
        }
        var flag = false;
        $("input#roleInAddModal").each(function () {
            var c = $(this).prop("checked");
            flag = c;
            if(c){
                $("form#addForm").submit();
                return false;
            }
        });
        if(flag){
            return;
        }
        alert("请选择至少一种角色");
    }
//$().ready(function() {
//    $("#addForm").validate({
//        submitHandler:function(form){
//            alert("提交事件!");
//            form.submit();
//        },
//        rules:{
//            adminUser.name:{required:true}
//
//        },
//    });
//});
</script>
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" style="height: 400px;width: 400px">
        <div class="modal-content editDivInListAdminUser">
            <div class="modal-header">
                <span>添加管理员用户</span>
            </div>
            <div class="modal-body">
                <form  id="addForm" class="addForm" action="/tmall_ssm/admin_user_add" method="post">
                    <table class="addTable">
                        <tr>
                            <td style="width: 60px">用户名:</td>
                            <td><input id="idInAddModal" name="adminUser.name" type="text"></td>
                        </tr>
                        <tr>
                            <td>密码:</td>
                            <td><input id="passwordInAddModal" name="adminUser.password" type="text"></td>
                        </tr>
                        <tr>
                            <td>所属角色:</td>
                            <td id="allRoles">
                                <c:forEach items="${roles}" var="role">
                                    <input type="checkbox" id="roleInAddModal" name="roleIds" rid="${role.id}" value="${role.id}">
                                    <span>${role.name}</span>&nbsp;&nbsp;&nbsp;
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <%--<button class="btn btn-success">添  加</button>--%>
                <button type="submit" class="btn btn-info" onclick="submitAddForm()">添  加</button>
            </div>
        </div>
    </div>
</div>
