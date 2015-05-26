<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <form action="admin-commodity-add-update" method="post" id="mainForm">
        <input type="hidden" name="id" value="${commodity.id}">
        <%@include file="admin-add-commodity-basic.jsp"%>
        <div class="link">
            <table>
                <tr>
                    <td>商品ID号</td>
                    <td>
                        <div class="input-group" role="group">
                            <input type="text" id="showId" name="showId" class="form-control"
                                   value="${commodity.showId}" placeholder="在此输入商品ID号"/>
                            <%@include file="admin-commodity-crawler-button.jsp"%>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>爬虫链接</td>
                    <td>
                        <input type="hidden" name="url" value="${commodity.url}"/>
                        <a id="crawlerLink" class="crawlerLink" href="${commodity.url}" target="_blank">${commodity.url}</a>
                    </td>
                </tr>
                <tr>
                    <td>购买链接</td>
                    <td>
                        <input type="hidden" name="buyUrl" value="${commodity.buyUrl}"/>
                        <a id="buyUrl" class="buyLink" href="${commodity.buyUrl}" target="_blank">${commodity.buyUrl}</a>
                    </td>
                </tr>
                <tr>
                    <td>商品名称</td>
                    <td>
                        <input type="hidden" name="commodityName" value="${commodity.name}"/>
                        <a id="commodityName" class="noLineAnchor keyWordLink" target="_blank"
                           href="${commodity.url}">${commodity.name}</a>
                    </td>
                </tr>
            </table>
            <div class="images">
                <div class="commodityImage">
                    <div class="title">商品图片</div>
                    <div class="imageGroup">
                        <div class="image" ng-repeat="imageUrl in imageUrls">
                            <img class="link" ng-src="{{imageUrl}}" title="单击放大"
                                 ng-click="enlargeImage($index, 'normal')"/><br/>
                            <a class="noLineAnchor" href="javascript:void(0)"
                               ng-click="deleteImage($index)">删除</a>
                        </div>
                    </div>
                </div>
                <div class="commodityDescImage">
                    <div class="title">商品描述图片</div>
                    <div class="imageGroup">
                        <div class="image" ng-repeat="detailImageUrl in detailImageUrls">
                            <img class="link" ng-src="{{detailImageUrl}}" title="单击放大"
                                 ng-click="enlargeImage($index, 'detail')"/><br/>
                            <a class="noLineAnchor" href="javascript:void(0)"
                               ng-click="deleteDetailImage($index)">删除</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="detail">
            <table>
                <colgroup>
                    <col class="title">
                    <col class="content">
                </colgroup>
                <tbody>
                <tr>
                    <td>商品参数</td>
                    <td>
                        <textarea id="commodityDescription" name="parameters" cols="45"
                                  rows="12">${commodity.parameters}</textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="parametersEffect" id="commodityDescriptionEffect"></div>
                    </td>
                </tr>
                <tr>
                    <td>相关好评</td>
                    <td>
                        <c:forEach var="appraiseGroup" items="${positiveAppraiseGroups}">
                            <input type="text" class="form-control" name="positiveAppraiseGroups"
                                   value="${appraiseGroup.content}"/>
                        </c:forEach>
                        <img class="link" title="添加" ng-click="addAppraiseGroup($event, 'positiveAppraiseGroups')"
                             src="resources/css/images/add.png"/>
                    </td>
                </tr>
                <tr>
                    <td>相关差评</td>
                    <td>
                        <c:forEach var="appraiseGroup" items="${negativeAppraiseGroups}">
                            <input type="text" class="form-control" name="negativeAppraiseGroups"
                                   value="${appraiseGroup.content}"/>
                        </c:forEach>
                        <img class="link" title="添加" ng-click="addAppraiseGroup($event, 'negativeAppraiseGroups')"
                             src="resources/css/images/add.png"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="button">
            <button id="publishCommodity" name="publishSubmit" type="submit">
                ${commodity.id == null ? '发布商品' : '修改商品'}
            </button>
        </div>
        <div class="price">
            <div class="recommend">(建议不写)</div>
            <table>
                <tr>
                    <td class="title">历史低价</td>
                    <td class="text"><input type="text" class="form-control"/></td>
                    <td class="title">销量</td>
                    <td class="text"><input type="text" class="form-control"/></td>
                    <td class="title">最高售价</td>
                    <td class="text"><input type="text" class="form-control"/></td>
                </tr>
                <tr>
                    <td class="title">上价时间</td>
                    <td><input type="text" class="form-control"/></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="title">价格历史</td>
                    <td colspan="3"><input type="text" class="form-control"/></td>
                    <td colspan="2">
                        <span style="font-size: 9pt">
                        输入格式：<br>['2014-12-24', 83.83],['2015-01-04', 88.83]
                        </span>
                    </td>
                </tr>
            </table>
        </div>
        <div class="normal" ng-repeat="imageUrl in originalImageUrls" style="display:none;">
            <input type="hidden" name="imageUrls" value="{{imageUrl}}"/>
        </div>
        <div class="detail" ng-repeat="detailImageUrl in originalDetailImageUrls" style="display:none;">
            <input type="hidden" name="detailImageUrls" value="{{detailImageUrl}}"/>
        </div>
    </form>
    <div class="deepTransparent" id="submitInfo">
        <h1>正在处理，图片下载可能要一些时间，请耐心等待...</h1>
    </div>
    <div id="enlargeImage" class="boxShadow enlargeImage">
        <div class="normal" ng-repeat="imageUrl in originalImageUrls">
            <img ng-src="{{imageUrl}}"/>
        </div>
        <div class="detail" ng-repeat="detailImageUrl in originalDetailImageUrls">
            <img ng-src="{{detailImageUrl}}"/>
        </div>
        <div class="closeEnlargeIcon" ng-click="closeEnlargeImage()"></div>
    </div>
</div>
<%@include file="footer.jsp" %>
