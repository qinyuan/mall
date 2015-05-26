<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <form id="detailForm" method="post" action="admin-detail-update">
        <div class="text">
            <label>相关商品数量限制(0表示无限制)：</label>
            <input type="text" style="width: 30px;" name="relatedCommoditySize"
                   value="${appConfig.relatedCommoditySize}"/>
            <h4>文字描述：</h4>
            <textarea class="ckeditor" name="detailText">${appConfig.detailText}</textarea>

            <div class="submit">
                <button id="editSubmit" type="submit" class="btn btn-success">
                    提交修改
                </button>
            </div>
        </div>
        <div class="image">
            <h4>相关图片：</h4>
            <table class="normal">
                <colgroup>
                    <col class="image"/>
                    <col class="link"/>
                    <col class="action"/>
                </colgroup>
                <thead>
                <tr>
                    <th>图片链接</th>
                    <th>目标链接</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="detailImage" items="${appConfig.detailImages}">
                    <tr id="detailImage_${detailImage.id}">
                        <td>
                            <a class="limit-size" data-options="limit:40" href="${detailImage.path}"
                               target="_blank" title="${detailImage.path}">${detailImage.path}</a>
                        </td>
                        <td>
                            <a class="limit-size" data-options="limit:20" href="${detailImage.link}"
                               target="_blank" title="${detailImage.link}">${detailImage.link}</a>
                        </td>
                        <td class="action">
                            <jsp:include page="widget-edit-delete.jsp">
                                <jsp:param name="editAction" value="editImage($event)"/>
                                <jsp:param name="deleteAction" value="deleteImage($event)"/>
                            </jsp:include>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div>
            <button id="addImage" ng-click="addImage()" type="button" class="btn btn-default">
                增加图片
            </button>
        </div>
    </form>
    <fieldset class="effect">
        <legend>效果</legend>
        <div class="effect">
            <%@include file="widget-detail-text.jsp" %>
            <%@include file="widget-detail-images.jsp" %>
        </div>
    </fieldset>
    <form class="fixedForm" id="imageForm" method="post" action="admin-detail-image-add-update.json"
          enctype="multipart/form-data">
        <input type="hidden" name="id"/>

        <div>
            <label>图片(url或上传图片)</label><span class="required">*</span><br/>
            <jsp:include page="widget-upload-image.jsp">
                <jsp:param name="id" value="image"/>
            </jsp:include>
        </div>
        <div>
            <label>目标链接</label><br/>
            <input type="text" name="link"/>
        </div>
        <div>
            <button id="addImageSubmit" type="submit" class="btn btn-success"
                    ng-click="validateImageInput($event)">
                添加
            </button>
            <button id="editImageSubmit" type="submit" class="btn btn-success"
                    ng-click="validateImageInput($event)">
                修改
            </button>
            <button ng-click="cancelImageInput()" type="button" class="btn btn-default">
                取消
            </button>
        </div>
    </form>
</div>
<%@include file="footer.jsp" %>