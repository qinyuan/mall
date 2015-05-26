<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="boxShadow">
    <table class="normal">
        <colgroup>
            <col class="index"/>
            <col class="name"/>
            <col class="firstLetter"/>
            <col class="parent"/>
            <col class="logo"/>
            <col class="shoppe"/>
            <col class="slogan"/>
            <col class="edit"/>
        </colgroup>
        <thead>
        <tr>
            <th>序号</th>
            <th>名称</th>
            <th>首字母</th>
            <th>父品牌</th>
            <th>logo</th>
            <th>旗舰店</th>
            <th>品牌口号</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="branch" items="${branches}" varStatus="status">
            <tr id="branch_${branch.id}">
                <td class="index">${status.index+1}</td>
                <td class="name">${branch.name}</td>
                <td class="firstLetter">${branch.firstLetter}</td>
                <td class="parent" data-options="parentId: ${branch.parentId}">${branch.parentName}</td>
                <td class="logo">
                    <div class="rectangle">
                        矩形: <a href="${branch.logo}" title="${branch.logo}"
                               target="_blank"><img onload="adjustImageWidth(this, 82);" src="${branch.logo}"/></a>
                    </div>
                    <div class="squareLogo">
                        方形: <a href="${branch.squareLogo}" title="${branch.squareLogo}"
                               target="_blank"><img onload="adjustImageWidth(this, 82);"
                                                    src="${branch.squareLogo}"/></a>
                    </div>
                    <div class="poster">
                        海报: <a href="${branch.poster}" title="${branch.poster}"
                               target="_blank"><img onload="adjustImageWidth(this, 82);" src="${branch.poster}"/></a>
                    </div>
                </td>
                <td class="shoppe">
                    <%@include file="widget-shoppe-link.jsp" %>
                </td>
                <td class="slogan">${branch.slogan}</td>
                <td class="edit">
                    <jsp:include page="widget-edit-delete.jsp">
                        <jsp:param name="editAction" value="editBranch($event)"/>
                        <jsp:param name="deleteAction" value="deleteBranch($event)"/>
                    </jsp:include>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form id="branchForm" method="post" action="admin-branch-add-update"
          enctype="multipart/form-data">
        <input type="hidden" name="id"/>

        <div class="basic">
            <label>名称</label>
            <span class="required">*</span>
            <input type="text" id="name" name="name"/>
            <label>首字母</label>
            <span class="required">*</span>
            <input type="text" id="firstLetter" name="firstLetter"/>
            <label>父品牌</label>
            <jsp:include page="admin-branch-select.jsp">
                <jsp:param name="elementId" value="parentId"/>
            </jsp:include>
        </div>
        <div class="logo">
            <label>矩形logo(url或上传图片)</label>
            <span class="required">*</span>
            <jsp:include page="widget-upload-image.jsp">
                <jsp:param name="id" value="logo"/>
            </jsp:include>
        </div>
        <div class="logo">
            <label>方形logo(url或上传图片)</label>
            <span class="required">*</span>
            <jsp:include page="widget-upload-image.jsp">
                <jsp:param name="id" value="squareLogo"/>
            </jsp:include>
        </div>
        <div class="logo">
            <label>品牌海报(url或上传图片)</label>
            <jsp:include page="widget-upload-image.jsp">
                <jsp:param name="id" value="poster"/>
            </jsp:include>
        </div>
        <div class="slogan">
            <label>品牌口号</label>
            <textarea cols="65" rows="3" id="slogan" name="slogan"></textarea>
        </div>
        <div class="shoppe">
            <div class="left"><label>官方旗舰店</label></div>
            <div class="right">
                <div ng-repeat="shoppe in shoppes">
                    <label>名称</label><input type="text" name="shoppeNames" value="{{shoppe.name}}"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <label>url</label><input type="text" name="shoppeUrls" value="{{shoppe.url}}"/>
                    <img ng-click="deleteShoppe($index)" class="link" title="删除"
                         src="resources/css/images/table/action_delete.png"/>
                </div>
                <img ng-click="addShoppe()" class="link" title="添加" src="resources/css/images/add.png"/>
            </div>
        </div>
        <%@include file="admin-form-submit.jsp" %>
    </form>
</div>
<%@include file="footer.jsp" %>