<%--
  Created by IntelliJ IDEA.
  User: LouisDio
  Date: 2017/11/27
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
    $(function () {
        <c:if test="${!empty msg}">
            $("span.errorMessage").html("${msg}");
            $("div.registerErrorMessageDiv").css("visibility","visible");
        </c:if>
        $(".registerForm").submit(function () {
           if(0==$("#name").val().length){
               $("span.errorMessage").html("请输入用户名");
               $("div.registerErrorMessageDiv").css("visibility","visible");
               return false;
           }
            if(0==$("#password").val().length){
                $("span.errorMessage").html("请输入密码");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }
            if(0==$("#repeatpassword").val().length){
                $("span.errorMessage").html("请输入确认密码");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }
            if($("#password").val() != $("#repeatpassword").val()){
                $("span.errorMessage").html("确认密码不一致");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }
        });
    })
</script>
<form method="post" action="foreregister" class="registerForm">
    <div class="registerDiv">
        <div class="registerErrorMessageDiv">
            <div class="alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"/>
                <span class="errorMessage"></span>
            </div>
        </div>

        <table class="registerTable" align="center">
            <tbody>
                <tr>
                    <td class="registerTip registerTableLeftTD">设置会员名</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="registerTableLeftTD">登录名</td>
                    <td class="registerTableRightTD"><input id="name" name="name" placeholder="会员名一旦设置后，不能修改"></td>
                </tr>
                <tr>
                    <td class="registerTip registerTableLeftTD">设置登录密码</td>
                    <td class="registerTableRightTD">登录时验证，保护账号信息</td>
                </tr>
                <tr>
                    <td class="registerTableLeftTD">登录密码</td>
                    <td class="registerTableRightTD"><input id="password" name="password" type="password" placeholder="设置你的登录密码"></td>
                </tr>
                <tr>
                    <td class="registerTableLeftTD">确认密码</td>
                    <td class="registerTableRightTD"><input id="repeatpassword" type="password" placeholder="请再次输入你的密码"></td>
                </tr>
                <tr>
                    <td colspan="2" class="registerButtonTD">
                        <a href="registerSuccess.jsp"><button>提  交</button></a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</form>

