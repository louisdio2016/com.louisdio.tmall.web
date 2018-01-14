<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
    function submitEditForm() {
        var flag = false;
        $("input#roleInModal").each(function () {
            var c = $(this).prop("checked");
            flag = c;
            if(c){
                $("form#editForm").submit();
                return false;
            }
        });
        if(flag){
            return;
        }
        alert("请选择至少一种角色");
    }
</script>
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" style="height: 400px;width: 400px">
        <div class="modal-content editDivInListAdminUser">
            <div class="modal-header">
                <span>编辑管理员用户</span>
            </div>
            <div class="modal-body">
                <form id="editForm" class="editForm" action="/tmall_ssm/admin_user_update" method="post" >
                    <table class="editTable">
                        <tr>
                            <td style="width: 60px">
                                ID
                            </td>
                            <td>
                                <input id="idInModal" name="adminUser.id" readonly="readonly" type="text">
                            </td>
                        </tr>
                        <tr>
                            <td>账号名</td>
                            <td>
                                <input id="nameInModal" type="text" name="adminUser.name"/>
                            </td>
                        </tr>
                        <tr>
                            <td>注册日期</td>
                            <td>
                                <input id="timeInModal" type="text" name="adminUser.createtime" readonly="readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td>所属角色</td>
                            <td id="roleInTd">
                                <c:forEach items="${roles}" var="role">
                                    <input type="checkbox" id="roleInModal" name="roleIds" rid="${role.id}" value="${role.id}">
                                    <span>${role.name}</span>&nbsp;&nbsp;&nbsp;
                                </c:forEach>
                            </td>
                        </tr>
                        <%--<tr class="submitTR">--%>
                            <%--<td colspan="2" align="center">--%>
                                <%--<input class="btn btn-success" type="submit" value="修  改">--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-info" type="submit" onclick="submitEditForm()">修  改</button>
            </div>
                <%--<div><span id="timeInModal"><fmt:formatDate value="${editAdminUser.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span></div>--%>
        </div>
    </div>
</div>
