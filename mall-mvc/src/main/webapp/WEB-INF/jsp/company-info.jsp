<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="header.jsp" %>
<div class="title">关于我们</div>
<div class="center">
    <div class="detail boxShadow" id="contentDetail"></div>
    <div class="rightNavigation">
        <div class="image">
            <img src="resources/css/images/company_info/01.gif"/>
        </div>
        <div class="company selected">
            <a href="javascript:void(0)" class="darkFont noLineAnchor" id="aboutUs">关于我们</a>
        </div>
        <div class="company">
            <a href="javascript:void(0)" class="darkFont noLineAnchor" id="contactUs">联系我们</a>
        </div>
        <div class="company">
            <a href="javascript:void(0)" class="darkFont noLineAnchor" id="freeDeclaration">免责声明</a>
        </div>
        <div class="company">
            <a href="javascript:void(0)" class="darkFont noLineAnchor" id="friendLinks">友情链接</a>
        </div>
    </div>
</div>
<script id="aboutUsTpl" type="text/x-handlebars-template">
    <div class="blueFont" style="font-size: 14pt;font-weight: bold; margin: 10px 0;">EUROPE</div>
    <div>Stories curated by Lonely Plantet's Europe Destination Editors:
        <span class="blueFont">Jo Cooke, James Smart, Brana Vladisavljievic, Kate Morgan, Anna Tyler</span>
        and
        <span class="blueFont">Gemma Graham</span>
    </div>
    <img src="resources/css/images/company_info/02.gif"/>

    <div>Rooftops of Catalonia. <span class="blueFont">Image by Scott Wylie / CC BY 2.0</span></div>
</script>
<script id="contactUsTpl" type="text/x-handlebars-template">
    <div style="font-weight: bold;">Count Denmark lay claim to the scariest theme park ride?</div>
    <div>An attraction at Denmark's Tivoli Frihedent in Aarhus has bean ganing notoriety ootoriety the internet, with
        many wondering if it could be the world's scariest. Sky Tower invober inv s being droped 100 feet, with nothing
        but a simple net to break the fall. The exper the expense is apparently so trerrifying that similar rides have
        been used in experiments to simots to sir te near-death experiences. Read more: <span class="blueFont">buffingtonpost.com</span>
    </div>
    <table class="advice">
        <tr>
            <td class="prompt">您的意见/建议</td>
            <td><textarea class="form-control" rows="8"></textarea></td>
        </tr>
        <tr>
            <td class="prompt">您的联系方式</td>
            <td>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="邮箱/QQ号"/>
                    <span class="input-group-addon">(xxxxxxxxxxxx一段文字)</span>
                </div>
            </td>
        </tr>
        <tr>
            <td class="prompt">验证码</td>
            <td>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="请输入验证码"/>
                </div>
            </td>
        </tr>
    </table>
    <div class="submit">
        <button type="button" class="orangeButton">提交</button>
    </div>
</script>
<script id="freeDeclarationTpl" type="text/x-handlebars-template">
    <div>免责声明...</div>
</script>
<script id="friendLinksTpl" type="text/x-handlebars-template">
    <div>友情链接...</div>
</script>
<%@include file="footer.jsp" %>
