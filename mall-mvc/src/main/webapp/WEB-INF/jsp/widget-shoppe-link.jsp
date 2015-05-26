<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="shoppe" items="${branch.shoppes}">
    <div class="link">
        <a target="_blank" href="${shoppe.url}">${shoppe.name}</a>
    </div>
</c:forEach>