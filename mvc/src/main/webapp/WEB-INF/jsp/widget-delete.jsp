<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<span <c:if test="${param.deleteAction!=null}">ng-click="${param.deleteAction}"</c:if>>
    <img class="link" title="删除" src="resources/css/images/table/action_delete.png"/>
</span>