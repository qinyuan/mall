<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="fixedForm" id="searchWordForm" method="post"
      action="admin-hot-search-word-add-update">
    <input type="hidden" name="id"/>
    <input type="hidden" name="categoryId"/>

    <div>
        <input type="text" name="content"/>
        &nbsp;&nbsp;
        <input type="checkbox" name="hot"/>热点词
    </div>
    <%@include file="admin-form-submit.jsp"%>
</form>