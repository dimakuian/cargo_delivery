<jsp:useBean id="recordPerPage" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="sort" scope="request" type="java.lang.String"/>
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

<fmt:message key="admin_orders.jsp.title" var="adminOrders"/>
<c:set var="title" value="${adminOrders}" scope="page"/>
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
    <input type="hidden" name="page"
           value="/controller?command=adminOrders&page_number=${currentPage}&sort=${sort}">
</form>
<!-- end Language switcher -->
<%--<div>--%>
<%--    <!-- button for sort content -->--%>
<%--    <form action="/controller" method="do">--%>
<%--        <select name="sort" onchange="this.form.submit()">--%>
<%--            <option selected disabled><fmt:message key="inner_text.sort_by"/></option>--%>
<%--            <option value="id ASC"><fmt:message key="sort_type.number_lowest"/></option>--%>
<%--            <option value="id DESC"><fmt:message key="sort_type.number_highest"/></option>--%>
<%--            <option value="surname ASC"><fmt:message key="sort_type.sender_A_Z"/></option>--%>
<%--            <option value="surname DESC"><fmt:message key="sort_type.sender_Z_A"/></option>--%>
<%--            <c:choose>--%>
<%--                <c:when test="${locale=='en'}">--%>
<%--                    <option value="shipping_city_en ASC"><fmt:message key="sort_type.shipping_address_A_Z"/></option>--%>
<%--                    <option value="shipping_city_en DESC"><fmt:message key="sort_type.shipping_address_Z_A"/></option>--%>
<%--                    <option value="delivery_city_en ASC"><fmt:message key="sort_type.delivery_address_A_Z"/></option>--%>
<%--                    <option value="delivery_city_en DESC"><fmt:message key="sort_type.delivery_address_Z_A"/></option>--%>
<%--                </c:when>--%>
<%--                <c:when test="${locale=='ua'}">--%>
<%--                    <option value="shipping_city_ua ASC"><fmt:message key="sort_type.shipping_address_A_Z"/></option>--%>
<%--                    <option value="shipping_city_ua DESC"><fmt:message key="sort_type.shipping_address_Z_A"/></option>--%>
<%--                    <option value="delivery_city_ua ASC"><fmt:message key="sort_type.delivery_address_A_Z"/></option>--%>
<%--                    <option value="delivery_city_ua DESC"><fmt:message key="sort_type.delivery_address_Z_A"/></option>--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <option value="shipping_city_ua ASC"><fmt:message key="sort_type.shipping_address_A_Z"/></option>--%>
<%--                    <option value="shipping_city_ua DESC"><fmt:message key="sort_type.shipping_address_Z_A"/></option>--%>
<%--                    <option value="delivery_city_ua ASC"><fmt:message key="sort_type.delivery_address_A_Z"/></option>--%>
<%--                    <option value="delivery_city_ua DESC"><fmt:message key="sort_type.delivery_address_Z_A"/></option>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>
<%--            <option value="status_id ASC"><fmt:message key="sort_type.status_show_new"/></option>--%>
<%--        </select>--%>
<%--        <input type="hidden" name="command" value="adminOrders">--%>
<%--        <input type="hidden" name="page_number" value="${currentPage}">--%>
<%--    </form>--%>
<%--    <!--main content-->--%>
<%--    <div class="container">--%>
<%--        <c:if test="${allOrders.size()>0}">--%>
<%--            <div class="row">--%>
<%--                <c:forEach var="order" items="${allOrders}">--%>
<%--                    <div class="col-sm-6">--%>
<%--                        <div class="card" style="margin: 3px">--%>
<%--                            <div class="card-header">--%>
<%--                                <fmt:message key="inner_text.order_number" var="order_number"/>--%>
<%--                                <h5 class="card-title"><c:out value="${order_number}: ${order.id}"/></h5>--%>
<%--                                <fmt:message key="inner_text.shipping_status" var="status"/>--%>
<%--                                <c:out value="${status}: "/>--%>
<%--                                <c:choose>--%>
<%--                                    <c:when test="${locale=='en'}">--%>
<%--                                        <c:out value="${order.status.en}"/>--%>
<%--                                    </c:when>--%>
<%--                                    <c:when test="${locale=='ua'}">--%>
<%--                                        <c:out value="${order.status.ua}"/>--%>
<%--                                    </c:when>--%>
<%--                                    <c:otherwise>--%>
<%--                                        <c:out value="${order.status.ua}"/>--%>
<%--                                    </c:otherwise>--%>
<%--                                </c:choose>--%>
<%--                            </div>--%>
<%--                            <div class="card-body">--%>
<%--                                <ul class="list-group list-group-flush">--%>
<%--                                    <li class="list-group-item">--%>
<%--                                        <fmt:message key="inner_text.sender" var="sender"/>--%>
<%--                                        <c:out value="${sender}: ${order.consignee}"/>--%>
<%--                                    </li>--%>
<%--                                    <li class="list-group-item"><fmt:message key="inner_text.from" var="from"/>--%>
<%--                                        <c:out value="${from}: "/>--%>
<%--                                        <c:choose>--%>
<%--                                            <c:when test="${locale=='en'}">--%>
<%--                                                <c:out value="${order.shippingAddress.description.en}"/>--%>
<%--                                            </c:when>--%>
<%--                                            <c:when test="${locale=='ua'}">--%>
<%--                                                <c:out value="${order.shippingAddress.description.ua}"/>--%>
<%--                                            </c:when>--%>
<%--                                            <c:otherwise>--%>
<%--                                                <c:out value="${order.shippingAddress.description.ua}"/>--%>
<%--                                            </c:otherwise>--%>
<%--                                        </c:choose>--%>
<%--                                    </li>--%>
<%--                                    <li class="list-group-item">--%>
<%--                                        <fmt:message key="inner_text.to" var="to"/>--%>
<%--                                        <c:out value="${to}: "/>--%>
<%--                                        <c:choose>--%>
<%--                                            <c:when test="${locale=='en'}">--%>
<%--                                                <c:out value="${order.deliveryAddress.description.en}"/>--%>
<%--                                            </c:when>--%>
<%--                                            <c:when test="${locale=='ua'}">--%>
<%--                                                <c:out value="${order.deliveryAddress.description.ua}"/>--%>
<%--                                            </c:when>--%>
<%--                                            <c:otherwise>--%>
<%--                                                <c:out value="${order.deliveryAddress.description.ua}"/>--%>
<%--                                            </c:otherwise>--%>
<%--                                        </c:choose>--%>
<%--                                    </li>--%>
<%--                                    <li class="list-group-item">--%>
<%--                                        <fmt:message key="inner_text.create_date" var="create_date"/>--%>
<%--                                        <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${order.creationTime}"--%>
<%--                                                        var="time"/>--%>
<%--                                        <c:out value="${create_date}: ${time}"/>--%>
<%--                                    </li>--%>
<%--                                    <li class="list-group-item">--%>
<%--                                        <fmt:message key="inner_text.consignee" var="consignee"/>--%>
<%--                                        <c:out value="${consignee}: ${order.consignee}"/>--%>
<%--                                    </li>--%>
<%--                                    <li class="list-group-item">--%>
<%--                                        <fmt:message key="inner_text.fare" var="fare"/>--%>
<%--                                        <c:out value="${fare}: ${order.fare}"/>--%>
<%--                                    </li>--%>
<%--                                </ul>--%>
<%--                                <c:choose>--%>
<%--                                    <c:when test="${order.status.ua eq 'створений' or order.status.en eq 'created'}">--%>
<%--                                        <form action="/controller" method="post">--%>
<%--                                            <input type="hidden" name="command" value="confirmOrder">--%>
<%--                                            <input type="hidden" name="order" value="${order.getId()}">--%>
<%--                                            <fmt:message key="button.confirm" var="button_confirm"/>--%>
<%--                                            <input type="submit" class="btn btn-success" name="procedure"--%>
<%--                                                   value="${button_confirm}">--%>
<%--                                            <fmt:message key="button.cancel" var="button_cancel"/>--%>
<%--                                            <input type="submit" name="procedure"--%>
<%--                                                   class="btn btn-danger" value="${button_cancel}">--%>
<%--                                        </form>--%>
<%--                                    </c:when>--%>
<%--                                    <c:otherwise>--%>
<%--                                        <form action="/controller" method="post">--%>
<%--                                            <input type="hidden" name="command" value="changeOrderStatus">--%>
<%--                                            <input type="hidden" name="order" value="${order.getId()}">--%>
<%--                                            <select name="status_id" class="custom-select mr-sm-2"--%>
<%--                                                    onchange="this.form.submit()">--%>
<%--                                                <option selected disabled><c:out value="change status"/></option>--%>

<%--                                                <c:forEach var="status" items="${statuses}">--%>
<%--                                                    <c:if test="${status.description.ua ne 'створений' and--%>
<%--                                        status.description.en ne 'created' and status.description.ua ne 'підтверджений'--%>
<%--                                        and status.description.en ne 'confirmed'}">--%>

<%--                                                        <option value="${status.statusID}">--%>
<%--                                                            <c:choose>--%>
<%--                                                                <c:when test="${locale=='en'}">--%>
<%--                                                                    <c:out value="${status.description.en}"/>--%>
<%--                                                                </c:when>--%>
<%--                                                                <c:when test="${locale=='ua'}">--%>
<%--                                                                    <c:out value="${status.description.ua}"/>--%>
<%--                                                                </c:when>--%>
<%--                                                                <c:otherwise>--%>
<%--                                                                    <c:out value="${status.description.ua}"/>--%>
<%--                                                                </c:otherwise>--%>
<%--                                                            </c:choose>--%>
<%--                                                        </option>--%>
<%--                                                    </c:if>--%>
<%--                                                </c:forEach>--%>
<%--                                            </select>--%>
<%--                                        </form>--%>
<%--                                    </c:otherwise>--%>
<%--                                </c:choose>--%>
<%--                                <form action="/controller" method="do">--%>
<%--                                    <input type="hidden" name="command" value="adminViewOrder">--%>
<%--                                    <input type="hidden" name="backPage" value="/controller?command=adminOrders">--%>
<%--                                    <fmt:message key="button.show" var="button_show"/>--%>
<%--                                    <button class="btn btn-primary" type="submit" name="orderID" value="${order.id}">--%>
<%--                                        <c:out value="${button_show}"/>--%>
<%--                                    </button>--%>
<%--                                </form>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </c:forEach>--%>
<%--            </div>--%>
<%--        </c:if>--%>
<%--    </div>--%>


<%--</div>--%>
<div class="container-fluid">

    <div class="row">
        <div class="col-sm-8">
            <form>
                <!-- first filter group: from address, to address, status -->
                <div class="row">
                    <div class="col-sm-4">
                        <label for="shipping_address">From</label>
                        <select class="form-control-sm" id="shipping_address">
                            <c:forEach var="shippAddr" items="${localities}">
                                <jsp:useBean id="shippAddr" type="com.epam.delivery.db.entities.bean.LocalityBean"/>
                                <option value="${shippAddr.localityID}">
                                    <c:choose>
                                        <c:when test="${locale=='en'}">
                                            <c:out value="${shippAddr.description.ua}"/>
                                        </c:when>
                                        <c:when test="${locale=='ua'}">
                                            <c:out value="${shippAddr.description.ua}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${shippAddr.description.ua}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-4">
                        <label for="delivery_address">To</label>
                        <select class="form-control-sm" id="delivery_address">
                            <option value="">All</option>
                            <c:forEach var="delivAddr" items="${localities}">
                                <jsp:useBean id="delivAddr" type="com.epam.delivery.db.entities.bean.LocalityBean"/>
                                <option value="${delivAddr.localityID}">
                                    <c:choose>
                                        <c:when test="${locale=='en'}">
                                            <c:out value="${delivAddr.description.en}"/>
                                        </c:when>
                                        <c:when test="${locale=='ua'}">
                                            <c:out value="${delivAddr.description.ua}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${delivAddr.description.ua}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-4">
                        <label for="status">Status:</label>
                        <select class="form-control-sm" id="status">
                            <c:forEach var="status" items="${statuses}">
                                <jsp:useBean id="status"
                                             type="com.epam.delivery.db.entities.bean.StatusDescriptionBean"/>
                                <option value="${status.statusID}">
                                    <c:choose>
                                        <c:when test="${locale=='en'}">
                                            <c:out value="${status.description.en}"/>
                                        </c:when>
                                        <c:when test="${locale=='ua'}">
                                            <c:out value="${status.description.ua}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${status.description.ua}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <!-- first filter group: from date, to date, from sum ,to sum -->
                <div class="row">
                    <!-- select from date -->
                    <div class="col-sm-3">
                        <div class="input-group input-group-sm mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">From</span>
                            </div>
                            <label for="fromDate"></label>
                            <input type="date" class="form-control" id="fromDate" name="fromDate"
                                   value="${fromDate}">
                            <div class="input-group-append">
                                <span class="input-group-text"><i class="far fa-calendar-alt"></i></span>
                            </div>
                        </div>
                    </div>
                    <!-- select to date -->
                    <div class="col-sm-3">
                        <div class="input-group input-group-sm mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">To</span>
                            </div>
                            <label for="toDate"></label>
                            <input type="date" class="form-control" id="toDate" name="toDate"
                                   value="${toDate}">
                            <div class="input-group-append">
                                <span class="input-group-text"><i class="far fa-calendar-alt"></i></span>
                            </div>
                        </div>
                    </div>
                    <!-- select from sum -->
                    <div class="col-sm-3">
                        <div class="input-group input-group-sm mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">From</span>
                            </div>
                            <label for="fromSum"></label>
                            <c:choose>
                                <c:when test="${not empty fromSum}">
                                    <input type="text" class="form-control" id="fromSum" name="fromSum"
                                           value="${fromSum}">
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="form-control" id="fromSum" name="fromSum" value="0">
                                </c:otherwise>
                            </c:choose>
                            <div class="input-group-append">
                                <span class="input-group-text">hrn</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="input-group input-group-sm mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">to</span>
                            </div>
                            <label for="toSum"></label>
                            <input type="text" class="form-control" id="toSum" name="toSum" value="${toSum}">
                            <div class="input-group-append">
                                <span class="input-group-text">hrn</span>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- form button group : ok, cancel -->
                <div class="row" style="margin: auto">
                        <!-- submit button -->
                        <button type="submit" class="btn btn-info btn-sm float-left">
                            <fmt:message key="admin_invoices.jsp.button.ok"/>
                        </button>
                        <!-- cancel button -->
                        <a class="btn btn-danger btn-sm float-right"
                           href="${pageContext.request.contextPath}/controller?command=adminOrders">
                            <fmt:message key="admin_invoices.jsp.button.reset_all"/>
                        </a>
                    </div>
            </form>
        </div>

        <div class="col-sm-4">
            <div class="row">
                <!-- sort content -->
                <div class="col-sm-6">
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <input type="hidden" name="command" value="adminOrders">
                        <input type="hidden" name="recordPerPage" value="${recordPerPage}">
                        <label for="sort" style="margin: auto;display: inline-block">
                            <fmt:message key="admin_orders.jsp.text.status"/>
                        </label>
                        <select name="sort" id="sort" class="form-control-sm" onchange="this.form.submit();">
                            <option selected disabled>
                                <c:choose>
                                    <c:when test="${sort eq 'o.id ASC'}">
                                        <fmt:message key="admin_orders.jsp.option.number_l"/>
                                    </c:when>
                                    <c:when test="${sort eq 'o.id DESC'}">
                                        <fmt:message key="admin_orders.jsp.option.number_h"/>
                                    </c:when>
                                    <c:when test="${sort eq 'o.creation_time ASC'}">
                                        <fmt:message key="admin_orders.jsp.option.date_l"/>
                                    </c:when>
                                    <c:when test="${sort eq 'o.creation_time DESC'}">
                                        <fmt:message key="admin_orders.jsp.option.date_h"/>
                                    </c:when>
                                    <c:when test="${sort eq 'o.fare ASC'}">
                                        <fmt:message key="admin_orders.jsp.option.sum_l"/>
                                    </c:when>
                                    <c:when test="${sort eq 'o.fare DESC'}">
                                        <fmt:message key="admin_orders.jsp.option.sum_h"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="admin_orders.jsp.option.number_l"/>
                                    </c:otherwise>
                                </c:choose>
                            </option>
                            <option value="o.id ASC">
                                <fmt:message key="admin_orders.jsp.option.number_l"/>
                            </option>
                            <option value="o.id DESC">
                                <fmt:message key="admin_orders.jsp.option.number_h"/>
                            </option>
                            <option value="o.creation_time ASC">
                                <fmt:message key="admin_orders.jsp.option.date_l"/>
                            </option>
                            <option value="o.creation_time DESC">
                                <fmt:message key="admin_orders.jsp.option.date_h"/>
                            </option>
                            <option value="o.fare ASC">
                                <fmt:message key="admin_orders.jsp.option.sum_l"/>
                            </option>
                            <option value="o.fare DESC">
                                <fmt:message key="admin_orders.jsp.option.sum_h"/>
                            </option>
                        </select>
                    </form>
                </div>

                <!-- change number orders in 1 page -->
                <div class="col-sm-6">
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <input type="hidden" name="command" value="adminOrders">
                        <input type="hidden" name="sort" value="${sort}">
                        <label for="recordPerPage" style="margin: auto;display: inline-block">
                            <fmt:message key="admin_orders.jsp.label.on_page"/>
                        </label>
                        <select id="recordPerPage" name="recordPerPage" class="form-control-sm"
                                onchange="this.form.submit();">
                            <c:choose>
                                <c:when test="${recordPerPage eq 10}">
                                    <option selected value="10"><c:out value="10"/></option>
                                    <option value="30"><c:out value="30"/></option>
                                    <option value="50"><c:out value="50"/></option>
                                </c:when>
                                <c:when test="${recordPerPage eq 30}">
                                    <option value="10"><c:out value="10"/></option>
                                    <option selected value="30"><c:out value="30"/></option>
                                    <option value="50"><c:out value="50"/></option>
                                </c:when>
                                <c:when test="${recordPerPage eq 50}">
                                    <option value="10"><c:out value="10"/></option>
                                    <option value="30"><c:out value="30"/></option>
                                    <option selected value="50"><c:out value="50"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option selected value="10"><c:out value="10"/></option>
                                    <option value="30"><c:out value="30"/></option>
                                    <option value="50"><c:out value="50"/></option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <table class="table table-hover" style="margin-top: 10px">
        <thead class="thead-light">
        <tr>
            <th scope="col"><c:out value="#"/></th>
            <th scope="col"><fmt:message key="admin_orders.jsp.table.sender"/></th>
            <th scope="col"><fmt:message key="admin_orders.jsp.table.from"/></th>
            <th scope="col"><fmt:message key="admin_orders.jsp.table.to"/></th>
            <th scope="col"><fmt:message key="admin_orders.jsp.table.create_date"/></th>
            <th scope="col"><fmt:message key="admin_orders.jsp.table.consignee"/></th>
            <th scope="col"><fmt:message key="admin_orders.jsp.table.fare"/></th>
            <th scope="col"><fmt:message key="admin_orders.jsp.table.status"/></th>
            <th scope="col"><fmt:message key="admin_orders.jsp.table.operation"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${allOrders}">
            <jsp:useBean id="order" type="com.epam.delivery.db.entities.bean.OrderBean"/>
            <tr>
                <!-- order number -->
                <th scope="row">
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <input type="hidden" name="command" value="adminViewOrder">
                        <input type="hidden" name="backPage" value="/controller?command=adminOrders">
                        <button type="submit" class="btn btn-link-sm" name="orderID" value="${order.id}">
                            <c:out value="${order.id}"/>
                        </button>
                    </form>
                </th>
                <!-- client -->
                <th scope="row"><c:out value="${order.client}"/></th>
                <!-- shipping address -->
                <th scope="row">
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
                </th>
                <!-- delivery address -->
                <th scope="row">
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
                </th>
                <!-- order create time -->
                <td><fmt:formatDate value="${order.creationTime}" type="both"/></td>
                <!-- consignee -->
                <th scope="row"><c:out value="${order.consignee}"/></th>
                <!-- order fare -->
                <th scope="row"><c:out value="${order.fare}"/></th>
                <!-- order status description -->
                <th scope="row">
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
                </th>
                <td>
                    <c:choose>
                        <c:when test="${order.statusId eq 1}">
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="confirmOrder">
                                <input type="hidden" name="order" value="${order.id}">
                                <button type="submit" class="btn btn-success" name="procedure" value="confirm">
                                    <fmt:message key="admin_orders.jsp.button.confirm"/>
                                </button>
                                <button type="submit" name="procedure"
                                        class="btn btn-danger" value="cancel">
                                    <fmt:message key="admin_orders.jsp.button.cancel"/>
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button><fmt:message key="admin_orders.jsp.button.change_status"/></button>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <c:if test="${noOfPages > 1}">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item disabled">
                                <a class="page-link"
                                   href="/controller?command=adminOrders&page_number=${i}&sort=${sort}">
                                    <c:out value="${i}"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link"
                                   href="/controller?command=adminOrders&page_number=${i}&sort=${sort}">
                                    <c:out value="${i}"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </nav>
    </c:if>
</div>
</body>
</html>
