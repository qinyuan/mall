<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="inner">
    <tbody>
    <c:forEach var="searchWord" items="${richCategory.hotSearchWords}">
        <tr id="hotSearchWord_${searchWord.id}">
            <td class="content">
                <span <c:if test="${searchWord.hot}">class="hot"</c:if>>${searchWord.content}</span>
            </td>
            <td>
                <jsp:include page="widget-edit-delete.jsp">
                    <jsp:param name="editAction" value="editSearchWord($event)"/>
                    <jsp:param name="deleteAction" value="deleteSearchWord($event)"/>
                </jsp:include>
                <jsp:include page="widget-ranking.jsp">
                    <jsp:param name="upAction" value="rankUpSearchWord($event)"/>
                    <jsp:param name="downAction" value="rankDownSearchWord($event)"/>
                </jsp:include>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div><img class="link" ng-click="addSearchWord($event)" src="resources/css/images/add.png"/></div>
