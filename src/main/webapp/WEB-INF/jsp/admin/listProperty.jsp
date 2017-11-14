<%--
  Created by IntelliJ IDEA.
  User: LouisDio
  Date: 2017/11/13
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="UTF-8" import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_property_list?cid=${c.id}">${c.name}</a></li>
        <li class="active">属性管理</li>
    </ol>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
                <tr class="success">
                    <th>ID</th>
                    <th>属性名称</th>
                    <th>编辑</th>
                    <th>删除</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${pList}">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.name}</td>
                        <td><a href="admin_property_edit?pid=${p.id}"><span  class="glyphicon glyphicon-edit"></span></a></td>
                        <td><a href="admin_property_delete?pid=${p.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </div>
    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp"%>
    </div>
    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增属性</div>
        <div class="panel-body">
            <form id="addForm"  action="admin_property_add" method="post">
                <table class="addTable">
                    <tr>
                        <td>属性名称</td>
                        <td><input id="name" type="text" name="name" class="form-control"/></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${c.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<%@include file="../include/admin/adminFooter.jsp"%>