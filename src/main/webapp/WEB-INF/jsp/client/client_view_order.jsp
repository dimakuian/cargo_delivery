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
<fmt:message key="inner_text_title_view_order" var="title"/>
<c:set var="title" value="${title}" scope="page"/>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<!-- Language switcher begin -->
<form name="locales" action="/controller" method="post">
    <select name="lang" onchange="this.form.submit()">
        <option selected disabled><fmt:message key="language.chooseLang"/></option>
        <option value="ua"><fmt:message key="language.ua"/></option>
        <option value="en"><fmt:message key="language.en"/></option>
    </select>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="/controller?command=clientViewOrder&orderID=${orderBean.id}">
</form>
<!-- end Language switcher -->
<div class="container-fluid">
    <c:set value="${orderBean}" var="order"/>
    <c:choose>
        <c:when test="${locale=='en'}">
            <c:out value="${order.shippingAddress.en}"/><br>
            <c:out value="${order.deliveryAddress.en}"/><br>
        </c:when>
        <c:when test="${locale=='ua'}">
            <c:out value="${order.shippingAddress.ua}"/><br>
            <c:out value="${order.deliveryAddress.ua}"/><br>
        </c:when>
        <c:otherwise>
            <c:out value="${order.shippingAddress.ua}"/><br>
            <c:out value="${order.deliveryAddress.ua}"/><br>
        </c:otherwise>
    </c:choose>
    <c:out value="${order.creationTime}"/><br>
    <c:out value="${order.consignee}"/><br>
    <c:out value="${order.description}"/><br>
    <c:out value="${order.length}"/><br>
    <c:out value="${order.height}"/><br>
    <c:out value="${order.width}"/><br>
    <c:out value="${order.weight}"/><br>
    <c:out value="${order.volume}"/><br>
    <c:out value="${order.fare}"/><br>
    <c:if test="${order.deliveryDate ne null}">
        <c:out value="${order.deliveryDate}"/>
    </c:if>
    <c:choose>
        <c:when test="${locale=='en'}">
            <c:out value="${order.status.en}"/><br>
        </c:when>
        <c:when test="${locale=='ua'}">
            <c:out value="${order.status.ua}"/><br>
        </c:when>
        <c:otherwise>
            <c:out value="${order.status.ua}"/><br>
        </c:otherwise>
    </c:choose>
    <button onclick="window.location.href='/controller?command=clientCabinet'">Назад</button>
</div>
</body>
</html>
