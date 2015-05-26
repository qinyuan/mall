<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="images">
    <div ng-repeat="snapshot in snapshots" class="boxShadow imageGroup"
         style="display:none;" ng-show="true">
        <div class="image">
            <a ng-href="detail.html?serial={{snapshot.serialNumber}}" target="_blank">
                <div ng-style="background-image:url({{snapshot.picture}})"
                     style="background-image:url({{snapshot.picture}})"></div>
            </a>
        </div>
        <div class="description">
            <a ng-href="detail.html?serial={{snapshot.serialNumber}}"
               class="noLineAnchor" target="_blank">
                {{snapshot.name}}
            </a>
        </div>
        <div class="price">
            ￥{{snapshot.price}}
        </div>
        <div class="branch">
            <a ng-href="shoppe?id={{snapshot.branch.id}}" target="_blank" ng-show="snapshot.branch!=null">
                <img class="link branch-logo" ng-src="{{snapshot.branch.logo}}"/>
            </a>
        </div>
    </div>
    <div ng-show="(snapshots.length==0)" style="display: none;" class="no-found">
        <img src="${appConfig.noFoundImage}"/>
        <c:forEach var="noFoundText" items="${appConfig.noFoundTexts}">
            <p>${noFoundText}</p>
        </c:forEach>
    </div>
</div>
<div class="loading">
    <span>加载中，请稍候...</span>
</div>
<div class="loadMore">
    <a href="javascript:void(0)" class="noLineAnchor" ng-click="loadMore()">加载更多</a>
</div>