<%--
  Created by IntelliJ IDEA.
  User: LouisDio
  Date: 2017/11/14
  Time: 15:42
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
        <li><a href="admin_product_list?cid=${c.id}">${c.name}</a></li>
        <li class="active">产品管理</li>
    </ol>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
                <tr class="success">
                    <td>ID</td>
                    <td>图片</td>
                    <td>产品名称</td>
                    <td>产品小标题</td>
                    <td>原价格</td>
                    <td>优惠价格</td>
                    <td>库存数量</td>
                    <td>图片管理</td>
                    <td>设置属性</td>
                    <td>编辑</td>
                    <td>删除</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pList}" var="p">
                    <tr>
                        <td>${p.id}</td>
                        <td>
                            <c:if test="${!empty p.productImage}">
                                <a title="点击显示原图"  href="img/productSingle_small/${p.productImage.id}.jpg"><img height="50px" width="40px" src="img/productSingle_small/${p.productImage.id}.jpg"></a>
                            </c:if>
                        </td>
                        <td>${p.name}</td>
                        <td>${p.subTitle}</td>
                        <td>${p.originalPrice}</td>
                        <td>${p.promotePrice}</td>
                        <td>${p.stock}</td>
                        <td><a href="admin_productImage_list?pid=${p.id}"><span class="glyphicon glyphicon-picture"></span></a></td>
                        <td><a><span></span></a></td>
                        <td><a href="admin_product_edit?pid=${p.id}"><span  class="glyphicon glyphicon-edit"></span></a></td>
                        <td><a href="admin_product_delete?pid=${p.id}&cid=${p.cid}"><span class="glyphicon glyphicon-trash"></span></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp"%>
    </div>
    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增产品</div>
        <div class="panel-body">
            <form id="addForm" action="admin_product_add" method="post">
                <table class="addTable">
                    <tr>
                        <td>产品名称</td>
                        <td><input type="text" name="name" id="name" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>产品小标题</td>
                        <td><input type="text" name="subTitle" id="subTitle" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>原始价格</td>
                        <td><input type="text" name="originalPrice" id="originalPrice" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td><input type="text" name="promotePrice" id="promotePrice" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>库存</td>
                        <td><input type="text" name="stock" id="stock" class="form-control"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" value="${c.id}" name="cid">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<%@include file="../include/admin/adminFooter.jsp"%>
