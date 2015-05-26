<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<table class="normal">
    <colgroup>
        <col class="index"/>
        <col class="name"/>
        <col class="firstLetter"/>
        <col class="parent"/>
        <col class="logo"/>
        <col class="commodityCount"/>
    </colgroup>
    <thead>
    <tr>
        <th>序号</th>
        <th>名称</th>
        <th>首字母</th>
        <th>父品牌</th>
        <th>logo</th>
        <th>录入商品数</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="commodityBranchGroup" items="${commodityBranchGroups}" varStatus="status">
        <c:set var="branch" value="${commodityBranchGroup.branch}"/>
        <tr>
            <td class="index">${status.index+1}</td>
            <td class="name">${branch.name}</td>
            <td class="firstLetter">${branch.firstLetter}</td>
            <td class="parent">${branch.parentName}</td>
            <td class="logo">
                <a href="${branch.logo}" title="${branch.logo}"
                   target="_blank"><img onload="adjustImageWidth(this, 82);" src="${branch.logo}"/></a>
            </td>
            <td class="commodityCount">
                <span class="commodityCountValue">${commodityBranchGroup.commodityCount}</span>
                <span class="showBar">&nbsp;</span>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
