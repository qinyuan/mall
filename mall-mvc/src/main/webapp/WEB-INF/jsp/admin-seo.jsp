<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <table class="normal">
        <colgroup>
            <col class="url">
            <col class="word">
            <col class="description">
            <col class="action">
        </colgroup>
        <thead>
        <tr>
            <th>页面链接</th>
            <th>关键词</th>
            <th>描述</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="seoKeyword" items="${seoKeywords}">
            <tr id="seoKeyword_${seoKeyword.id}">
                <td class="url">
                    <a href="${seoKeyword.url}" class="limit-size" data-options="limit:15"
                       target="_blank" title="${seoKeyword.url}">${seoKeyword.url}</a>
                </td>
                <td class="word">${seoKeyword.word}</td>
                <td class="description">${seoKeyword.description}</td>
                <td>
                    <jsp:include page="widget-edit-delete.jsp">
                        <jsp:param name="editAction" value="editKeyword($event)"/>
                        <jsp:param name="deleteAction" value="deleteKeyword($event)"/>
                    </jsp:include>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form id="keywordForm" method="post" action="admin-seo-keyword-add-update">
        <input type="hidden" name="id"/>

        <div class="url">
            <label>页面链接</label>
            <span class="required">*</span>
            <br/>
            <input type="text" name="url"/>
        </div>
        <div class="word">
            <label>关键词</label>
            <span class="required">*</span>
            <br/>
            <textarea name="word" cols="25" rows="3"></textarea>
        </div>
        <div class="description">
            <label>描述</label>
            <span class="required">*</span>
            <br/>
            <textarea name="description" cols="25" rows="3"></textarea>
        </div>
        <%@include file="admin-form-submit.jsp" %>
    </form>
</div>
<%@include file="footer.jsp" %>