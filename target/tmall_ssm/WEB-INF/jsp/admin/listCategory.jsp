<%--
  Created by IntelliJ IDEA.
  User: LouisDio
  Date: 2017/11/6
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java"
    pageEncoding="UTF-8" import="java.uti.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<script>

</script>
<title>分类管理</title>
<div class="workingArea">
    <h1 class="">分类管理</h1>
    <br>
    <br>
    <!--嵌套三个div：表格，page，新增分类-->
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
                <tr class="success">
                    <th>ID</th>
                    <th>图片</th>
                    <th>分类名称</th>
                    <th>属性管理</th>
                    <th>产品管理</th>
                    <th>编辑</th>
                    <th>删除</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${cs}">
                        <tr>
                            <td>${c.id}</td>
                            <td><img height="40px" src="img/category/${c.id}.jpg"></td>
                            <td>${c.name}</td>
                            <td><a href="admin_property_list?cid=${c.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>
                            <td><a href="admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-th-shopping-cart"></span></a></td>
                            <td><a href="admin_category_edit?cid=${c.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                            <td><a deleteLink="true" href="admin_category_delete?cid=${c.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                        </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%//@include file="../include/admin/adminPage.jsp" %>
    </div>
    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增分类</div>
        <div class="panel-body">
            <form id="addForm" method="post" enctype="multipart/form-data" action="/admin_category_add">
                <table class="addTable">
                    <tr>
                        <td>分类名称</td>
                        <td><input type="text" id="name" name="name" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>分类图片</td>
                        <td><input name="image" type="file" id="categoryPic" accept="image/*"/></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<%@include file="../include/admin/adminFooter.jsp"%>
