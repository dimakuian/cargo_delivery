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
<%--<jsp:useBean id="locale" scope="session" type="java.lang.String"/>--%>
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
<fmt:message key="client_orders.jsp.title" var="user_cabinet_title"/>
<c:set var="title" value="${user_cabinet_title}" scope="page"/>
<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<jsp:useBean id="currentFilter" scope="request" type="java.lang.String"/>
<jsp:useBean id="currentPage" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="currentSort" scope="request" type="java.lang.String"/>
<div>
    <!-- Language switcher begin -->
    <form name="locales" action="${pageContext.request.contextPath}/controller" method="post">
        <label for="local"></label>
        <select id="local" name="lang" onchange="this.form.submit()">
            <option selected disabled><fmt:message key="language.chooseLang"/></option>
            <option value="ua"><fmt:message key="language.ua"/></option>
            <option value="en"><fmt:message key="language.en"/></option>
        </select>
        <input type="hidden" name="command" value="setLocale">
        <c:choose>
            <c:when test="${not empty currentFilter}">
                <input type="hidden" name="page"
                       value="/controller?command=clientOrders&page_number=${currentPage}&sort=${currentSort}
                       &filter=${currentFilter}">
            </c:when>
            <c:otherwise>
                <input type="hidden" name="page"
                       value="/controller?command=clientOrders&page_number=${currentPage}&sort=${currentSort}">
            </c:otherwise>
        </c:choose>
    </form>
    <!-- end Language switcher -->
</div>

<!--main content-->
<div class="container-fluid">
    <c:if test="${not empty message}">
        <jsp:useBean id="message" scope="application" type="java.lang.String"/>
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <strong>${message}</strong>
            <!-- close message -->
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <c:remove var="message"/>
        </div>
    </c:if>

    <!-- main content-->
    <c:choose>
        <c:when test="${clientOrders.size()>0}">
            <div class="row">
                <!-- filter content button-->
                <div class="col-sm-2">
                    <jsp:useBean id="statuses" scope="application" type="java.util.List"/>
                    <jsp:useBean id="localities" scope="application" type="java.util.List"/>
                    <form action="<c:url value="/controller"/>" method="get">
                        <select id="filter" name="filter" class="selectpicker form-control" title="Choose one of the following..."
                                onchange="this.form.submit()">
                            <option selected disabled><fmt:message key="inner_text.filter_by"/></option>
                            <fmt:message key="client_orders.jsp.label.status" var="status"/>
                            <optgroup label="${status}">
                                <c:forEach var="status" items="${statuses}">
                                    <option value="${status.description.en}">
                                        <c:choose>
                                            <c:when test="${locale eq 'ua'}">
                                                <c:out value="${status.description.ua}"/>
                                            </c:when>
                                            <c:when test="${locale eq 'en'}">
                                                <c:out value="${status.description.en}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${status.description.ua}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </option>
                                </c:forEach>
                            </optgroup>
                            <fmt:message key="client_orders.jsp.text.shipping_address" var="shipping_address"/>
                            <optgroup label="${shipping_address}">
                                <c:forEach items="${localities}" var="department">
                                    <option value="shipping_department${department.localityID}">
                                        <c:choose>
                                            <c:when test="${locale eq 'ua'}">
                                                <c:out value="${department.description.ua}"/>
                                            </c:when>
                                            <c:when test="${locale eq 'en'}">
                                                <c:out value="${department.description.en}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${department.description.ua}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </option>
                                </c:forEach>
                            </optgroup>
                            <fmt:message key="client_orders.jsp.text.delivery_address" var="delivery_address"/>
                            <optgroup label="${delivery_address}">
                                <c:forEach items="${localities}" var="department">
                                    <option value="delivery_department${department.localityID}">
                                        <c:choose>
                                            <c:when test="${locale eq 'ua'}">
                                                <c:out value="${department.description.ua}"/>
                                            </c:when>
                                            <c:when test="${locale eq 'en'}">
                                                <c:out value="${department.description.en}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${department.description.ua}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </option>
                                </c:forEach>
                            </optgroup>
                        </select>
                        <input type="hidden" name="command" value="clientOrders">
                        <input type="hidden" name="page_number" value="${currentPage}">
                        <input type="hidden" name="sort" value="${currentSort}">
                    </form>
                </div>
                
                <!--button for sort content -->
                <div class="col-sm-2">
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <select class="selectpicker form-control" id="sort" name="sort" onchange="this.form.submit()">
                            <option selected disabled><fmt:message key="client_orders.jsp.text.sort_by"/></option>
                            <option value="id ASC"><fmt:message key="client_orders.jsp.sort.number_lowest"/></option>
                            <option value="id DESC"><fmt:message key="client_orders.jsp.sort.number_highest"/></option>
                            <c:choose>
                                <c:when test="${locale=='en'}">
                                    <option value="shipping_address_EN ASC"><fmt:message
                                            key="client_orders.jsp.sort.shipping_address_A_Z"/></option>
                                    <option value="shipping_address_EN DESC"><fmt:message
                                            key="client_orders.jsp.sort.shipping_address_Z_A"/></option>
                                    <option value="delivery_address_EN ASC"><fmt:message
                                            key="client_orders.jsp.sort.delivery_address_A_Z"/></option>
                                    <option value="delivery_address_EN DESC"><fmt:message
                                            key="client_orders.jsp.sort.delivery_address_Z_A"/></option>
                                </c:when>
                                <c:when test="${locale=='ua'}">
                                    <option value="shipping_address_UA ASC"><fmt:message
                                            key="client_orders.jsp.sort.shipping_address_A_Z"/></option>
                                    <option value="shipping_address_UA DESC"><fmt:message
                                            key="client_orders.jsp.sort.shipping_address_Z_A"/></option>
                                    <option value="delivery_address_UA ASC"><fmt:message
                                            key="client_orders.jsp.sort.delivery_address_A_Z"/></option>
                                    <option value="delivery_address_UA DESC"><fmt:message
                                            key="client_orders.jsp.sort.delivery_address_Z_A"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option value="shipping_address_UA ASC"><fmt:message
                                            key="client_orders.jsp.sort.shipping_address_A_Z"/></option>
                                    <option value="shipping_address_UA DESC"><fmt:message
                                            key="client_orders.jsp.sort.shipping_address_Z_A"/></option>
                                    <option value="delivery_address_UA ASC"><fmt:message
                                            key="client_orders.jsp.sort.delivery_address_A_Z"/></option>
                                    <option value="delivery_address_UA DESC"><fmt:message
                                            key="client_orders.jsp.sort.delivery_address_Z_A"/></option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                        <input type="hidden" name="command" value="clientOrders">
                        <input type="hidden" name="page_number" value="${currentPage}">
                        <c:if test="${not empty currentFilter}">
                            <input type="hidden" name="filter" value="${currentFilter}">
                        </c:if>
                    </form>
                </div>
                <jsp:useBean id="notPaidInvoices" scope="request" type="java.lang.Integer"/>
                <c:if test="${notPaidInvoices > 0}">
                    <div class="col-sm-8">
                        <fmt:message key="client_orders.jsp.text.invoices_to_pay" var="invoices_to_pay"/>
                        <button type="button" class="btn btn-primary float-right"
                                onclick="window.location.href='/controller?command=clientViewInvoices'">
                            <c:out value="${invoices_to_pay}"/><span class="badge badge-light">
                            <c:out value="${notPaidInvoices}"/></span>
                        </button>
                    </div>
                </c:if>
            </div>
            <div class="row">
                <c:forEach var="order" items="${clientOrders}">
                    <div class="col-sm-4">
                        <div class="card" style="margin: 3px">
                            <div class="card-header">
                                <fmt:message key="client_orders.jsp.text.order_number" var="order_number"/>
                                <h5 class="card-title"><c:out value="${order_number}: ${order.id}"/></h5>
                                <fmt:message key="client_orders.jsp.text.shipping_status" var="status"/>
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
                            </div>
                            <div class="card-body">
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item"><fmt:message key="client_orders.jsp.text.from" var="from"/>
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
                                        <fmt:message key="client_orders.jsp.text.to" var="to"/>
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
                                        <fmt:message key="client_orders.jsp.text.create_date" var="create_date"/>
                                        <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${order.creationTime}"
                                                        var="time"/>
                                        <c:out value="${create_date}: ${time}"/>
                                    </li>
                                    <li class="list-group-item">
                                        <fmt:message key="client_orders.jsp.text.consignee" var="consignee"/>
                                        <c:out value="${consignee}: ${order.consignee}"/>
                                    </li>
                                    <li class="list-group-item">
                                        <fmt:message key="client_orders.jsp.text.fare" var="fare"/>
                                        <c:out value="${fare}: ${order.fare}"/>
                                    </li>
                                </ul>
                                <form action="<c:url value="/controller"/>" method="get">
                                    <input type="hidden" name="command" value="clientViewOrder">
                                    <input type="hidden" name="orderID" value="${order.id}">
                                    <fmt:message key="client_orders.jsp.button.show" var="button_show"/>
                                    <button class="btn btn-primary" type="submit"><c:out value="${button_show}"/>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="text-center">
                <p class="mb-0">
                    <span class="text-muted"><fmt:message key="client_orders.jsp.message.empty_orders_list"/></span>
                </p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%--For displaying Page numbers.
The when condition does not display a link for the current page--%>
<jsp:useBean id="noOfPages" scope="request" type="java.lang.Integer"/>
<c:if test="${noOfPages > 1}">
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item disabled">
                            <a class="page-link"
                               href="<c:url value="/controller?command=clientOrders&page_number=${i}&sort=${currentSort}"/>">
                                <c:out value="${i}"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${not empty currentFilter}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=clientOrders&page_number=${i}&sort=${currentSort}&filter=${currentFilter}">
                                        <c:out value="${i}"/>
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=clientOrders&page_number=${i}&sort=${currentSort}">
                                        <c:out value="${i}"/>
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </nav>
</c:if>
</body>
</html>
