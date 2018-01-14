<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
    function submitEditForm() {
        $("form#editForm").submit();
        return;
    }
</script>
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" style="height: 400px;width: 400px">
        <div class="modal-content editDivInListAdminUser">
            <div class="modal-header">
                <span>编辑权限</span>
            </div>
            <div class="modal-body">
                <form id="editForm" class="editForm" action="/tmall_ssm/admin_menu_update" method="post" >
                    <table class="editTable">
                        <tr>
                            <td style="width: 60px">
                                ID
                            </td>
                            <td>
                                <input id="idInModal" name="id" readonly="readonly" type="text">
                            </td>
                        </tr>
                        <tr>
                            <td>权限名</td>
                            <td>
                                <input id="nameInModal" type="text" name="name"/>
                            </td>
                        </tr>
                        <tr>
                            <td>SIGN</td>
                            <td>
                                <input id="signInModal" type="text" name="sign"/>
                            </td>
                        </tr>
                        <tr>
                            <td>创建日期</td>
                            <td>
                                <input id="timeInModal" type="text" name="createtime" readonly="readonly"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-info" type="submit" onclick="submitEditForm()">修  改</button>
            </div>
        </div>
    </div>
</div>

