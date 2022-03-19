<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 15.02.2022
  Time: 18:27
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
<fmt:message key="userCabinet.title" var="user_cabinet_title"/>
<c:set var="title" value="${user_cabinet_title}" scope="page"/>
<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@include file="/WEB-INF/jspf/header.jspf"%>
<!-- Language switcher begin -->
<form name="locales" action="/controller" method="post">
    <select name="lang" onchange="this.form.submit()">
        <option selected disabled><fmt:message key="register.chooseLang"/></option>
        <option value="ua"><fmt:message key="register.ua"/></option>
        <option value="en"><fmt:message key="register.en"/></option>
    </select>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="/controller?command=userCabinet">
</form>
<!-- end Language switcher -->

<div class="user_cabinet_main">
    <div>
        <c:if test="${clientOrders.size()>0}">
            <table>
                <tr>
                    <th><fmt:message key="userCabinet.from"/></th>
                    <th><fmt:message key="userCabinet.to"/></th>
                    <th><fmt:message key="userCabinet.create_date"/></th>
                    <th><fmt:message key="userCabinet.consignee"/></th>
                    <th><fmt:message key="userCabinet.fare"/></th>
                    <th><fmt:message key="userCabinet.shipping_status"/></th>
                    <th><fmt:message key="userCabinet.details"/></th>
                </tr>
                <c:forEach var="order" items="${clientOrders}">
                    <tr>
                        <td>${order.getShippingAddressID()}</td>
                        <td>${order.getDeliveryAddressID()}</td>
                        <td>${order.getCreationTime()}</td>
                        <td>${order.getConsignee()}</td>
                        <td>${order.getFare()}</td>
                        <td><c:choose>
                            <c:when test="${locale=='en'}">
                                ${order.getStatusID()}
                            </c:when>
                            <c:when test="${locale=='ua'}">
                                ${order.getStatusID()}
                            </c:when>
                            <c:otherwise>
                                ${order.getStatusID()}
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${order.getStatusID() == 1}">
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="payOrder">
                                <input type="hidden" name="order" value="${order.getId()}">
                                <fmt:message key="userCabinet.button.pay" var="button_pay"/>
                                <input type="submit" value="${button_pay}">
                            </form>
                        </c:if>
                        </td>
                        <td>
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="editOrder">
                                <input type="hidden" name="order" value="${order.getId()}">
                                <fmt:message key="userCabinet.button.show" var="button_show"/>
                                <input type="submit" value="${button_show}">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>

    <aside class="sidebar">
        <form action="/controller" method="post" style="display: inline">
            <input type="hidden" name="command" value="editUser">
            <input type="text" name="name" value="${client.name}" readonly><br>
            <input type="text" name="surname" value="${client.surname}" readonly><br>
            <input type="text" name="patronymic" value="${client.patronymic}" readonly><br>
            <input type="text" name="email" value="${client.email}" readonly><br>
            <input type="text" name="phone" value="${client.phone}" readonly><br>
            <input type="submit" value="Edit">
        </form>
        <p><fmt:message key="userCabinet.user_balance"/><c:out value=" ${client.getBalance()}"/></p>
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="recharge">
            <input type="number" name="balance" value="0" autocomplete="false">
            <fmt:message key="userCabinet.recharge_balance" var="recharge_balance"/>
            <input type="submit" value="${recharge_balance}">
        </form>
    </aside>
</div>
<c:out value="${message}"/>
<c:remove var="message"/>
</body>
</html>
