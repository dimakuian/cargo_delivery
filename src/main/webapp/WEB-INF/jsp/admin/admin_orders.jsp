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

<%--</div>--%>
<div class="container-fluid">
    <c:if test="${not empty message}">
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <strong>${message}</strong>
            <!-- close message -->
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <c:remove var="message"/>
        </div>
    </c:if>
    <div class="row">
        <div class="col-sm-8">
            <form action="/controller" method="get">
                <input type="hidden" name="command" value="adminOrders">
                <input type="hidden" name="recordPerPage" value="${recordPerPage}">
                <input type="hidden" name="sort" value="${sort}">
                <!-- first filter group: from address, to address, status -->
                <div class="row">
                    <div class="col-sm-4">
                        <label for="shipping_address"><fmt:message key="client_orders.jsp.text.from"/></label>
                        <select class="form-control-sm" id="shipping_address" name="shipping_address">
                            <option value=""><fmt:message key="admin_orders.jsp.text.choose_address"/></option>
                            <c:forEach var="shippAddr" items="${localities}">
                                <jsp:useBean id="shippAddr" type="com.epam.delivery.db.entities.bean.LocalityBean"/>
                                <c:choose>
                                    <c:when test="${shippAddr.localityID eq shipping_address}">
                                        <option value="${shippAddr.localityID}" selected>
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
                                    </c:when>
                                    <c:otherwise>
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
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-4">
                        <label for="delivery_address"><fmt:message key="client_orders.jsp.text.to"/></label>
                        <select class="form-control-sm" id="delivery_address" name="delivery_address">
                            <option value=""><fmt:message key="admin_orders.jsp.text.choose_address"/></option>
                            <c:forEach var="delivAddr" items="${localities}">
                                <jsp:useBean id="delivAddr" type="com.epam.delivery.db.entities.bean.LocalityBean"/>
                                <c:choose>
                                    <c:when test="${delivAddr.localityID eq delivery_address}">
                                        <option value="${delivAddr.localityID}" selected>
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
                                    </c:when>
                                    <c:otherwise>
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
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-4">
                        <label for="status"><fmt:message key="admin_orders.jsp.text.status"/></label>
                        <select class="form-control-sm" id="status" name="statusID">
                            <option value=""><fmt:message key="admin_orders.jsp.text.all"/></option>
                            <c:forEach var="status" items="${statuses}">
                                <jsp:useBean id="status"
                                             type="com.epam.delivery.db.entities.bean.StatusDescriptionBean"/>
                                <c:choose>
                                    <c:when test="${status.statusID eq statusID}">
                                        <option value="${status.statusID}" selected>
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
                                    </c:when>
                                    <c:otherwise>
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
                                    </c:otherwise>
                                </c:choose>
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
                                <span class="input-group-text"><fmt:message key="admin_orders.jsp.text.from"/></span>
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
                                <span class="input-group-text"><fmt:message key="admin_orders.jsp.text.to"/></span>
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
                                <span class="input-group-text"><fmt:message key="admin_orders.jsp.text.from"/></span>
                            </div>
                            <label for="fromSum"></label>
                            <c:choose>
                                <c:when test="${not empty fromSum}">
                                    <input type="text" class="form-control" id="fromSum" name="fromSum"
                                           value="${fromSum}" pattern="^\d*(\.\d{0,2})?$">
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="form-control" id="fromSum" name="fromSum" value="0"
                                           pattern="^\d*(\.\d{0,2})?$">
                                </c:otherwise>
                            </c:choose>
                            <div class="input-group-append">
                                <span class="input-group-text"><fmt:message key="admin_orders.jsp.text.hrn"/></span>
                            </div>
                        </div>
                    </div>
                    <!-- select to sum -->
                    <div class="col-sm-3">
                        <div class="input-group input-group-sm mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><fmt:message key="admin_orders.jsp.text.to"/></span>
                            </div>
                            <label for="toSum"></label>
                            <input type="text" class="form-control" id="toSum" name="toSum" value="${toSum}"
                                   pattern="^\d*(\.\d{0,2})?$">
                            <div class="input-group-append">
                                <span class="input-group-text"><fmt:message key="admin_orders.jsp.text.hrn"/></span>
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
                <div class="col-sm-7">
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <input type="hidden" name="command" value="adminOrders">
                        <c:if test="${not empty shipping_address}">
                            <input type="hidden" name="shipping_address" value="${shipping_address}">
                        </c:if>
                        <c:if test="${not empty delivery_address}">
                            <input type="hidden" name="delivery_address" value="${delivery_address}">
                        </c:if>
                        <c:if test="${not empty statusID}">
                            <input type="hidden" name="statusID" value="${statusID}">
                        </c:if>
                        <c:if test="${not empty fromDate}">
                            <input type="hidden" name="fromDate" value="${fromDate}">
                        </c:if>
                        <c:if test="${not empty toDate}">
                            <input type="hidden" name="toDate" value="${toDate}">
                        </c:if>
                        <c:if test="${not empty fromSum}">
                            <input type="hidden" name="fromSum" value="${fromSum}">
                        </c:if>
                        <c:if test="${not empty toSum}">
                            <input type="hidden" name="toSum" value="${toSum}">
                        </c:if>
                        <input type="hidden" name="recordPerPage" value="${recordPerPage}">
                        <label for="sort" style="margin: auto; display: inline-block">
                            <fmt:message key="admin_orders.jsp.text.sort"/>
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
                <div class="col-sm-5">
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <c:if test="${not empty shipping_address}">
                            <input type="hidden" name="shipping_address" value="${shipping_address}">
                        </c:if>
                        <c:if test="${not empty delivery_address}">
                            <input type="hidden" name="delivery_address" value="${delivery_address}">
                        </c:if>
                        <c:if test="${not empty statusID}">
                            <input type="hidden" name="statusID" value="${statusID}">
                        </c:if>
                        <c:if test="${not empty fromDate}">
                            <input type="hidden" name="fromDate" value="${fromDate}">
                        </c:if>
                        <c:if test="${not empty toDate}">
                            <input type="hidden" name="toDate" value="${toDate}">
                        </c:if>
                        <c:if test="${not empty fromSum}">
                            <input type="hidden" name="fromSum" value="${fromSum}">
                        </c:if>
                        <c:if test="${not empty toSum}">
                            <input type="hidden" name="toSum" value="${toSum}">
                        </c:if>
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
                <td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${order.creationTime}"/></td>
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
                                <button type="submit" name="procedure" class="btn btn-danger" value="cancel">
                                    <fmt:message key="admin_orders.jsp.button.cancel"/>
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="changeOrderStatus">
                                <input type="hidden" name="order" value="${order.id}">
                                <select name="status_id" class="custom-select mr-sm-2" onchange="this.form.submit()">
                                    <option selected disabled>
                                        <fmt:message key="admin_orders.jsp.button.change_status"/>
                                    </option>
                                    <c:forEach var="innerStatus" items="${statuses}">
                                        <jsp:useBean id="innerStatus"
                                                     type="com.epam.delivery.db.entities.bean.StatusDescriptionBean"/>
                                        <c:if test="${innerStatus.statusID ne 1 and innerStatus.statusID ne 3}">
                                            <option value="${innerStatus.statusID}">
                                                <c:choose>
                                                    <c:when test="${locale=='en'}">
                                                        <c:out value="${innerStatus.description.en}"/>
                                                    </c:when>
                                                    <c:when test="${locale=='ua'}">
                                                        <c:out value="${innerStatus.description.ua}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${innerStatus.description.ua}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </form>
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
<script>
</script>
</body>
</html>
