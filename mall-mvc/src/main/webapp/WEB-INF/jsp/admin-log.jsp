<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <table class="normal">
        <colgroup>
            <col class="index"/>
            <col class="user"/>
            <col class="time"/>
            <col class="action"/>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>用户</th>
            <th>时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="userLog" items="${userLogs}" varStatus="status">
            <tr>
                <td class="index">${status.index + rowStartIndex}</td>
                <td class="user">${userLog.username}</td>
                <td class="time">${userLog.logTime}</td>
                <td class="action">${userLog.action}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%@include file="pagination.jsp" %>
</div>
<%@include file="footer.jsp" %>