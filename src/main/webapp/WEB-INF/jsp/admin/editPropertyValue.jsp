<%--
  Created by IntelliJ IDEA.
  User: LouisDio
  Date: 2017/11/19
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<script>
$(function (){
    $("input.pvValue").keyup(function(){
        var value = $(this).val();
        var page = "admin_propertyValue_update";
        var pvid = $(this).attr("pvid");
        var parentSpan = $(this).parent("span");
        parentSpan.css("border","1px solid yellow")
        $.post(
            page,
            {"value":value,"id":pvid},
            function(result){
                if("success" == result)
                    parentSpan.css("border","1px solid green");
                else
                    parentSpan.css("border","1px solid red");
            }
        );
    });
});
</script>
<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${p.cid}">${c.name}</a></li>
        <li>${p.name}</li>
        <li class="active">编辑产品属性</li>
    </ol>
    <div class="editPVDiv">
        <c:forEach var="pv" items="${pvList}">
            <div class="eachPV">
                <span class="pvName">${pv.property.name}</span>
                <span class="pvValue"><input class="pvValue" pvid="${pv.id}" type="text" value="${pv.value}"></span>
            </div>
        </c:forEach>
    </div>
</div>
<%@include file="../include/admin/adminFooter.jsp"%>



