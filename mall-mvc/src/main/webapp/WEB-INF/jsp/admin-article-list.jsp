<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <table class="normal">
        <colgroup>
            <col class="index"/>
            <col class="title"/>
            <col class="action"/>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>标题</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="article" items="${articles}" varStatus="status">
            <tr id="article_${article.id}">
                <td>${status.index+1}</td>
                <td><a target="_blank" href="article.html?id=${article.id}">${article.title}</a></td>
                <td>
                    <jsp:include page="widget-edit-delete.jsp">
                        <jsp:param name="editAction" value="editArticle($event)"/>
                        <jsp:param name="deleteAction" value="deleteArticle($event)"/>
                    </jsp:include>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="button">
        <button type="button" class="btn btn-success" ng-click="addArticle()">
            添加
        </button>
    </div>
</div>
<%@include file="footer.jsp" %>