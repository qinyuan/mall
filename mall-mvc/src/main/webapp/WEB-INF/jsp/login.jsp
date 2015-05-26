<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="login boxShadow">
    <div class="title">
        <span>商品管理员登录</span>
        <img class="link" id="closeLink" src="resources/css/images/login/close.png"/>
    </div>
    <div class="loginForm">
        <form action="j_spring_security_check" method="post">
            <p>
                <input type="text" id="username" name="j_username" class="form-control" placeholder="用户名"/>
            </p>

            <p>
                <input type="text" id="password" name="j_password" class="form-control" placeholder="密码"/>
            </p>

            <p>
                <input type="checkbox" id="rememberMe" name="_spring_security_remember_me"/>
                <span id="rememberMeLabel">记住我</span>
                <input type="submit" id="loginSubmit" value="登录"/>
            </p>
        </form>
    </div>
</div>
<%@include file="footer.jsp" %>
