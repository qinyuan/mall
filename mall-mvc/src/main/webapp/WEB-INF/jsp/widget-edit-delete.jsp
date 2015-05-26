<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="widget-edit.jsp">
    <jsp:param name="editAction" value="${param.editAction}"/>
</jsp:include>
<jsp:include page="widget-delete.jsp">
    <jsp:param name="deleteAction" value="${param.deleteAction}"/>
</jsp:include>
