<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="title">
    <div class="lowest"><a class="noLineAnchor" href="javascript:void(0)">历史最低</a></div>
    <security:authorize ifAnyGranted="ROLE_SUPPER_ADMIN,ROLE_ADMIN">
        <div class="all"><a class="noLineAnchor" href="javascript:void(0)">所有宝贝</a></div>
    </security:authorize>
</div>