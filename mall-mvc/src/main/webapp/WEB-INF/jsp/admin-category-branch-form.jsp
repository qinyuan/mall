<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="fixedForm" id="branchForm" method="post" action="admin-category-branch-add">
    <input type="hidden" name="categoryId"/>

    <div class="branchGroupLetter">
        <c:forEach var="branchGroup" items="${branchGroups}" varStatus="status">
            <div id="letter_${status.index}" ng-mouseover="showBranchGroup($event)"
                 <c:if test="${status.index==0}">class="selected"</c:if>>${branchGroup.letter}</div>
        </c:forEach>
    </div>
    <c:forEach var="branchGroup" items="${branchGroups}" varStatus="status">
        <div class="branchGroup" <c:if test="${status.index!=0}">style="display:none;"</c:if>>
            <c:forEach var="branch" items="${branchGroup.branches}">
                <div class="branch">
                    <img class="branch-logo link" src="${branch.logo}" ng-click="selectBranch($event)"
                         data-options="id:${branch.id}"/><br/>
                    <span>${branch.name}</span>
                </div>
            </c:forEach>
        </div>
    </c:forEach>
    <%@include file="admin-form-submit.jsp" %>
</form>