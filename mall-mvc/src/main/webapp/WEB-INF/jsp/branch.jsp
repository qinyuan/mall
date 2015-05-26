<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div id="branchGroups">
    <div class="branchGroup" ng-repeat="branchGroup in branchGroups">
        <div class="title"><span>{{branchGroup.letter}}</span></div>
        <div class="branch" ng-repeat="branch in branchGroup.branches">
            <a class="noLineAnchor" ng-href="shoppe?id={{branch.id}}" target="_blank">
                <img class="branch-logo" ng-src="{{branch.logo}}"/><br>{{branch.name}}
            </a>
        </div>
    </div>
</div>
<!--
<div class="loading">
<span>加载中，请稍候...</span>
</div>
-->
<div class="rightFloat">
    <div ng-repeat="branchGroup in branchGroups">
        <a class="darkFont sameWidthFont noLineAnchor" href="javascript:void(0)"
           ng-click="letterClick(branchGroup.letter)">{{branchGroup.letter}}</a>
    </div>
</div>
<%@include file="footer.jsp" %>
