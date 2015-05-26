<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<input type="hidden" id="commodityId" value="${commodity.id}"/>
<div class="left">
    <div class="snapshot boxShadow">
        <div class="left">
            <div class="branch">
                <c:if test="${branch!=null}">
                    所属品牌：
                    <a href="shoppe?id=${branch.id}" target="_blank">
                        <img class="branch-logo" src="${branch.logo}"/>
                    </a>
                </c:if>
            </div>
            <script id="pictures" type="text/x-data">${pictures}</script>
            <script id="middlePictures" type="text/x-data">${middlePictures}</script>
            <div class="largeImage">
                <c:if test="${fn:length(pictures)>0}">
                    <c:forEach var="picture" items="${middlePictures}" varStatus="status">
                        <img class="boxShadow" src="${picture}" onload="adjustImage(this, 300, 300)"
                             <c:if test="${status.index >0 }">style="display: none;"</c:if>/>
                    </c:forEach>

                    <div class="enlargeIcon mediumTransparent"></div>

                </c:if>
            </div>
            <div class="enlargeImage boxShadow">
                <c:forEach var="picture" items="${pictures}" varStatus="status">
                    <img src="${picture}" onload="adjustImage(this, 600, 600)"/>
                </c:forEach>

                <div class="closeEnlargeIcon"></div>
            </div>
            <div class="smallImage">
                <c:forEach var="picture" items="${smallPictures}" varStatus="status">
                    <img class="link" data-options="index:${status.index}"
                         src="${picture}" onload="adjustImageHeight(this, 42)"/>
                </c:forEach>
            </div>
        </div>
        <div class="right">
            <div class="name">${commodity.name}</div>
            <div class="price">
                <div>
                    价格： <span class="lowPrice">￥${currentPrice}</span>
                    销量： <span class="sales">${commodity.sales}</span>件
                </div>
                <div>
                    最高售价： <span class="highestPrice">￥${highPrice}</span>
                    上架时间： <span class="releaseDate">${commodity.onShelfTime}</span>
                </div>
                <div class="trend">
                    <div class="text">
                        <span>
                            相关评价 和 历史价格走势
                            <img class="link" src="resources/css/images/detail/trend.png"/>
                        </span>
                    </div>
                    <div class="priceHistory boxShadow">
                        <div id="trendChart"></div>
                        <div class="triangle-border tb-border"></div>
                        <div class="triangle-border tb-background"></div>
                        <c:if test="${fn:length(positiveAppraiseGroups)>0 || fn:length(negativeAppraiseGroups)>0}">
                            <div class="comments">
                                <div class="appraiseGroups">
                                    <c:forEach var="appraiseGroup" items="${positiveAppraiseGroups}">
                                        <div class="appraiseGroup">
                                            <a class="noLineAnchor positive"
                                               href="javascript:void(0)">${appraiseGroup.content}</a>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="appraiseGroups">
                                    <c:forEach var="appraiseGroup" items="${negativeAppraiseGroups}">
                                        <div class="appraiseGroup">
                                            <a class="noLineAnchor negative"
                                               href="javascript:void(0)">${appraiseGroup.content}</a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="buySubmit">
                <c:if test="${fn:length(branch.shoppes)>0}">
                    <%--
                    <span id="couponLink" class="noLineAnchor">
                        别忘了领取
                        <a class="noLineAnchor" href="${branch.shoppes[0].url}" target="_blank">
                            <img src="resources/css/images/edit-commodity/coupon.png"/>
                        </a>
                        哦
                    </span>
                    --%>
                    <a id="couponLink" class="noLineAnchor" href="${branch.shoppes[0].url}" target="_blank">
                        去看看有没有
                        <img src="resources/css/images/edit-commodity/coupon.png"/>
                    </a>
                </c:if>
                <a id="buySubmit" class="noLineAnchor orangeButton" href="${commodity.buyUrl}"
                   target="_blank">去购买</a>
            </div>
        </div>
        <div class="share">
            <div class="foundTime">
                发现时间：<span id="foundTime">${commodity.discoverTime}</span>
            </div>
            <div class="links">
                告诉小伙伴：
                <span class="links">
                <c:forEach var="link" items="${share.links}">
                    <a href="${link.url}" title="${link.title}" target="_blank"><img src="${link.icon}"/></a>
                </c:forEach>
                </span>
            </div>
        </div>
        <div class="description">
            <div class="title">
                <span class="goodsDetail selected">商品详情</span>
                <img src="resources/css/images/detail/vertical-split.png"/>
                <span class="shoppe">${branch.name}官方店铺</span>
                <img src="resources/css/images/detail/vertical-split.png"/>
            </div>
            <div class="detail">
                <script id="commodityParametersData" type="text/x-data">${commodity.parameters}</script>
                <div id="commodityParameters" class="goodsDetail"></div>
                <div class="shoppe">
                    <%@include file="widget-shoppe-link.jsp" %>
                </div>
            </div>
        </div>
    </div>
    <c:forEach var="detailPicture" items="${detailPictures}">
        <div class="detailImage boxShadow">
            <img src="${detailPicture}" onload="adjustImageWidth(this, 670);"/>
        </div>
    </c:forEach>
</div>
<div class="right">
    <div class="whiteBack boxShadow">
        <div class="title">
            更多相关商品
        </div>
        <div class="other">
            <c:forEach var="commodity" items="${relatedCommodities}">
                <div>
                    <div class="price">
                        <div class="priceBack deepTransparent"></div>
                        <div class="text">￥ ${commodity.price}</div>
                    </div>
                    <a href="detail?id=${commodity.id}" target="_blank">
                        <img src="${commodity.picture}"/>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="text">
        <%@include file="widget-detail-text.jsp" %>
        <%@include file="widget-detail-images.jsp" %>
    </div>
    <!--
    <div class="attention">
        <div class="title">关注我们</div>
        <div class="link">
            豆瓣：<a href="javascript:void(0)">豆豆</a><br>
            微博：<a href="javascript:void(0)">@豆瓣东西</a>
        </div>
    </div>
    -->
    <div class="advice">
        <a target="_blank" href="company-info?tab=2">我想给你们些建议</a>
    </div>
</div>
<%@include file="footer.jsp" %>
