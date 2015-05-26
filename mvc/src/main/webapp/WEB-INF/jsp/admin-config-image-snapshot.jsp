<%@ page import="com.qinyuan15.utils.mvc.RequestUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:choose>
    <c:when test="${param.url!=null && fn:length(param.url)>0}">
        <a target="_blank" href="${param.url}?t=<%=RequestUtils.VERSION%>" title="单击打开">
            <img src="${param.url}" onload="adjustImageWidth(this, 250);"/>
        </a>
    </c:when>
    <c:otherwise>
        <img/>
    </c:otherwise>
</c:choose>
