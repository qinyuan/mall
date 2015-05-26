<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<div class="basic">
    <c:if test="${commodity != null}">
        <div class="serial">
            <input type="hidden" name="serialNumber" value="${commodity.serialNumber}"/>

            <span class="title">商品编号</span>
            <span id="serial">${commodity.serialNumber}</span>
        </div>
    </c:if>

    <div class="branch">
        <div class="title">品牌选择</div>
        <%@include file="admin-edit-commodity-branch-select.jsp" %>
        <div class="require-info">(必填)</div>
        <div class="handle-link">
            <a target="_blank" href="admin-branch">品牌管理&gt;&gt;</a>
        </div>
    </div>
    <div class="category">
        <div class="title">商品分类</div>
        <%@include file="admin-edit-commodity-category-select.jsp" %>
        <div class="require-info">(必填)</div>
        <div class="handle-link">
            <a target="_blank" href="admin-category">分类管理&gt;&gt;</a>
        </div>
    </div>
</div>
