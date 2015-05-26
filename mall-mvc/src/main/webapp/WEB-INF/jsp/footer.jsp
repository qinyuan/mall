<%@ page import="com.qinyuan15.utils.mvc.RequestUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="footer">
    <div class="darkFont">
        @ 2005-2014 douban.com, all rights reserved<br>
        京ICP证090015号 京ICP备11027288号<br>
        <img src="resources/css/images/filing.gif"/>京公安备11010500728<br>
        违法和不良信息举报电话：4006910007
    </div>
    <div class="links">
        <jsp:useBean id="footLinkDao" class="com.qinyuan15.mall.core.dao.AppConfigFootLinkDao"/>
        <c:forEach var="footLink" items="${footLinkDao.instances}">
            <a href="${footLink.link}" target="_blank" class="blueFont">${footLink.text}</a>
        </c:forEach>
    </div>
</div>
</div>
</body>
<script src="resources/js/lib/jquery-1.11.1.min.js"></script>
<script src="resources/js/lib/jquery.url.js"></script>
<script src="resources/js/lib/jquery.cookie.js"></script>
<script src="resources/js/lib/jquery-form-3.51.0.js"></script>
<script src="resources/js/lib/bootstrap/js/bootstrap.min.js"></script>
<!--[if IE]>
<script src="resources/js/lib/angular/html5shiv.js"></script>
<script src="resources/js/lib/angular/json2.js"></script>
<script src="resources/js/lib/ie-patch.js"></script>
<![endif]-->
<script src="resources/js/lib/angular/angular.min.js"></script>
<script src="resources/js/lib/underscore-min.js"></script>
<script src="<%=RequestUtils.getJs("lib/jsutils")%>"></script>
<script src="<%=RequestUtils.getJs("common")%>"></script>
<c:forEach var="js" items="${moreJs}">
    <script src="resources/js/${js}.js?t=<%=RequestUtils.VERSION%>"></script>
</c:forEach>
<script src="<%=RequestUtils.getRelativeJs(request)%>"></script>
<!--[if IE]>
<c:forEach var="js" items="${ieJs}">
    <script src="resources/js/${js}.js?t=<%=RequestUtils.VERSION%>"></script>
</c:forEach>
<![endif]-->
</html>