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
<c:set var="title" value="admin_cabinet" scope="page"/>
<%--<fmt:message key="userCabinet.title" var="user_cabinet_title"/>--%>
<%--<c:set var="title" value="${user_cabinet_title}" scope="page"/>--%>
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
    <input type="hidden" name="page" value="/controller?command=adminCabinet">
</form>
<!-- end Language switcher -->
<div class="admin_cabinet_main" style="display: inline-block">
    <c:set var="statuses" value="${applicationScope['status_description']}"/>

    <!-- button for fort table -->
    <form action="/controller" method="do">
        <select name="sort" onchange="this.form.submit()">
            <option selected disabled><fmt:message key="inner_text.sort_by"/></option>
            <option value="id ASC"><fmt:message key="sort_type.number_lowest"/></option>
            <option value="id DESC"><fmt:message key="sort_type.number_highest"/></option>
            <option value="surname ASC"><fmt:message key="sort_type.sender_A_Z"/></option>
            <option value="surname DESC"><fmt:message key="sort_type.sender_Z_A"/></option>
            <c:choose>
                <c:when test="${locale=='en'}">
                    <option value="shipping_city_en ASC"><fmt:message key="sort_type.shipping_address_A_Z"/></option>
                    <option value="shipping_city_en DESC"><fmt:message key="sort_type.shipping_address_Z_A"/></option>
                    <option value="delivery_city_en ASC"><fmt:message key="sort_type.delivery_address_A_Z"/></option>
                    <option value="delivery_city_en DESC"><fmt:message key="sort_type.delivery_address_Z_A"/></option>
                </c:when>
                <c:when test="${locale=='ua'}">
                    <option value="shipping_city_ua ASC"><fmt:message key="sort_type.shipping_address_A_Z"/></option>
                    <option value="shipping_city_ua DESC"><fmt:message key="sort_type.shipping_address_Z_A"/></option>
                    <option value="delivery_city_ua ASC"><fmt:message key="sort_type.delivery_address_A_Z"/></option>
                    <option value="delivery_city_ua DESC"><fmt:message key="sort_type.delivery_address_Z_A"/></option>
                </c:when>
                <c:otherwise>
                    <option value="shipping_city_ua ASC"><fmt:message key="sort_type.shipping_address_A_Z"/></option>
                    <option value="shipping_city_ua DESC"><fmt:message key="sort_type.shipping_address_Z_A"/></option>
                    <option value="delivery_city_ua ASC"><fmt:message key="sort_type.delivery_address_A_Z"/></option>
                    <option value="delivery_city_ua DESC"><fmt:message key="sort_type.delivery_address_Z_A"/></option>
                </c:otherwise>
            </c:choose>
            <option value="status_id ASC"><fmt:message key="sort_type.status_show_new"/></option>
        </select>
        <input type="hidden" name="command" value="adminCabinet">
        <input type="hidden" name="page_number" value="${currentPage}">
    </form>
    <table>
        <tr>
            <th><fmt:message key="inner_text.order_number"/></th>
            <th><fmt:message key="inner_text.sender"/></th>
            <th><fmt:message key="inner_text.from"/></th>
            <th><fmt:message key="inner_text.to"/></th>
            <th><fmt:message key="inner_text.create_date"/></th>
            <th><fmt:message key="inner_text.consignee"/></th>
            <th><fmt:message key="inner_text.fare"/></th>
            <th><fmt:message key="inner_text.shipping_status"/></th>
            <th><fmt:message key="inner_text.details"/></th>
        </tr>
        <c:forEach var="order" items="${allOrders}">
            <tr>
                <td><c:out value="${order.id}"/></td>
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
    <br>
    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <table>
        <tr>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td><c:out value="${i}"/></td>
                    </c:when>
                    <c:otherwise>
                        <td>
                            <a href="/controller?command=adminCabinet&page_number=${i}&sort=${currentSort}">
                                <c:out value="${i}"/></a>
                        </td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
    <c:out value="${message}"/>
    <c:remove var="message"/>
</div>
</body>
</html>
