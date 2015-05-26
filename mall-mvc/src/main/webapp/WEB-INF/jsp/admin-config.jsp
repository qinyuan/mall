<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <form id="mainForm" method="post" action="admin-config-update" enctype="multipart/form-data">
        <fieldset>
            <legend>网站图标设置</legend>
            <div class="uploadImage">
                <label>浏览器标题栏显示的图标(url或上传图片)</label><br/>
                <jsp:include page="admin-config-upload-image.jsp">
                    <jsp:param name="id" value="favicon"/>
                    <jsp:param name="value" value="${appConfig.favicon}"/>
                </jsp:include>
            </div>
        </fieldset>
        <div class="split"></div>
        <fieldset>
            <legend>页头设置</legend>
            <div class="uploadImage">
                <label>页头横幅(url或上传图片)</label><br/>
                <jsp:include page="admin-config-upload-image.jsp">
                    <jsp:param name="id" value="globalBanner"/>
                    <jsp:param name="value" value="${appConfig.globalBanner}"/>
                </jsp:include>
            </div>
            <div class="uploadImage">
                <label>页头Logo(url或上传图片)</label><br/>
                <jsp:include page="admin-config-upload-image.jsp">
                    <jsp:param name="id" value="globalLogo"/>
                    <jsp:param name="value" value="${appConfig.globalLogo}"/>
                </jsp:include>
            </div>
        </fieldset>
        <div class="split"></div>
        <fieldset id="footLink">
            <legend>页尾链接设置</legend>
            <table class="normal">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>显示内容</th>
                    <th>目标链接</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="footLink" items="${footLinks}" varStatus="status">
                    <tr id="footLink_${footLink.id}">
                        <td class="index">${status.index+1}</td>
                        <td class="text">${footLink.text}</td>
                        <td class="link"><a href="${footLink.link}" target="_blank">
                                ${footLink.link}
                        </a></td>
                        <td class="action">
                            <jsp:include page="widget-edit-delete.jsp">
                                <jsp:param name="editAction" value="editFootLink($event)"/>
                                <jsp:param name="deleteAction" value="deleteFootLink($event)"/>
                            </jsp:include>
                            <jsp:include page="widget-ranking.jsp">
                                <jsp:param name="upAction" value="rankUpFootLink($event)"/>
                                <jsp:param name="downAction" value="rankDownFootLink($event)"/>
                            </jsp:include>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div>
                <button type="button" class="btn btn-success" ng-click="addFootLink()">
                    添加
                </button>
                <button type="button" class="btn btn-default" ng-click="resetFootLink()">
                    恢复初始设置
                </button>
            </div>
        </fieldset>
        <div class="split"></div>
        <fieldset>
            <legend><a target="_blank" href="index.html">主页(index.html)</a>设置</legend>
            <div class="uploadImage">
                <label>首页头部海报(url或上传图片)</label><br/>
                <jsp:include page="admin-config-upload-image.jsp">
                    <jsp:param name="id" value="indexHeadPoster"/>
                    <jsp:param name="value" value="${appConfig.indexHeadPoster}"/>
                </jsp:include>
            </div>
            <div class="uploadImage">
                <label>首页尾部海报(url或上传图片)</label><br/>
                <jsp:include page="admin-config-upload-image.jsp">
                    <jsp:param name="id" value="indexFootPoster"/>
                    <jsp:param name="value" value="${appConfig.indexFootPoster}"/>
                </jsp:include>
                <br/>
                <label>目标链接: </label>
                <input style="width:150px;" type="text" name="indexFootPosterLink"
                       value="${appConfig.indexFootPosterLink}"/>
            </div>
        </fieldset>
        <div class="split"></div>
        <fieldset>
            <legend><a href="list.html" target="_blank">商品列表页(list.html)</a>设置</legend>
            <div class="uploadImage">
                <label>名牌排行图片(url或上传图片)</label><br/>
                <jsp:include page="admin-config-upload-image.jsp">
                    <jsp:param name="id" value="branchRankImage"/>
                    <jsp:param name="value" value="${appConfig.branchRankImage}"/>
                </jsp:include>
            </div>
            <div class="uploadImage">
                <label>无对应商品时显示的图片(url或上传图片)</label><br/>
                <jsp:include page="admin-config-upload-image.jsp">
                    <jsp:param name="id" value="noFoundImage"/>
                    <jsp:param name="value" value="${appConfig.noFoundImage}"/>
                </jsp:include>
            </div>
            <div class="uploadImage">
                <label>无对应商品时显示的文本</label><br/>
                <textarea name="noFoundText" cols="32" rows="4">${appConfig.noFoundText}</textarea>
            </div>
        </fieldset>
        <div class="split"></div>
        <fieldset class="textOnly">
            <legend><a href="admin.html" target="_blank">管理员主页(admin.html)</a>设置</legend>
            <div>
                <label>商品列表中每个分页的商品数量(0表示无限制)</label>
                <input type="text" name="adminPaginationCommoditySize"
                       value="${appConfig.adminPaginationCommoditySize}"/>
            </div>
            <div>
                <label>商品列表中每个分页的底部链接数量(0表示无限制)</label>
                <input type="text" name="adminPaginationButtonSize"
                       value="${appConfig.adminPaginationButtonSize}"/>
            </div>
        </fieldset>
        <div class="split"></div>
        <fieldset class="textOnly">
            <legend><a href="admin-edit-commodity.html" target="_blank">商品编辑页(admin-edit-commodity.html)</a>设置</legend>
            <div>
                <label>商品图片数量限制(0表示无限制)</label>
                <input type="text" name="maxCommodityPictureSize"
                       value="${appConfig.maxCommodityPictureSize}"/>
            </div>
            <div>
                <label>商品描述图片数量限制(0表示无限制)</label>
                <input type="text" name="maxCommodityDetailPictureSize"
                       value="${appConfig.maxCommodityDetailPictureSize}"/>
            </div>
        </fieldset>
        <div class="split"></div>
        <div class="button">
            <button type="submit" class="btn btn-primary" ng-click="validateConfigInput($event)">
                提交修改
            </button>
        </div>
    </form>
    <form class="fixedForm" id="footLinkForm" method="post" action="admin-foot-link-add-update">
        <input type="hidden" name="id"/>

        <div>
            <label>显示文字</label>
            <span class="required">*</span>
            <br/>
            <input type="text" name="text"/>
        </div>
        <div>
            <label>目标链接</label>
            <span class="required">*</span>
            <br/>
            <input type="text" name="link"/>
        </div>
        <%@include file="admin-form-submit.jsp" %>
    </form>
</div>
<%@include file="footer.jsp" %>