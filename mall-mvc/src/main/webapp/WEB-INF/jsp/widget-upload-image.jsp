<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="text" id="${param.id}" name="${param.id}"
       <c:if test="${param.value!=null}">value="${param.value}"</c:if>  />
<input type="file" id="${param.id}File" name="${param.id}File"/>
