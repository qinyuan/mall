<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="info boxShadow">
    <div class="image"><img src="resources/css/images/search/black-cat.png"/></div>
    <div class="text">
        <div style="font-weight: bold;margin-bottom: 20px;color:#333333;">
            喵~没找到与“${keyWord}”相关的 商品 哦，要不您找个关键词我帮您再找找看
        </div>
        <div style="font-weight: bold;font-size:10pt;margin-bottom: 10px;color:#595959;">
            建议您：
        </div>
        <div style="font-size: 9pt;color:#595959;line-height:20px;">
            1. 看看输入的文字是否有误
            <br>
            2. 调整关键词，如“全铜花洒套件”改成“花洒”或“花洒 套件”
        </div>
    </div>
    <%@include file="commodity-search-form.jsp"%>
</div>
<%@include file="footer.jsp" %>
