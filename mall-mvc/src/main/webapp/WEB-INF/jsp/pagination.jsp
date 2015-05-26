<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="taglib.jsp" %>
<ul class="pagination">
    <c:forEach var="anchor" items="${paginationAnchors}">
        <li>
            <c:choose>
                <c:when test="${anchor.href==null}">
                    <span style="color:#555555;">${anchor.text}</span>
                </c:when>
                <c:otherwise>
                    <a href="${anchor.href}" title="${anchor.title}">${anchor.text}</a>
                </c:otherwise>
            </c:choose>
        </li>
    </c:forEach>
</ul>
