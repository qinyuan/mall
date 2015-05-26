<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="searchInfo boxShadow">
    <div class="keyWord">${keyWord} 相关搜索商品</div>
    <div class="search">
        <%@include file="commodity-search-form.jsp" %>
    </div>
</div>
<div class="goods">
    <div class="sort">
        <%@include file="list-sort-links-title.jsp" %>
        <%@include file="list-sort-links.jsp" %>
    </div>
    <%@include file="list-snapshots.jsp" %>
</div>
<%@include file="list-right-panel.jsp" %>
<%@include file="footer.jsp" %>
