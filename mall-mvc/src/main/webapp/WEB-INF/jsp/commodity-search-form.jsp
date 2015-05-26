<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="searchForm">
    <div class="input-group">
        <div class="input-group-btn searchType">
            <button type="button" class="btn btn-default dropdown-toggle grayBack"
                    data-toggle="dropdown">
                <span class="text">全部分类</span><span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li data-options="id:0">
                    <a href="javascript:void(0)">全部分类</a>
                </li>
                <c:if test="${categoryId!=null}">
                    <li data-options="id:${categoryId}">
                        <a href="javascript:void(0)">${categoryName}</a>
                    </li>
                </c:if>
                <c:forEach var="subCategory" items="${subCategories}">
                    <li data-options="id:${subCategory.id}">
                        <a href="javascript:void(0)">${subCategory.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <form action="search">
            <input type="hidden" id="searchCategoryId" name="categoryId"/>
            <input type="text" name="keyWord" id="searchInput"
                   class="form-control" placeholder="请输入您想查询的关键字"/>
            <span class="input-group-addon searchCommit">
                <img class="link" id="searchCommit" src="resources/css/images/searchButton.png"/>
            </span>
        </form>
    </div>
    <div class="hotWords">
        <span ng-repeat="hotWord in hotWords">
             <a class="{{hotWord.style}}" href="javascript:void(0)"
                ng-href="search?keyWord={{hotWord.content}}&categoryId={{categoryId}}" target="_blank">
                 {{hotWord.content}}
             </a>
        </span>
    </div>
</div>