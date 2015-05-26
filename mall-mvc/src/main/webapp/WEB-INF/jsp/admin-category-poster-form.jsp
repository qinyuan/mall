<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="fixedForm" id="posterForm" method="post" action="admin-category-poster-add-update"
      enctype="multipart/form-data">
    <input type="hidden" name="id"/>
    <input type="hidden" name="categoryId"/>

    <div class="path">
        <label>图片(url或上传图片)</label>
        <span class="required">*</span>
        <br/>
        <jsp:include page="widget-upload-image.jsp">
            <jsp:param name="id" value="poster"/>
        </jsp:include>
    </div>
    <div class="link">
        <label>目标链接</label><br/>
        <input type="text" name="link"/>
    </div>
    <%@include file="admin-form-submit.jsp" %>
</form>