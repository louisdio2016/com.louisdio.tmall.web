<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
    function submitAddForm() {
        var end = false;
        $("input#menuInAddModal").each(function () {
            var flag = $(this).prop("checked");
            end = flag;
            if(flag){
                $("form#addForm").submit();
                return false;
            }
        });
        if(end){
            return;
        }
        alert("至少选择一种权限");
    }
</script>
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" style="height: 400px;width: 400px">
        <div class="modal-content editDivInListAdminUser">
            <div class="modal-header">
                <span>添加角色</span>
            </div>
            <div class="modal-body">
                <form  id="addForm" class="addForm" action="/tmall_ssm/admin_role_add" method="post">
                    <table class="addTable">
                        <tr>
                            <td style="width: 60px">角色名:</td>
                            <td><input id="nameInAddModal" name="role.name" type="text"></td>
                        </tr>
                        <tr>
                            <td>所属权限:</td>
                            <td id="allMenus">
                                <c:forEach items="${menuList}" var="m">
                                    <input type="checkbox" id="menuInAddModal" name="menuIds" mid="${m.id}" value="${m.id}">
                                    <span>${m.name}</span>&nbsp;&nbsp;&nbsp;
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
