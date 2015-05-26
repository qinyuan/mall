<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="links">
    <a href="javascript:void(0)" class="noLineAnchor" ng-click="switchSortLinks($event, 'discoverTime')">
        发现时间 <img src="resources/css/images/arrow_down.gif"/>
    </a>
    <a href="javascript:void(0)" class="noLineAnchor" ng-click="switchSortLinks($event, 'price')">
        价格 <img src="resources/css/images/unSort.gif"/>
    </a>
    <a href="javascript:void(0)" class="noLineAnchor" ng-click="switchSortLinks($event, 'sales')">
        销量 <img src="resources/css/images/unSort.gif"/>
    </a>
</div>