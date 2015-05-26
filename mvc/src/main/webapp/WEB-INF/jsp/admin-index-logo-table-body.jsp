<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="indexLogo" items="${indexLogos}" varStatus="status">
    <tr id="indexLogo_${indexLogo.id}">
        <td class="index">${status.index+1}</td>
        <td class="path">
            <a class="limit-size" href="${indexLogo.path}" title="${indexLogo.path}" target="_blank"
               data-options="limit:26">${indexLogo.path}</a>
        </td>
        <td class="link">
            <a class="limit-size" href="${indexLogo.link}" title="${indexLogo.link}" target="_blank"
               data-options="limit:16">${indexLogo.link}</a>
        </td>
        <td class="description">${indexLogo.description}</td>
        <td class="thumbnail">
            <a href="${indexLogo.path}" target="_blank">
                <img src="${indexLogo.path}"/>
            </a>
        </td>
        <td>
            <jsp:include page="widget-edit-delete.jsp">
                <jsp:param name="editAction" value="editIndexLogo($event)"/>
                <jsp:param name="deleteAction" value="deleteIndexLogo($event)"/>
            </jsp:include>
            <jsp:include page="widget-ranking.jsp">
                <jsp:param name="upAction" value="rankUpIndexLogo($event)"/>
                <jsp:param name="downAction" value="rankDownIndexLogo($event)"/>
            </jsp:include>
        </td>
    </tr>
</c:forEach>
