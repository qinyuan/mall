<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<span <c:if test="${param.upAction!=null}">ng-click="${param.upAction}"</c:if>>
    <img class="link" title="上移" src="resources/css/images/table/arrow_up.png"/>
</span>
<span <c:if test="${param.downAction!=null}">ng-click="${param.downAction}"</c:if>>
    <img class="link" title="下移" src="resources/css/images/table/arrow_down.png"/>
</span>
