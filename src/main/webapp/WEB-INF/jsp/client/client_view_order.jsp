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
<c:set value="${orderBean}" var="order"/>
<div class="container">
    <div class="card" style="margin: 3px">
        <div class="card-header">
            <fmt:message key="inner_text.order_number" var="order_number"/>
            <h5 class="card-title"><c:out value="${order_number}: ${order.id}"/></h5>
        </div>
        <div class="card-body">
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><fmt:message key="inner_text.from" var="from"/>
                    <c:out value="${from}: "/>
                    <c:choose>
                        <c:when test="${locale=='en'}">
                            <c:out value="${order.shippingAddress.description.en}"/>
                        </c:when>
                        <c:when test="${locale=='ua'}">
                            <c:out value="${order.shippingAddress.description.ua}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${order.shippingAddress.description.ua}"/>
                        </c:otherwise>
                    </c:choose>
                </li>
                <li class="list-group-item">
                    <fmt:message key="inner_text.to" var="to"/>
                    <c:out value="${to}: "/>
                    <c:choose>
                        <c:when test="${locale=='en'}">
                            <c:out value="${order.deliveryAddress.description.en}"/>
                        </c:when>
                        <c:when test="${locale=='ua'}">
                            <c:out value="${order.deliveryAddress.description.ua}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${order.deliveryAddress.description.ua}"/>
                        </c:otherwise>
                    </c:choose>
                </li>
                <li class="list-group-item">
                    <fmt:message key="inner_text.create_date" var="create_date"/>
                    <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${order.creationTime}"
                                    var="time"/>
                    <c:out value="${create_date}: ${time}"/>
                </li>
                <li class="list-group-item">
                    <fmt:message key="inner_text.consignee" var="consignee"/>
                    <c:out value="${consignee}: ${order.consignee}"/>
                </li>
                <li class="list-group-item"><c:out value="${order.description}"/></li>
                <fmt:message key="measurement_cm" var="cm"/>
                <fmt:message key="parameters_length" var="length"/>
                <li class="list-group-item"><c:out value="${length}: ${order.length} ${cm}"/></li>
                <fmt:message key="parameters_height" var="height"/>
                <li class="list-group-item"><c:out value="${height}: ${order.height} ${cm}"/></li>
                <fmt:message key="parameters_width" var="width"/>
                <li class="list-group-item"><c:out value="${width}: ${order.width} ${cm}"/></li>
                <fmt:message key="parameters_weight" var="weight"/>
                <fmt:message key="measurement_kg" var="kg"/>
                <li class="list-group-item"><c:out value="${weight}: ${order.weight} ${kg}"/></li>
                <fmt:message key="parameters_volume" var="volume"/>
                <fmt:message key="measurement_cc" var="cc"/>
                <li class="list-group-item"><c:out value="${volume}: ${order.volume} ${cc}"/></li>
                <fmt:message key="inner_text.fare" var="fare"/>
                <fmt:message key="inner_text.currency" var="currency"/>
                <li class="list-group-item"><c:out value="${fare}: ${order.fare} ${currency}"/></li>

                <c:if test="${order.deliveryDate ne null}">
                    <li class="list-group-item"><c:out value="${order.deliveryDate}"/></li>
                </c:if>
                <li class="list-group-item">
                    <fmt:message key="inner_text.shipping_status" var="status"/>
                    <c:out value="${status}: "/>
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
                </li>
                <button class="btn btn-primary" onclick="window.location.href='/controller?command=clientCabinet'">
                    <fmt:message key="button.back_to_orders"/></button>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
