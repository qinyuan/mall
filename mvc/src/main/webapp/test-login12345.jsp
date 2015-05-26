<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%if (request.getRemoteHost().equals("127.0.0.1")) {%>
<form action="j_spring_security_check" method="post">
    <p>
        <input type="hidden" name="j_username" value="admin"/>
    </p>

    <p>
        <input type="hidden" name="j_password" value="admin"/>
    </p>

    <!--<p>
        <input type="checkbox" id="rememberMe" name="_spring_security_remember_me"/>
        <span id="rememberMeLabel">记住我</span>
        <input type="submit" id="loginSubmit" value="登录"/>
    </p>-->
</form>
<script src="resources/js/lib/jquery-1.11.1.min.js"></script>
<script>
    $('form').submit();
</script>
<%}%>
</body>
</html>
