<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 30.03.2022
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!-- Set actual locale -->
<c:choose>
    <c:when test="${empty locale}">
        <fmt:setLocale value="ua" scope="session"/>
        <c:set var="locale" value="ua" scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="${locale}" scope="session"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="resource"/>
<html>
<%--<fmt:message key="userCabinet.title" var="user_cabinet_title"/>--%>
<c:set var="title" value="admin_cabinet" scope="page"/>
<%--<c:set var="title" value="${user_cabinet_title}" scope="page"/>--%>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<!-- Language switcher begin -->
<form name="locales" action="/controller" method="post">
    <select name="lang" onchange="this.form.submit()">
        <option selected disabled><fmt:message key="register.chooseLang"/></option>
        <option value="ua"><fmt:message key="register.ua"/></option>
        <option value="en"><fmt:message key="register.en"/></option>
    </select>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="/controller?command=adminCabinet">
</form>
<!-- end Language switcher -->
<div class="admin_cabinet_main">
    <c:set var="statuses" value="${applicationScope['status_description']}"/>
    <table>
        <tr>
            <th>sender</th> <!--replace fmt-->
            <th><fmt:message key="userCabinet.from"/></th>
            <th><fmt:message key="userCabinet.to"/></th>
            <th><fmt:message key="userCabinet.create_date"/></th>
            <th><fmt:message key="userCabinet.consignee"/></th>
            <th><fmt:message key="userCabinet.fare"/></th>
            <th><fmt:message key="userCabinet.shipping_status"/></th>
            <th><fmt:message key="userCabinet.details"/></th>
        </tr>
        <c:forEach var="order" items="${allOrders}">
            <tr>
                <td><c:out value="${order.client}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${locale=='en'}">
                            <c:out value="${order.shippingAddress.en}"/>
                        </c:when>
                        <c:when test="${locale=='ua'}">
                            <c:out value="${order.shippingAddress.ua}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${order.shippingAddress.ua}"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${locale=='en'}">
                            <c:out value="${order.deliveryAddress.en}"/>
                        </c:when>
                        <c:when test="${locale=='ua'}">
                            <c:out value="${order.deliveryAddress.ua}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${order.deliveryAddress.ua}"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${order.creationTime}"/></td>
                <td><c:out value="${order.consignee}"/></td>
                <td><c:out value="${order.fare}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${locale=='en'}">
                            <c:out value="${order.status.en}"/>
                        </c:when>
                        <c:when test="${locale=='ua'}">
                            <c:out value="${order.status.ua}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${order.status.ua}"/>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${order.status.ua eq 'створений' or order.status.en eq 'created'}">
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="confirmOrder">
                            <input type="hidden" name="order" value="${order.getId()}">
                            <fmt:message key="adminCabinet.button.confirm" var="button_confirm"/>
                            <input type="submit" name="procedure" value="${button_confirm}"><br>
                            <fmt:message key="button.cancel" var="button_cancel"/>
                            <input type="submit" name="procedure" value="${button_cancel}">
                        </form>
                    </c:if>
                </td>
                <td>
                    <form action="/controller" method="post">
                        <input type="hidden" name="command" value="adminEditOrder">
                        <input type="hidden" name="order" value="${order.getId()}">
                        <fmt:message key="adminCabinet.button.show" var="button_show"/>
                        <input type="submit" value="${button_show}">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:out value="${message}"/>
    <c:remove var="message"/>
</div>
</body>
</html>
