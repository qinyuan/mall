<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="branch">
    <div class="poster">
        <a target="_blank" herf="javascript:void(0)">
            <div class="image"></div>
        </a>
    </div>
    <div class="title blueFont"><img src="${appConfig.branchRankImage}"/></div>
    <div class="logos">
        <div class="showBranch" ng-repeat="branch in showBranches" ng-mouseover="showMore()">
            <div class="border">
                <div class="fullBorder"></div>
                <div class="horizontalBorder"></div>
                <div class="verticalBorder"></div>
            </div>
            <div class="branchLogo">
                <a href="shoppe?id={{branch.id}}" target="_blank">
                    <img class="link branch-logo" ng-src="{{branch.logo}}"
                         onmouseover="showBranchBorder(this)"
                         onmouseout="hideBranchBorder(this)"/>
                </a>
            </div>
        </div>
        <div class="moreBranch branchLogo" ng-mouseover="showMore()" ng-show="moreBranch.show">
            <a href="javascript:void(0)" target="_blank">
                <img class="link branch-logo" ng-src="{{moreBranch.logo}}"/>
            </a>
        </div>
        <div class="hideBranch" ng-repeat="branch in hideBranches">
            <div class="border">
                <div class="fullBorder"></div>
                <div class="horizontalBorder"></div>
                <div class="verticalBorder"></div>
            </div>
            <div class="branchLogo">
                <a href="shoppe?id={{branch.id}}" target="_blank">
                    <img class="link branch-logo" ng-src="{{branch.logo}}"
                         onmouseover="showBranchBorder(this)"
                         onmouseout="hideBranchBorder(this)"/>
                </a>
            </div>
        </div>
    </div>
</div>