<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<script>
//	$(function () {
//		$("nav a#category_menu").hide();
//        $("nav a#user_menu").hide();
//        $("nav a#order_menu").hide();
//
//    });
</script>
<div class="navitagorDiv">
	<nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
		<img style="margin-left:10px;margin-right:0px" class="pull-left" src="img/site/tmallbuy.png" height="45px">
		<a class="navbar-brand" href="#nowhere">天猫后台</a>
		<!--临时变量，进行menu的拼接-->
        <c:set var="menus" scope="page" ></c:set>
		<!--遍历adminUser的role-->
        <%--<c:forEach items="${adminUser.roles}" var="role">--%>
			<%--<!--遍历每个role的menu-->--%>
			<%--<c:forEach items="${role.menus}" var="menu">--%>
                <%--&lt;%&ndash;<c:set var="menuName" scope="page" value="${menu.name}"></c:set>&ndash;%&gt;--%>
				<%--<!--判断如果不包含当前menu就在导航栏显示-->--%>
				<%--<c:if test="${!fn:contains(menus,menu.name)}">--%>
						<%--<a class="navbar-brand" id="${menu.sign}" href="${menu.sign}_list">${menu.name}</a>--%>
                <%--</c:if>--%>
				<%--<!--拼接当前的menu到menus-->--%>
                <%--<c:set var="menus" scope="page" value="${menus}${menu.name}"></c:set>--%>
			<%--</c:forEach>--%>
		<%--</c:forEach>--%>
		<!--shiro标签进行权限验证-->
		<shiro:hasPermission name="分类管理">
			<a class="navbar-brand" id="category_menu" href="admin_category_list">分类管理</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="用户管理">
			<a class="navbar-brand" id="user_menu" href="admin_user_list">用户管理</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="订单管理">
			<a class="navbar-brand" id="order_menu" href="admin_order_list">订单管理</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="角色管理">
			<a class="navbar-brand" id="order_menu" href="admin_role_list">角色管理</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="权限管理">
			<a class="navbar-brand" id="order_menu" href="admin_menu_list">权限管理</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="管理员账号管理">
			<a class="navbar-brand" id="order_menu" href="admin_user_list">管理员账号管理</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="测试">
			<a class="navbar-brand" id="order_menu" href="admin_test_list">测试</a>
		</shiro:hasPermission>
		<!--
		<a class="navbar-brand" id="category_menu" href="admin_category_list">分类管理</a>
		<a class="navbar-brand" id="user_menu" href="admin_user_list">用户管理</a>
		<a class="navbar-brand" id="order_menu" href="admin_order_list">订单管理</a>
		-->
		<a class="navbar-brand pull-right" logout="true" id="adminuserlogout" href="logout">注 销</a>
	</nav>
</div>