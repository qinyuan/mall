<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<div class="user">
    <security:authentication property="name"/>
    <a href="/j_spring_security_logout">[退出]</a>
</div>
<div class="split"></div>
<div class="commodity">发布的商品<span id="commodityCount">(${commodityCount})</span></div>
<div class="split">
    <div class="boldSplit"></div>
</div>
<div class="log">
    <div class="user"><a href="admin-log.html" target="_blank">操作日志</a></div>
    <div class="crawl"><a href="admin-crawl-log.html" target="_blank">爬虫日志</a></div>
    <div class="crawl"><a href="admin-proxy.html" target="_blank">代理日志</a></div>
</div>
<div class="split"></div>
<div class="activeGroup">
    <div class="active">有效 <span class="active">&nbsp;&nbsp;</span></div>
    <div class="inactive" title="被手工标记为无效">无效(手工) <span class="inactiveByUser">&nbsp;&nbsp;</span></div>
    <div class="inactive" title="被程序标记为无效">无效(程序) <span class="inactiveByProgram">&nbsp;&nbsp;</span></div>
</div>
<div class="split"></div>
<div class="linkGroup">
    <div class="branch"><a target="_blank" href="admin-add-statistics.html">录入统计&gt;&gt;</a></div>
    <div class="branch"><a target="_blank" href="admin-branch.html">品牌管理&gt;&gt;</a></div>
    <div class="category"><a target="_blank" href="admin-category.html">分类管理&gt;&gt;</a></div>
    <security:authorize ifAnyGranted="ROLE_SUPPER_ADMIN">
        <div class="index-logo"><a target="_blank" href="admin-index-logo.html">主页管理&gt;&gt;</a></div>
        <div class="detail"><a target="_blank" href="admin-detail.html">详情页管理&gt;&gt;</a></div>
        <div class="user"><a target="_blank" href="admin-user.html">用户管理&gt;&gt;</a></div>
        <div class="config"><a target="_blank" href="admin-config.html">系统设置&gt;&gt;</a></div>
        <div class="seo"><a target="_blank" href="admin-seo.html">SEO设置&gt;&gt;</a></div>
        <div class="article"><a target="_blank" href="admin-article-list.html">文章管理&gt;&gt;</a></div>
    </security:authorize>
</div>
<div class="split"></div>
