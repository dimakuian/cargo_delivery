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
    <input type="hidden" name="page" value="/controller?command=adminCabinet&page_number=${currentPage}&sort=${currentSort}">
</form>
<!-- end Language switcher -->
<div>
    <!-- button for sort content -->
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
    <!--main content-->
    <div class="container">
        <c:if test="${allOrders.size()>0}">
            <div class="row">
                <c:forEach var="order" items="${allOrders}">
                    <div class="col-sm-6">
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
                                    <li class="list-group-item">
                                        <fmt:message key="inner_text.sender" var="sender"/>
                                        <c:out value="${sender}: ${order.consignee}"/>
                                    </li>
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
                                    <li class="list-group-item">
                                        <fmt:message key="inner_text.fare" var="fare"/>
                                        <c:out value="${fare}: ${order.fare}"/>
                                    </li>
                                </ul>
                                <c:choose>
                                    <c:when test="${order.status.ua eq 'створений' or order.status.en eq 'created'}">
                                        <form action="/controller" method="post">
                                            <input type="hidden" name="command" value="confirmOrder">
                                            <input type="hidden" name="order" value="${order.getId()}">
                                            <fmt:message key="button.confirm" var="button_confirm"/>
                                            <input type="submit" class="btn btn-success" name="procedure"
                                                   value="${button_confirm}">
                                            <fmt:message key="button.cancel" var="button_cancel"/>
                                            <input type="submit" name="procedure"
                                                   class="btn btn-danger" value="${button_cancel}">
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="/controller" method="post">
                                            <input type="hidden" name="command" value="changeOrderStatus">
                                            <input type="hidden" name="order" value="${order.getId()}">
                                            <select name="status_id" class="custom-select mr-sm-2"
                                                    onchange="this.form.submit()">
                                                <option selected disabled><c:out value="change status"/></option>

                                                <c:forEach var="status" items="${statuses}">
                                                    <c:if test="${status.description.ua ne 'створений' and
                                        status.description.en ne 'created' and status.description.ua ne 'підтверджений'
                                        and status.description.en ne 'confirmed'}">

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
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                                <form action="/controller" method="do">
                                    <input type="hidden" name="command" value="adminViewOrder">
                                    <input type="hidden" name="backPage" value="/controller?command=adminCabinet">
                                    <fmt:message key="button.show" var="button_show"/>
                                    <button class="btn btn-primary" type="submit" name="orderID" value="${order.id}">
                                        <c:out value="${button_show}"/>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
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
                               href="/controller?command=adminCabinet&page_number=${i}&sort=${currentSort}">
                                <c:out value="${i}"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link"
                               href="/controller?command=adminCabinet&page_number=${i}&sort=${currentSort}">
                                <c:out value="${i}"/></a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </nav>
</div>
</body>
</html>
