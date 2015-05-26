<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="title">
    <img src="${appConfig.indexHeadPoster}"/>
</div>
<div class="search input-group">
    <form action="search">
        <input type="text" name="keyWord" id="searchInput" class="form-control"
               placeholder="搜下有什么好东西"/>
        <span class="input-group-addon searchCommit">
            <img class="link" id="searchCommit" src="resources/css/images/index/search.png"/>
        </span>
    </form>
</div>
<div class="links">
    <c:forEach var="indexLogo" items="${indexLogos}">
        <a href="${fn:length(indexLogo.link)>0?indexLogo.link:'javascript:void(0)'}">
            <div class="logoGroup">
                <img src="${indexLogo.path}">

                <div class="cover"></div>
                <div class="text">
                    <div class="back"></div>
                    <c:if test="${fn:length(indexLogo.description)>0}">
                        <div class="logoDesc">${indexLogo.description}</div>
                    </c:if>
                </div>
            </div>
        </a>
    </c:forEach>
</div>
<script id="footPoster" type="text/x-data">${appConfig.indexFootPoster}</script>
<script id="footPosterLink" type="text/x-data">${appConfig.indexFootPosterLink}</script>
<%@include file="footer.jsp" %>

