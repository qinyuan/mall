<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <div class="order">
        统计类型：
        <select id="statisticType">
            <option value="0">按品牌</option>
            <option value="1">按分类</option>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        排序规则：
        <select id="orderType">
            <option value="0">商品数量</option>
            <option value="1">${commodityBranchGroups == null ? '分类顺序' : '字母顺序' }</option>
        </select>
    </div>
    <c:if test="${commodityBranchGroups != null}">
        <%@include file="admin-add-statistics-by-branch.jsp" %>
    </c:if>
    <c:if test="${commodityCategoryGroups != null}">
        <%@include file="admin-add-statistics-by-category.jsp" %>
    </c:if>
</div>
<%@include file="footer.jsp" %>