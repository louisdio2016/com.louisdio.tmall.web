<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
    function submitAddForm() {
        $("form#addForm").submit();
        return;
    }
</script>
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" style="height: 400px;width: 400px">
        <div class="modal-content editDivInListAdminUser">
            <div class="modal-header">
                <span>添加权限</span>
            </div>
            <div class="modal-body">
                <form  id="addForm" class="addForm" action="/tmall_ssm/admin_menu_add" method="post">
                    <table class="addTable">
                        <tr>
                            <td style="width: 60px">权限名:</td>
                            <td><input id="nameInAddModal" name="name" type="text"></td>
                        </tr>
                        <tr>
                            <td>SIGN:</td>
                            <td><input type="text" id="signInAddModal" name="sign"></td>
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
