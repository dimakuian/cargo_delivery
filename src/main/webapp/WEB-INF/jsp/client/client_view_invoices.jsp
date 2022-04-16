<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 09.04.2022
  Time: 20:54
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
<%--<fmt:message key="inner_text_title_view_order" var="title"/>--%>
<c:set var="title" value="invoices" scope="page"/>
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
    <input type="hidden" name="page" value="/controller?command=clientViewInvoices">
</form>
<!-- end Language switcher -->
<c:if test="${not empty message}">
    <c:out value="${message}"/>
    <c:remove var="message"/>
</c:if>
<div class="container-fluid">
    <c:choose>
        <c:when test="${allInvoicesMap.size()==0}">
            <div class="text-center">
                <p class="mb-0" text-center><span class="text-muted"><fmt:message key="message.empty_invoices_list"/>
                </span></p>
            </div>
        </c:when>
        <c:when test="${newInvoicesMap.size()==0}">
            <div class="text-center">
                <p class="mb-0" text-center><span class="text-muted"><fmt:message
                        key="message.empty_new_invoices_list"/></span>
                </p>
            </div>
        </c:when>
        <c:when test="${newInvoicesMap.size()>0}">
            <div class="text-center">
                <p class="mb-0" text-center><span class="text-muted"><fmt:message key="inner_text.new_invoices"/>
                </span></p>
            </div>
            <div class="row">
                <c:forEach var="entry" items="${newInvoicesMap}">
                    <c:set var="invoice" value="${entry.key}"/>
                    <c:set var="order" value="${entry.value}"/>
                    <div class="col-sm-3" style="margin-bottom: 10px">
                        <div class="card" style="margin: 3px">
                            <div class="card-header">
                                <h6 style="margin-bottom: 0px;" class="text-centre">
                                    <strong>
                                        <c:out value="${invoice.id} from ${invoice.creationDatetime}"/>
                                    </strong>
                                </h6>
                                <p style="margin-bottom: 0px;"
                                   class="text-right text-info">
                                    <span><small><fmt:message key="invoice_status.new"/></small></span>
                                </p>
                            </div>
                            <div class="card-body" style="padding: 0px;">
                                <div class="col-md-12"
                                     style="background-color: green; width: 100%;">
                                    <p style="color: white; display: inline-block; margin-bottom: 0px;"><small>
                                        <c:choose>
                                            <c:when test="${locale=='en'}">
                                                <c:out value=" ${order.shippingAddress.description.en} >
                                                ${order.deliveryAddress.description.en}"/>
                                            </c:when>
                                            <c:when test="${locale=='ua'}">
                                                <c:out value=" ${order.shippingAddress.description.ua} >
                                                ${order.deliveryAddress.description.ua}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value=" ${order.shippingAddress.description.ua} >
                                                ${order.deliveryAddress.description.ua}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </small></p>
                                </div>
                                <div class="col-md-12" style="margin: 3px;">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p><span class="text-muted"><small>
                                                <fmt:message key="inner_text.consignee" var="consignee"/>
                                                <c:out value="${consignee}"/>
                                           </small></span>
                                        </div>
                                        <div class="col-md-8">
                                            <small><c:out value="${order.consignee}"/></small></p>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-4">
                                            <p><span class="text-muted"><small>
                                                <fmt:message key="inner_text.description" var="description"/>
                                                <c:out value="${description}"/>
                                            </small></span>
                                        </div>
                                        <div class="col-md-8">
                                            <p><small><c:out value="${order.description}"/></small></p>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-4">
                                            <p><span class="text-muted"><small>
                                                <!-- replace  to sum -->
                                                <fmt:message key="countCost.total_to_pay" var="sum"/>
                                                <c:out value="${sum}"/>
                                            </small></span>
                                        </div>
                                        <div class="col-md-8">
                                            <p><small><c:out value="${invoice.sum}"/></small></p>
                                        </div>
                                    </div>
                                    <c:if test="${invoice.invoiceStatusID ==0}">
                                        <form action="/controller" method="post">
                                            <input type="hidden" name="command" value="payInvoice">
                                            <input type="hidden" name="order" value="${invoice.orderID}">
                                            <input type="hidden" name="invoice" value="${invoice.id}">
                                            <button type="submit" value="pay" name="procedure"
                                                    class="btn btn-success btn-sm">
                                                <small><fmt:message key="button.pay"/></small></button>
                                            <button type="submit" value="decline" name="procedure"
                                                    class="btn btn-danger btn-sm"><small>
                                                <fmt:message key="button.decline"/></small></button>
                                        </form>
                                    </c:if>
                                    <a class="btn btn-primary btn-sm"
                                       href="<c:url value="/controller?command=clientViewOrder&orderID=${invoice.orderID}"/>"
                                       role="button"><small><fmt:message key="button.show_order_details"/></small></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
    </c:choose>
    <c:if test="${allInvoicesMap.size()>0}">
        <div class="text-center">
            <p class="mb-0" text-center><span class="text-muted">
                 <a data-toggle="collapse" href="#collapseExample" aria-expanded="false"
                    aria-controls="collapseExample">
            <fmt:message key="inner_text.all_invoices"/></a></span></p>
        </div>
        <div class="collapse" id="collapseExample">
            <div class="row">
                <c:forEach var="entry" items="${allInvoicesMap}">
                    <c:set var="invoice" value="${entry.key}"/>
                    <c:set var="order" value="${entry.value}"/>
                    <div class="col-sm-3" style="margin-bottom: 10px">
                        <div class="card" style="margin: 3px">
                            <div class="card-header">
                                <h6 style="margin-bottom: 0px;" class="text-centre">
                                    <strong>
                                        <c:out value="${invoice.id} from ${invoice.creationDatetime}"/>
                                    </strong>
                                </h6>
                                <c:choose>
                                    <c:when test="${invoice.invoiceStatusID==0}">
                                        <p style="margin-bottom: 0px;"
                                           class="text-right text-info">
                                            <span><small><fmt:message key="invoice_status.new"/></small></span>
                                        </p>
                                    </c:when>
                                    <c:when test="${invoice.invoiceStatusID==1}">
                                        <p style="margin-bottom: 0px;" class="text-right text-danger">
                                            <span><small><fmt:message key="invoice_status.declined"/></small></span>
                                        </p>
                                    </c:when>
                                    <c:when test="${invoice.invoiceStatusID==2}">
                                        <p style=" margin-bottom: 0px;"
                                           class="text-right text-success">
                                            <span><small><fmt:message key="invoice_status.paid"/></small></span>
                                        </p>
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="card-body" style="padding: 0px;">
                                <div class="col-md-12"
                                     style="background-color: green; width: 100%;">
                                    <p style="color: white; display: inline-block; margin-bottom: 0px;"><small>
                                        <c:choose>
                                            <c:when test="${locale=='en'}">
                                                <c:out value=" ${order.shippingAddress.description.en} >
                                                ${order.deliveryAddress.description.en}"/>
                                            </c:when>
                                            <c:when test="${locale=='ua'}">
                                                <c:out value=" ${order.shippingAddress.description.ua} >
                                                ${order.deliveryAddress.description.ua}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value=" ${order.shippingAddress.description.ua} >
                                                ${order.deliveryAddress.description.ua}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </small></p>
                                </div>
                                <div class="col-md-12" style="margin: 3px;">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p><span class="text-muted"><small>
                                                <fmt:message key="inner_text.consignee" var="consignee"/>
                                                <c:out value="${consignee}"/>
                                           </small></span>
                                        </div>
                                        <div class="col-md-8">
                                            <small><c:out value="${order.consignee}"/></small></p>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-4">
                                            <p><span class="text-muted"><small>
                                                <fmt:message key="inner_text.description" var="description"/>
                                                <c:out value="${description}"/>
                                            </small></span>
                                        </div>
                                        <div class="col-md-8">
                                            <p><small><c:out value="${order.description}"/></small></p>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-4">
                                            <p><span class="text-muted"><small>
                                                <!-- replace  to sum -->
                                                <fmt:message key="countCost.total_to_pay" var="sum"/>
                                                <c:out value="${sum}"/>
                                            </small></span>
                                        </div>
                                        <div class="col-md-8">
                                            <p><small><c:out value="${invoice.sum}"/></small></p>
                                        </div>
                                    </div>
                                    <c:if test="${invoice.invoiceStatusID ==0}">
                                        <form action="/controller" method="post">
                                            <input type="hidden" name="command" value="payInvoice">
                                            <input type="hidden" name="order" value="${invoice.orderID}">
                                            <input type="hidden" name="invoice" value="${invoice.id}">
                                            <button type="submit" value="pay" name="procedure"
                                                    class="btn btn-success btn-sm">
                                                <small><fmt:message key="button.pay"/></small></button>
                                            <button type="submit" value="decline" name="procedure"
                                                    class="btn btn-danger btn-sm"><small>
                                                <fmt:message key="button.decline"/></small></button>
                                        </form>
                                    </c:if>
                                    <a class="btn btn-primary btn-sm"
                                       href="<c:url value="/controller?command=clientViewOrder&orderID=${invoice.orderID}"/>"
                                       role="button"><small><fmt:message
                                            key="button.show_order_details"/></small></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>
    </c:if>
</div>

</body>
</html>