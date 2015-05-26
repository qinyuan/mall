<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <table class="normal">
        <thead>
        <tr>
            <th>序号</th>
            <th>图片链接</th>
            <th>目标链接</th>
            <th>描述</th>
            <th>缩略图</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%@include file="admin-index-logo-table-body.jsp" %>
        </tbody>
    </table>
    <form id="indexLogoForm" method="post" action="admin-index-logo-add-update"
          enctype="multipart/form-data">
        <input type="hidden" name="id"/>

        <div class="logo">
            <label>图片(url或上传图片)</label>
            <span class="required">*</span>
            <br/>
            <jsp:include page="widget-upload-image.jsp">
                <jsp:param name="id" value="logo"/>
            </jsp:include>
        </div>
        <div class="link">
            <label>目标链接</label><br/>
            <input type="text" name="link"/>
        </div>
        <div class="description">
            <label>描述</label><br/>
            <input type="text" name="description"/>
        </div>
        <%@include file="admin-form-submit.jsp" %>
    </form>
</div>
<%@include file="footer.jsp" %>