<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<table class="normal">
    <colgroup>
        <col class="index"/>
        <col class="name"/>
        <col class="parent"/>
        <col class="commodityCount"/>
    </colgroup>
    <thead>
    <tr>
        <th>序号</th>
        <th>名称</th>
        <th>父分类</th>
        <th>录入商品数</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="commodityCategoryGroup" items="${commodityCategoryGroups}" varStatus="status">
        <c:set var="category" value="${commodityCategoryGroup.category}"/>
        <tr>
            <td class="index">${status.index+1}</td>
            <td class="name"><c:if test="${category.parentId != null && category.parentId > 0}">&nbsp;&nbsp;&nbsp;&nbsp;</c:if>${category.name}</td>
            <td class="parent">${category.parentName}</td>
            <td class="commodityCount">
                <span class="commodityCountValue">${commodityCategoryGroup.commodityCount}</span>
                <span class="showBar">&nbsp;</span>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
