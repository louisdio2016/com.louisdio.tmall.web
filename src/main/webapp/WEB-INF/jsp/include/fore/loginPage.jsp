<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
    $(function () {
        <c:if test="${!empty msg}">
            $("span.errorMessage").html("${msg}");
            $("div.loginErrorMessageDiv").show();
        </c:if>
        $("form.loginForm").submit(function () {
            if(0==$("input#name").val().length){
                $("span.errorMessage").html("用户名不能为空");
                $("div.loginErrorMessageDiv").show();
                return false;
            }
            if(0==$("input#password").val().length){
                $("span.errorMessage").html("密码不能为空");
                $("div.loginErrorMessageDiv").show();
                return false;
            }
            return true;
        });
        $("form.loginForm input").keyup(function () {
            $("div.loginErrorMessageDiv").hide();
        });
        var left = window.innerWidth/2+162;
        $("div.loginSmallDiv").css("left",left);
    });
</script>
<div id="loginDiv" style="position:relative">
    <div class="simpleLogo">
        <a href="${contextPath}"><img src="img/site/simpleLogo.png"></a>
    </div>
    <img id="loginBackgroundImg" src="img/site/loginBackground.png" class="loginBackgroundImg">
    <form action="forelogin" class="loginForm" method="post">
        <div id="loginSmallDiv" class="loginSmallDiv">
            <div class="loginErrorMessageDiv">
                <div class="alert alert-danger">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                    <span class="errorMessage"></span>
                </div>
            </div>

            <div class="login_acount_text">账户登录</div>
            <div class="loginInput">
                <span class="loginInputIcon">
                    <span class="glyphicon glyphicon-user"></span>
                </span>
                <input id="name" name="name" placeholder="手机/会员名/邮箱" type="text">
            </div>

            <div class="loginInput">
                <span class="loginInputIcon">
                    <span class="glyphicon glyphicon-lock"></span>
                </span>
                <input id="password" name="password" placeholder="密码" type="password">
            </div>

            <div>
                <a class="notImplementLink" href="#nowhere">忘记登录密码</a>
                <a href="registerPage" class="pull-right">免费注册</a>
            </div>

            <div style="margin-top: 20px">
                <button type="submit" class="btn btn-block redButton">登  录</button>
            </div>
        </div>
    </form>
</div>

