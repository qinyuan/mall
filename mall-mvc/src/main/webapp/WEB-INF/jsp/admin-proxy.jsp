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
            <col class="action"/>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>代理</th>
            <th>速度(ms)</th>
            <th>访问链接</th>
            <th>被拦截时间</th>
            <th>操作</th>
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
                <td class="action">
                    <a href="javascript:void(0)" ng-click="reactivate(${rejection.id})">重新激活</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%@include file="pagination.jsp" %>
</div>
<%@include file="footer.jsp" %>