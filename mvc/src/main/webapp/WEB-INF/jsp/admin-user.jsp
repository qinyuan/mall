<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <table class="normal">
        <colgroup>
            <col class="index"/>
            <col class="username"/>
            <col class="password"/>
            <col class="role"/>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>用户名</th>
            <th>密码</th>
            <th>角色</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}" varStatus="status">
            <tr id="user_${user.id}">
                <td class="index">${status.index+1}</td>
                <td class="username">${user.username}</td>
                <td class="password">${user.password}</td>
                <td class="role">${fn:contains(user.role, 'SUPPER')?'超级管理员':'管理员'}</td>
                <td class="edit">
                    <c:choose>
                        <c:when test="${fn:contains(user.role,'SUPPER')}">
                            <jsp:include page="widget-edit.jsp">
                                <jsp:param name="editAction" value="editUser($event)"/>
                            </jsp:include>
                        </c:when>
                        <c:otherwise>
                            <jsp:include page="widget-edit-delete.jsp">
                                <jsp:param name="editAction" value="editUser($event)"/>
                                <jsp:param name="deleteAction" value="deleteUser($event)"/>
                            </jsp:include>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form id="userForm" method="post" action="admin-user-add-update">
        <input type="hidden" name="id"/>

        <div>
            <label>用户名: </label><br/>
            <input type="text" name="username"/>
        </div>
        <div>
            <label>密码：</label><br/>
            <input type="password" name="password"/>
        </div>
        <%@include file="admin-form-submit.jsp" %>
    </form>
</div>
<%@include file="footer.jsp" %>