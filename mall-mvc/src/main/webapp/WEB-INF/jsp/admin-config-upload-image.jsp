<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="widget-upload-image.jsp">
    <jsp:param name="id" value="${param.id}"/>
    <jsp:param name="value" value="${param.value}"/>
</jsp:include>
<jsp:include page="admin-config-image-snapshot.jsp">
    <jsp:param name="url" value="${param.value}"/>
</jsp:include>