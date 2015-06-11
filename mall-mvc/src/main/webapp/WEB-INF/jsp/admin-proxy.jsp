<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <div class="proxyCount">
        <span>代理总数：${proxyCount}</span>
        <span>有效代理数：${fastProxyCount}</span>
    </div>
    <table class="normal">
        <colgroup>
            <col class="index"/>
            <col class="proxy"/>
            <col class="speed"/>
            <col class="url"/>
            <col class="time"/>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>代理</th>
            <th>速度(ms)</th>
            <th>访问链接</th>
            <th>时间</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="rejection" items="${rejections}" varStatus="status">
            <tr>
                <td class="index">${status.index + rowStartIndex}</td>
                <td class="proxy">${rejection.proxy}</td>
                <td class="speed">${rejection.speed}</td>
                <td class="url">
                    <a href="${rejection.url}" target="_blank">${rejection.url}</a>
                </td>
                <td class="time">${rejection.rejectTime}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%@include file="pagination.jsp" %>
</div>
<%@include file="footer.jsp" %>