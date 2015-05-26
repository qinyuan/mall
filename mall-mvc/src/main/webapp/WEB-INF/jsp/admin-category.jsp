<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <table class="normal">
        <colgroup>
            <col class="index">
            <col class="name">
            <col class="parent">
            <col class="action">
            <col class="hotSearchWord">
            <col class="branch">
            <col class="poster">
        </colgroup>
        <thead>
        <tr>
            <th class="index">序号</th>
            <th class="name">名称</th>
            <th class="parent">父分类</th>
            <th class="action"></th>
            <th class="hotSearchWord">搜索关键词</th>
            <th class="branch">关联品牌</th>
            <th class="poster">海报</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="richCategory" items="${richCategories}" varStatus="status">
            <c:set var="category" value="${richCategory.category}"/>
            <tr id="category_${category.id}">
                <td class="index">${status.index+1}</td>
                <td class="name"><c:if test="${richCategory.categoryLevel>0}">&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
                        ${category.name}</td>
                <td class="parent" data-options="parentId: ${category.parentId}">${category.parentName}</td>
                <td>
                    <jsp:include page="widget-edit-delete.jsp">
                        <jsp:param name="editAction" value="editCategory($event)"/>
                        <jsp:param name="deleteAction" value="deleteCategory($event)"/>
                    </jsp:include>
                    <jsp:include page="widget-ranking.jsp">
                        <jsp:param name="upAction" value="rankUpCategory($event)"/>
                        <jsp:param name="downAction" value="rankDownCategory($event)"/>
                    </jsp:include>
                </td>
                <td class="hotSearchWord">
                    <%@include file="admin-category-hot-search-word-table.jsp" %>
                </td>
                <td class="branch">
                    <%@include file="admin-category-branch-table.jsp" %>
                </td>
                <td class="poster">
                    <%@include file="admin-category-poster-table.jsp" %>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%@include file="admin-category-form.jsp" %>
    <%@include file="admin-category-hot-search-word-form.jsp" %>
    <%@include file="admin-category-branch-form.jsp" %>
    <%@include file="admin-category-poster-form.jsp" %>
</div>
<%@include file="footer.jsp" %>