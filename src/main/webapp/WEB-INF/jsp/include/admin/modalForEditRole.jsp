<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
    //检查至少有一个checkbox被选中
    function submitEditForm(){
        var flag = false;
        $("input#menuInModal").each(function () {
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
        alert("请选择至少一种权限");
//        $("input#menuInModal").each(function () {
//            var c = $(this).prop("checked");
//            flag = c;
//            if (c){
//                $("form#editForm").submit();
//                return false;
//            }
//        });
//        if(flag){
//            return;
//        }
//        alert("请至少选择一种权限");

    }
</script>
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" style="height: 400px;width: 400px">
        <div class="modal-content editDivInListAdminUser">
            <div class="modal-header">
                <span>编辑角色</span>
            </div>
            <div class="modal-body">
                <form id="editForm" class="editForm" action="/tmall_ssm/admin_role_update" method="post" >
                    <table class="editTable">
                        <tr>
                            <td style="width: 60px">
                                ID
                            </td>
                            <td>
                                <input id="idInModal" name="role.id" readonly="readonly" type="text">
                            </td>
                        </tr>
                        <tr>
                            <td>角色名</td>
                            <td>
                                <input id="nameInModal" type="text" name="role.name"/>
                            </td>
                        </tr>
                        <tr>
                            <td>创建日期</td>
                            <td>
                                <input id="timeInModal" type="text" name="role.createtime" readonly="readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td>所属权限</td>
                            <td id="menuInTd">
                                <c:forEach items="${menuList}" var="m">
                                    <input type="checkbox" id="menuInModal" name="menuIds" mid="${m.id}" value="${m.id}">
                                    <span>${m.name}</span>&nbsp;&nbsp;&nbsp;
                                </c:forEach>
                            </td>
                        </tr>
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

