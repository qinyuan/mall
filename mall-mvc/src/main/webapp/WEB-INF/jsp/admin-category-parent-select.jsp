<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<select id="${param.elementId}" name="${param.elementId}">
    <option value="0"></option>
    <c:forEach var="category" items="${rootCategories}">
        <option value="${category.id}">${category.name}</option>
    </c:forEach>
</select>
