<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="logo boxShadow" id="logoDiv">
    <div class="logoImage"><img onload="adjustImageWidth(this, 70);" src="${branch.squareLogo}"/></div>
    <div class="slogan">${branch.slogan}</div>
    <div class="links">
        <%@include file="widget-shoppe-link.jsp" %>
        <div class="left back"></div>
        <div class="right back"></div>
        <div class="top back"></div>
        <div class="bottom back"></div>
    </div>
</div>
<div class="goods">
    <div class="sort">
        <%@include file="list-sort-links-title.jsp"%>
        <%@include file="list-sort-links.jsp" %>
    </div>
    <%@include file="list-snapshots.jsp" %>
</div>
<%@include file="list-right-panel.jsp" %>
<%@include file="footer.jsp" %>
