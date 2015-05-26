<%@ page import="com.qinyuan15.mall.core.dao.AppConfig" %>
<%@ page import="com.qinyuan15.mall.core.dao.SeoKeyword" %>
<%@ page import="com.qinyuan15.mall.ui.SeoKeywordUtils" %>
<%@ page import="com.qinyuan15.utils.mvc.RequestUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<%SeoKeyword seoKeyword = SeoKeywordUtils.getMatchSeoKeyword(request);%>
<!DOCTYPE html>
<html>
<head lang="zh-ch">
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>${title}</title>
    <meta name="keywords" content="<%=seoKeyword==null?"":seoKeyword.getWord()%>">
    <meta name="description" content="<%=seoKeyword==null?"":seoKeyword.getDescription()%>">
    <link rel="icon" href="${appConfig.favicon}" type="image/x-icon" />
    <link rel="shortcut icon" href="${appConfig.favicon}" type="image/x-icon" />
    <link href="resources/js/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=RequestUtils.getCss("common")%>">
    <c:forEach var="css" items="${moreCss}">
        <link rel="stylesheet" href="resources/css/${css}.css?t=<%=RequestUtils.VERSION%>">
    </c:forEach>
    <link rel="stylesheet" href="<%=RequestUtils.getRelativeCss(request)%>">
    <c:forEach var="js" items="${headJs}">
        <script src="resources/js/${js}.js?t=<%=RequestUtils.VERSION%>"></script>
    </c:forEach>
</head>
<body class="ng-app:main" ng-app="main" id="ng-app">
<div class="header">
    <div><img src="${appConfig.globalBanner}"/></div>
</div>
<div class="navigationBack" ng-controller="NavigationController">
    <div class="navigation">
        <div class="logo">
            <a href="index"><img src="${appConfig.globalLogo}"/></a>
        </div>
        <div class="navigationLinks">
            <a style="display:none;" ng-show="true" ng-repeat="category in categories" class="{{category.class}} noLineAnchor"
               href="javascript:void(0)" ng-href="list.html?id={{category.id}}">{{category.text}}</a>
        </div>
    </div>
</div>
${headerAdditions}
<div class="content" ng-controller="ContentController">