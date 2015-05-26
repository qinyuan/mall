<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<input type="hidden" id="categoryId" value="${categoryId}"/>

<div class="search boxShadow" ng-mouseout="hideMore($event)">
    <div class="navigationName boxShadow orangeBack">
        <span class="selectedNavigation">${categoryName}</span>
    </div>
    <div class="subCategory">
        <c:forEach var="subCategory" items="${subCategories}" varStatus="status">
            <div>
                    <%--
                    <a href="list.html?id=${categoryId}&subCategory=${subCategory.id}"
                       class="noLineAnchor lightGrayFont" data-options="id:${subCategory.id}"
                       ng-click="selectSubCategory($event)">${subCategory.name}</a>
                       --%>
                <a href="list.html?id=${categoryId}&subCategory=${subCategory.id}"
                   class="noLineAnchor lightGrayFont" data-options="id:${subCategory.id}">${subCategory.name}</a>
            </div>
        </c:forEach>
    </div>
    <div class="split"></div>
    <div class="right">
        <div class="searchFormBack"></div>
        <%@include file="list-branch.jsp" %>
    </div>
</div>
<%@include file="commodity-search-form.jsp" %>
<div class="goods">
    <div class="sort">
        <div class="title blueFont">
            今日<span class="selectedNavigation">${categoryName}</span>最低价
        </div>
        <%@include file="list-sort-links.jsp" %>
    </div>
    <%@include file="list-snapshots.jsp" %>
</div>
<%@include file="list-right-panel.jsp" %>
<%@include file="footer.jsp" %>