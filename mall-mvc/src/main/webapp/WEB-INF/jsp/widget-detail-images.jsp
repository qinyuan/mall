<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="detailImage" items="${appConfig.detailImages}">
    <div style="margin: 5px 5px 5px -5px;">
        <c:choose>
            <c:when test="${fn:length(detailImage.link)>0}">
                <a target="_blank" href="${detailImage.link}">
                    <img src="${detailImage.path}"/>
                </a>
            </c:when>
            <c:otherwise>
                <img src="${detailImage.path}"/>
            </c:otherwise>
        </c:choose>
    </div>
</c:forEach>
