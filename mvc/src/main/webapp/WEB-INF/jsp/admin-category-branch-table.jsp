<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="inner">
    <tbody>
    <c:forEach var="branch" items="${richCategory.branches}">
        <tr id="branch_${branch.id}">
            <td><img class="branch-logo" src="${branch.logo}"/></td>
            <td>
                <jsp:include page="widget-delete.jsp">
                    <jsp:param name="deleteAction" value="deleteBranch($event)"/>
                </jsp:include>
                <jsp:include page="widget-ranking.jsp">
                    <jsp:param name="upAction" value="rankUpBranch($event)"/>
                    <jsp:param name="downAction" value="rankDownBranch($event)"/>
                </jsp:include>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div><img class="link" ng-click="addBranch($event)" src="resources/css/images/add.png"/></div>