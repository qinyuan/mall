<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <form action="admin-add-commodities-submit" method="post" id="mainForm">
        <%@include file="admin-add-commodity-basic.jsp" %>
        <div class="link">
            <table class="showIds">
                <tr>
                    <td class="title">商品ID号</td>
                    <td class="body showIds">
                        <div class="input-group" role="group">
                            <input type="text" class="form-control" value="" placeholder="在此输入商品ID号"/>
                            <img class="link" title="添加" ng-click="addShowId($event)"
                                 src="resources/css/images/add.png"/>
                        </div>
                        <%@include file="admin-commodity-crawler-button.jsp" %>
                    </td>
                </tr>
            </table>
            <div class="commodityInfo" style="display: none;" ng-show="true" ng-repeat="commodity in commodities">
                <div class="links">
                    <div class="title">爬虫链接</div>
                    <input type="hidden" name="showId" value="{{commodity.showId}}"/>

                    <div class="body">
                        <input type="hidden" name="url" value="{{commodity.crawlerUrl}}"/>
                        <a class="crawlerLink" href="{{commodity.crawlerUrl}}" target="_blank">{{commodity.crawlerUrl}}</a>
                    </div>
                    <div class="title">购买链接</div>
                    <div class="body">
                        <input type="hidden" name="buyUrl" value="{{commodity.buyUrl}}"/>
                        <a class="buyLink" href="{{commodity.buyUrl}}" target="_blank">{{commodity.buyUrl}}</a>
                    </div>
                    <div class="title">商品名称</div>
                    <div class="body">
                        <input type="hidden" name="commodityName" value="{{commodity.name}}"/>
                        <a class="noLineAnchor keyWordLink" target="_blank"
                           href="{{commodity.crawlerUrl}}">{{commodity.name}}</a>
                    </div>
                    <%--
                    <div class="title">商品参数</div>
                    <div class="body">
                        <textarea id="commodityDescription_{{commodity.showId}}" name="parameters"
                                  ng-keyup="commodityDescriptionKeyup($event)"
                                  onkeyup="commodityDescriptionKeyup(this)"
                                  cols="45" rows="12">{{commodity.parameters}}</textarea>
                    </div>
                    <div class="parametersEffect" id="commodityDescriptionEffect_{{commodity.showId}}"></div>
                    --%>
                </div>
                <div class="images">
                    <div class="commodityImage">
                        <div class="title">商品图片</div>
                        <div class="imageGroup">
                            <div class="image" ng-repeat="imageUrl in commodity.imageUrls">
                                <input type="hidden" name="imageUrls" value="{{commodity.showId + '_' + imageUrl}}"/>
                                <img class="link" ng-src="{{imageUrl}}" title="单击放大"
                                     ng-click="enlargeImage(commodity.showId, $index, 'normal')"/><br/>
                                <a class="noLineAnchor" href="javascript:void(0)"
                                   ng-click="deleteImage(commodity.showId, $index)">删除</a>
                            </div>
                        </div>
                    </div>
                    <div class="commodityDescImage">
                        <div class="title">商品描述图片</div>
                        <div class="imageGroup">
                            <div class="image" ng-repeat="detailImageUrl in commodity.detailImageUrls">
                                <input type="hidden" name="detailImageUrls"
                                       value="{{commodity.showId + '_' + detailImageUrl}}"/>
                                <img class="link" ng-src="{{detailImageUrl}}" title="单击放大"
                                     ng-click="enlargeImage(commodity.showId, $index, 'detail')"/><br/>
                                <a class="noLineAnchor" href="javascript:void(0)"
                                   ng-click="deleteDetailImage(commodity.showId, $index)">删除</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="deleteLink" ng-click="deleteCommodity($index)">
                    <a class="noLineAnchor" href="javascript:void(0)">删除</a>
                </div>
            </div>
        </div>
        <div class="button">
            <button id="publishCommodity" name="publishSubmit" type="submit">发布商品</button>
        </div>
    </form>
    <div class="deepTransparent" id="submitInfo">
        <h1>正在处理，图片下载可能要一些时间，请耐心等待...</h1>
    </div>
    <div id="enlargeImage_{{commodity.showId}}" class="boxShadow enlargeImage" ng-repeat="commodity in commodities">
        <div class="normal" ng-repeat="imageUrl in commodity.imageUrls">
            <img ng-src="{{imageUrl}}"/>
        </div>
        <div class="detail" ng-repeat="detailImageUrl in commodity.detailImageUrls">
            <img ng-src="{{detailImageUrl}}"/>
        </div>
        <div class="closeEnlargeIcon" ng-click="closeEnlargeImage()"></div>
    </div>
</div>
<%@include file="footer.jsp" %>
