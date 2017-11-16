<%--
  Created by IntelliJ IDEA.
  User: LouisDio
  Date: 2017/11/15
  Time: 15:03
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
        <li><a href="admin_product_list?cid=${p.cid}">${p.category.name}</a></li>
        <li>${p.name}</li>
        <li>产品图片管理</li>
    </ol>
    <table class="addPictureTable" align="center">
            <tr>
                <td class="addPictureTableTD">
                    <div>
                        <div class="panel panel-warning addPictureDiv">
                            <div class="panel-heading">新增产品单个图片</div>
                            <div class="panel-body">
                                <form class="addFormSingle" action="admin_productImage_add" enctype="multipart/form-data" method="post">
                                    <table class="addTable">
                                        <tr>
                                            <td>请选择本地图片 尺寸400X400为佳</td>
                                        </tr>
                                        <tr>
                                            <td><input type="file" name="image" id="filepathSingle"></td>
                                        </tr>
                                        <tr class="submitTR">
                                            <td align="center">
                                                <input type="hidden" value="${p.id}" name="pid">
                                                <input type="hidden" value="type_single" name="type">
                                                <button type="submit" class="btn btn-success">提交</button>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                        </div>
                        <div class="listDataTableDiv">
                            <table class="table table-striped table-bordered table-hover  table-condensed">
                                <thead>
                                    <tr class="success">
                                        <td>ID</td>
                                        <td>产品单个图片略缩图</td>
                                        <td>删除</td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${pisSingle}" var="s">
                                        <tr>
                                            <td>${s.id}</td>
                                            <td><a title="点击查看原图" href="img/productSingle/${s.id}.jpg"><img height="50px" src="img/productSingle/${s.id}.jpg"></a></td>
                                            <td><a deleteLink="true" href="admin_productImage_delete?iid=${s.id}&pid=${p.id}&type=${s.type}"><span class="glyphicon glyphicon-trash"></span></a></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </td>
                <td class="addPictureTableTD">
                    <div>
                        <div class="panel panel-warning addPictureDiv">
                            <div class="panel-heading">新增产品详情图片</div>
                            <div class="panel-body">
                                <form class="addFormSingle" action="admin_productImage_add" enctype="multipart/form-data" method="post">
                                    <table class="addTable">
                                        <tr>
                                            <td>请选择本地图片 尺寸400X400为佳</td>
                                        </tr>
                                        <tr>
                                            <td><input type="file" name="image" id="filepathDetail"></td>
                                        </tr>
                                        <tr class="submitTR">
                                            <td align="center">
                                                <input type="hidden" value="${p.id}" name="pid">
                                                <input type="hidden" value="type_detail" name="type">
                                                <button type="submit" class="btn btn-success">提交</button>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                        </div>
                        <div class="listDataTableDiv">
                            <table class="table table-striped table-bordered table-hover  table-condensed">
                                <thead>
                                <tr class="success">
                                    <td>ID</td>
                                    <td>产品详情图片略缩图</td>
                                    <td>删除</td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${pisDetail}" var="d">
                                    <tr>
                                        <td>${d.id}</td>
                                        <td><a title="点击显示原图" href="img/productDetail/${d.id}.jpg"><img height="50px" src="img/productDetail/${d.id}.jpg"></a></td>
                                        <td><a deleteLink="true" href="admin_productImage_delete?iid=${d.id}&pid=${p.id}&type=${d.type}"><span class="glyphicon glyphicon-trash"></span></a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </td>
            </tr>
    </table>
</div>
<%@include file="../include/admin/adminFooter.jsp"%>
