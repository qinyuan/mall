<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="left">
    <%@include file="admin-navi.jsp" %>
</div>
<div class="right">
    <div class="filter">
        <div class="input-group-btn">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                ${selectedCategoryName == null ? '(分类过滤)' : selectedCategoryName}
            </button>
            <ul class="dropdown-menu" role="menu">
                <c:forEach var="category" items="${categories}">
                    <li><a href="${category.href}">${category.name}</a></li>
                </c:forEach>
            </ul>
        </div>
        <div id="branchSelect" class="input-group-btn">
            <button type="button" class="btn btn-default" ng-click="showBranchesToSelect($event)">
                ${selectedBranchName == null ? '(品牌过滤)' : selectedBranchName}
            </button>
            <div class="branchDropdown" style="left: 0;">
                <div class="branchGroupLetter">
                    <c:forEach var="branchGroup" items="${branchGroups}" varStatus="status">
                        <div ng-mouseover="showBranchGroup($event, ${status.index})"
                             <c:if test="${status.index==0}">class="selected"</c:if>>${branchGroup.letter}</div>
                    </c:forEach>
                </div>
                <c:forEach var="branchGroup" items="${branchGroups}" varStatus="status">
                    <div class="branchGroup" <c:if test="${status.index!=0}">style="display:none;"</c:if>>
                        <c:forEach var="branch" items="${branchGroup.branches}">
                            <div class="branch" ng-click="selectBranch(${branch.id})">
                                <span>${branch.name}</span>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
                <div style="font-size: 12pt; font-style: italic; margin:5px 10px;">
                    <a style="color: #333333;" href="javascript:void(0)" ng-click="selectAllBranch()">所有品牌</a>
                </div>
            </div>
        </div>
    </div>
    <div class="search input-group">
        <form action="admin" method="get">
            <input type="hidden" name="categoryId" value="${selectedCategoryId}"/>
            <input type="hidden" name="branchId" value="${selectedBranchId}"/>
            <input type="text" name="keyWord" id="searchInput" class="form-control"
                   value="${keyWord}" placeholder="商品搜索"/>
            <span class="input-group-addon searchCommit">
            <img class="link" id="searchCommit" src="resources/css/images/index/search.png"/>
        </span>
        </form>
    </div>
    <%@include file="admin-commodities.jsp" %>
</div>
<%@include file="footer.jsp" %>
