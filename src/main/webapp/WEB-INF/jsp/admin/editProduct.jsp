<%--
  Created by IntelliJ IDEA.
  User: LouisDio
  Date: 2017/11/14
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${p.cid}">${p.category.name}</a></li>
        <li>${p.name}</li>
        <li>编辑产品</li>
    </ol>
    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑产品</div>
        <div class="panel-body">
            <form class="addForm" action="admin_product_update" method="post">
                <table class="editTable">
                    <tr>
                        <td>产品名称</td>
                        <td><input type="text" value="${p.name}" class="form-control" name="name" id="name"></td>
                    </tr>
                    <tr>
                        <td>产品小标题</td>
                        <td><input type="text" value="${p.subTitle}" class="form-control" name="subTitle" id="subTitle"></td>
                    </tr>
                    <tr>
                        <td>原始价格</td>
                        <td><input type="text" value="${p.originalPrice}" class="form-control" name="originalPrice" id="originalPrice"></td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td><input type="text" value="${p.promotePrice}" class="form-control" name="promotePrice" id="promotePrice"></td>
                    </tr>
                    <tr>
                        <td>库存</td>
                        <td><input type="text" value="${p.stock}" class="form-control" name="stock" id="stock"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" id="id" value="${p.id}">
                            <input type="hidden" name="cid" id="cid" value="${p.cid}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>
