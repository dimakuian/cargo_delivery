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
<%@include file="/WEB-INF/jspf/header.jspf" %>
<div>
    <!-- Language switcher begin -->
    <form name="locales" action="/controller" method="post">
        <select name="lang" onchange="this.form.submit()">
            <option selected disabled><fmt:message key="language.chooseLang"/></option>
            <option value="ua"><fmt:message key="language.ua"/></option>
            <option value="en"><fmt:message key="language.en"/></option>
        </select>
        <input type="hidden" name="command" value="setLocale">
        <input type="hidden" name="page"
               value="/controller?command=clientCabinet&page_number=${currentPage}&sort=${currentSort}">
    </form>
    <!-- end Language switcher -->



    <!--main content-->
    <div class="container-fluid">
        <c:if test="${notPaidInvoices > 0}">
            <fmt:message key="inner_text.invoices_to_pay" var="invoices_to_pay"/>
            <button type="button" class="btn btn-primary"
                    onclick="window.location.href='/controller?command=clientViewInvoices'">
                <c:out value="${invoices_to_pay}"/><span class="badge badge-light">
        <c:out value="${notPaidInvoices}"/></span>
            </button>
        </c:if>


        <!--button for sort content -->
        <form action="/controller" method="do">
            <select name="sort" onchange="this.form.submit()">
                <option selected disabled><fmt:message key="inner_text.sort_by"/></option>
                <option value="id ASC"><fmt:message key="sort_type.number_lowest"/></option>
                <option value="id DESC"><fmt:message key="sort_type.number_highest"/></option>
                <c:choose>
                    <c:when test="${locale=='en'}">
                        <option value="shipping_city_en ASC"><fmt:message
                                key="sort_type.shipping_address_A_Z"/></option>
                        <option value="shipping_city_en DESC"><fmt:message
                                key="sort_type.shipping_address_Z_A"/></option>
                        <option value="delivery_city_en ASC"><fmt:message
                                key="sort_type.delivery_address_A_Z"/></option>
                        <option value="delivery_city_en DESC"><fmt:message
                                key="sort_type.delivery_address_Z_A"/></option>
                    </c:when>
                    <c:when test="${locale=='ua'}">
                        <option value="shipping_city_ua ASC"><fmt:message
                                key="sort_type.shipping_address_A_Z"/></option>
                        <option value="shipping_city_ua DESC"><fmt:message
                                key="sort_type.shipping_address_Z_A"/></option>
                        <option value="delivery_city_ua ASC"><fmt:message
                                key="sort_type.delivery_address_A_Z"/></option>
                        <option value="delivery_city_ua DESC"><fmt:message
                                key="sort_type.delivery_address_Z_A"/></option>
                    </c:when>
                    <c:otherwise>
                        <option value="shipping_city_ua ASC"><fmt:message
                                key="sort_type.shipping_address_A_Z"/></option>
                        <option value="shipping_city_ua DESC"><fmt:message
                                key="sort_type.shipping_address_Z_A"/></option>
                        <option value="delivery_city_ua ASC"><fmt:message
                                key="sort_type.delivery_address_A_Z"/></option>
                        <option value="delivery_city_ua DESC"><fmt:message
                                key="sort_type.delivery_address_Z_A"/></option>
                    </c:otherwise>
                </c:choose>
                <option value="status_id ASC"><fmt:message key="sort_type.status_show_new"/></option>
            </select>
            <input type="hidden" name="command" value="clientCabinet">
            <input type="hidden" name="page_number" value="${currentPage}">
        </form>
        <c:choose>
            <c:when test="${clientOrders.size()>0}">
                <div class="row">
                    <c:forEach var="order" items="${clientOrders}">
                        <div class="col-sm-4">
                            <div class="card" style="margin: 3px">
                                <div class="card-header">
                                    <fmt:message key="inner_text.order_number" var="order_number"/>
                                    <h5 class="card-title"><c:out value="${order_number}: ${order.id}"/></h5>
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
                                </div>
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item"><fmt:message key="inner_text.from" var="from"/>
                                            <c:out value="${from}: "/>
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
                                        </li>
                                        <li class="list-group-item">
                                            <fmt:message key="inner_text.to" var="to"/>
                                            <c:out value="${to}: "/>
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
                                        <li class="list-group-item">
                                            <fmt:message key="inner_text.fare" var="fare"/>
                                            <c:out value="${fare}: ${order.fare}"/>
                                        </li>

                                    </ul>

                                        <form action="/controller" method="do">
                                            <input type="hidden" name="command" value="clientViewOrder">
                                            <input type="hidden" name="orderID" value="${order.id}">
                                            <fmt:message key="button.show" var="button_show"/>
                                            <button class="btn btn-primary" type="submit"><c:out
                                                    value="${button_show}"/></button>
                                        </form>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="text-center">
                    <p class="mb-0"><span class="text-muted">
                  <fmt:message key="message.empty_orders_list"/>
                      </span></p>
                </div>
            </c:otherwise>
        </c:choose>

    </div>

    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item disabled">
                            <a class="page-link"
                               href="/controller?command=clientCabinet&page_number=${i}&sort=${currentSort}">
                                <c:out value="${i}"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link"
                               href="/controller?command=clientCabinet&page_number=${i}&sort=${currentSort}">
                                <c:out value="${i}"/></a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </nav>
</div>
<c:out value="${message}"/>
<c:remove var="message"/>
</body>
</html>
