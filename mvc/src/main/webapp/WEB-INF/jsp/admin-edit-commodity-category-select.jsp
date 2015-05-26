<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<input type="hidden" id="initCategoryId" value="${commodity.categoryId}"/>

<div id="categorySelect" class="input-group-btn">
    <input type="hidden" name="categoryId" value="{{category.selected.id}}">
    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
        {{category.selected.name}}
    </button>
    <ul class="dropdown-menu" role="menu">
        <li ng-repeat="item in category.items">
            <a href="javascript:void(0)" ng-click="selectCategory(item.id)">{{item.name}}</a>
        </li>
    </ul>
</div>
<div id="subCategorySelect" class="input-group-btn">
    <input type="hidden" name="subCategoryId" value="{{subCategory.selected.id}}">
    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
            ng-disabled="subCategory.disabled">
        {{subCategory.selected.name}}
    </button>
    <ul class="dropdown-menu" role="menu">
        <li ng-repeat="item in subCategory.items">
            <a href="javascript:void(0)" ng-click="selectSubCategory(item.id)">{{item.name}}</a>
        </li>
    </ul>
</div>
