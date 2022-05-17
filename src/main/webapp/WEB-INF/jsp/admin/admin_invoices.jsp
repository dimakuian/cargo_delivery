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
<jsp:useBean id="locale" scope="session" type="java.lang.String"/>
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
<fmt:message key="admin_invoices.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page"/>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<!-- Language switcher begin -->
<form name="locales" action="${pageContext.request.contextPath}/controller" method="post">
    <label for="lang"></label>
    <select id="lang" name="lang" onchange="this.form.submit()">
        <option selected disabled><fmt:message key="language.chooseLang"/></option>
        <option value="ua"><fmt:message key="language.ua"/></option>
        <option value="en"><fmt:message key="language.en"/></option>
    </select>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="/controller?command=adminInvoices">
</form>
<!-- end Language switcher -->


<%--<jsp:useBean id="recordPerPage" scope="application" type="java.lang.Integer"/>--%>
<!-- main content -->
<div class="container-fluid">
    <div class="row">

        <!-- left column -->
        <div class="col-sm-3" style="width: 90%">
            <form action="/controller" method="get">
                <input type="hidden" name="command" value="adminInvoices">
                <input type="hidden" name="recordPerPage" value="${recordPerPage}">
                <input type="hidden" name="sort" value="${sort}">
                <div class="list-group" id="list-tab" role="tablist">
                    <div class="list-group-item">
                        <div class="row">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">from</span>
                                </div>
                                <input type="date" class="form-control" name="fromDate">
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="far fa-calendar-alt"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">to</span>
                                </div>
                                <input type="date" class="form-control" name="toDate">
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="far fa-calendar-alt"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="col-sm-12">
                            <span>Status:</span>
                        </div>
                        <div class="form-check col-sm-12">
                            <c:choose>
                                <c:when test="${statusID eq 0}">
                                    <input class="form-check-input" type="radio" name="statusID" id="status1" value="0"
                                           checked>
                                </c:when>
                                <c:otherwise>
                                    <input class="form-check-input" type="radio" name="statusID" id="status1" value="0">
                                </c:otherwise>
                            </c:choose>
                            <label class="form-check-label" for="status1">
                                <fmt:message key="admin_invoices.jsp.invoice_status.new"/>
                            </label>
                        </div>
                        <div class="form-check col-sm-12">
                            <c:choose>
                                <c:when test="${statusID eq 1}">
                                    <input class="form-check-input" type="radio" name="statusID" id="status2" value="1"
                                           checked>
                                </c:when>
                                <c:otherwise>
                                    <input class="form-check-input" type="radio" name="statusID" id="status2" value="1">
                                </c:otherwise>
                            </c:choose>
                            <label class="form-check-label" for="status2">
                                <fmt:message key="admin_invoices.jsp.invoice_status.paid"/>
                            </label>
                        </div>
                        <div class="form-check col-sm-12">
                            <c:choose>
                                <c:when test="${statusID eq 2}">
                                    <input class="form-check-input" type="radio" name="statusID" id="status3" value="2"
                                           checked>
                                </c:when>
                                <c:otherwise>
                                    <input class="form-check-input" type="radio" name="statusID" id="status3" value="2">
                                </c:otherwise>
                            </c:choose>
                            <label class="form-check-label" for="status3">
                                <fmt:message key="admin_invoices.jsp.invoice_status.declined"/>
                            </label>
                        </div>
                        <div class="form-check col-sm-12">
                            <c:choose>
                                <c:when test="${empty statusID}">
                                    <input checked class="form-check-input" id="all" name="statusID" type="radio"
                                           value="">
                                </c:when>
                                <c:otherwise>
                                    <input class="form-check-input" id="all" name="statusID" type="radio" value="">
                                </c:otherwise>
                            </c:choose>
                            <label class="form-check-label" for="all">
                                <fmt:message key="admin_invoices.jsp.invoice_status.all"/>
                            </label>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">from</span>
                                </div>
                                <c:choose>
                                    <c:when test="${not empty fromSum}">
                                        <input type="text" class="form-control" name="fromSum" value="${fromSum}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="form-control" name="fromSum" value="0">
                                    </c:otherwise>
                                </c:choose>
                                <div class="input-group-append">
                                    <span class="input-group-text">hrn</span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">to</span>
                                </div>
                                <input type="text" class="form-control" name="toSum" value="${toSum}">
                                <div class="input-group-append">
                                    <span class="input-group-text">hrn</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="list-group-item">
                            <button type="submit" class="btn btn-info float-left">Ok</button>
                            <a class="btn btn-danger float-right"
                               href="${pageContext.request.contextPath}/controller?command=adminInvoices">Reset all
                            </a>
                    </div>
                </div>
            </form>
        </div>

        <!-- right column -->
        <div class="col-sm-9">
            <div class="row">

                <!-- sort content -->
                <div class="col-sm-6">
                    <div class="col-sm-6">
                        <div class="row">
                            <form action="${pageContext.request.contextPath}/controller" method="get">
                                <input type="hidden" name="command" value="adminInvoices">
                                <input type="hidden" name="recordPerPage" value="${recordPerPage}">
                                <label for="sort" style="margin: auto;display: inline-block">
                                    <fmt:message key="admin_invoices.jsp.label.sort"/>
                                </label>
                                <select name="sort" id="sort" class="form-control-sm" onchange="this.form.submit();">
                                    <option selected disabled>
                                        <c:choose>
                                            <c:when test="${sort eq 'id ASC'}">
                                                <fmt:message key="admin_invoices.jsp.option.number_l"/>
                                            </c:when>
                                            <c:when test="${sort eq 'id DESC'}">
                                                <fmt:message key="admin_invoices.jsp.option.number_h"/>
                                            </c:when>
                                            <c:when test="${sort eq 'creation_datetime ASC'}">
                                                <fmt:message key="admin_invoices.jsp.option.date_l"/>
                                            </c:when>
                                            <c:when test="${sort eq 'creation_datetime DESC'}">
                                                <fmt:message key="admin_invoices.jsp.option.date_h"/>
                                            </c:when>
                                            <c:when test="${sort eq 'sum ASC'}">
                                                <fmt:message key="admin_invoices.jsp.option.sum_l"/>
                                            </c:when>
                                            <c:when test="${sort eq 'sum DESC'}">
                                                <fmt:message key="admin_invoices.jsp.option.sum_h"/>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="admin_invoices.jsp.option.number_l"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </option>
                                    <option value="id ASC"><fmt:message
                                            key="admin_invoices.jsp.option.number_l"/>
                                    </option>
                                    <option value="id DESC"><fmt:message
                                            key="admin_invoices.jsp.option.number_h"/>
                                    </option>
                                    <option value="creation_datetime ASC"><fmt:message
                                            key="admin_invoices.jsp.option.date_l"/>
                                    </option>
                                    <option value="creation_datetime DESC"><fmt:message
                                            key="admin_invoices.jsp.option.date_h"/>
                                    </option>
                                    <option value="sum ASC"><fmt:message
                                            key="admin_invoices.jsp.option.sum_l"/>
                                    </option>
                                    <option value="sum DESC"><fmt:message
                                            key="admin_invoices.jsp.option.sum_h"/>
                                    </option>
                                </select>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- change number invoice in 1 page -->
                <div class="col-sm-6">
                    <div class="col-sm-6">
                        <div class="row">
                            <form action="${pageContext.request.contextPath}/controller" method="get">
                                <input type="hidden" name="command" value="adminInvoices">
                                <input type="hidden" name="sort" value="${sort}">
                                <label for="recordPerPage" style="margin: auto;display: inline-block">
                                    <fmt:message key="admin_invoices.jsp.label.on_page"/>
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

            <jsp:useBean id="allInvoices" scope="request" type="java.util.ArrayList"/>
            <table class="table table-hover" style="margin-top: 10px">
                <thead class="thead-light">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><fmt:message key="admin_invoices.jsp.table_date"/></th>
                    <th scope="col"><fmt:message key="admin_invoices.jsp.table_order_numb"/></th>
                    <th scope="col"><fmt:message key="admin_invoices.jsp.table_sum"/></th>
                    <th scope="col"><fmt:message key="admin_invoices.jsp.table_status"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="invoice" items="${allInvoices}">
                    <jsp:useBean id="invoice" type="com.epam.delivery.db.entities.Invoice"/>
                    <tr>
                        <th scope="row">${invoice.id}</th>
                        <td><fmt:formatDate value="${invoice.creationDatetime}" type="both"/></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller" method="get">
                                <input type="hidden" name="command" value="adminViewOrder">
                                <input type="hidden" name="backPage" value="/all_invoices">
                                <button type="submit" class="btn btn-link" name="orderID" value="${invoice.orderID}">
                                        ${invoice.orderID}
                                </button>
                            </form>
                        </td>
                        <td>${invoice.sum}</td>
                        <td>
                            <c:choose>
                                <c:when test="${invoice.invoiceStatusID==0}">
                                    <fmt:message key="admin_invoices.jsp.invoice_status.new"/>
                                </c:when>
                                <c:when test="${invoice.invoiceStatusID==1}">
                                    <fmt:message key="admin_invoices.jsp.invoice_status.paid"/>
                                </c:when>
                                <c:when test="${invoice.invoiceStatusID==2}">
                                    <fmt:message key="admin_invoices.jsp.invoice_status.declined"/>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <%--For displaying Page numbers.
                The when condition does not display a link for the current page--%>
            <%--            <jsp:useBean id="noOfPages" scope="application" type="java.lang.Integer"/>--%>
            <c:if test="${noOfPages >1}">
                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <%--                                <jsp:useBean id="currentPage" scope="application" type="java.lang.Integer"/>--%>
                                <c:when test="${currentPage eq i}">
                                    <li class="page-item disabled">
                                        <a class="page-link" href="${pageContext.request.contextPath}
                                        /controller?command=adminInvoices&page_number=${i}&recordPerPage=${recordPerPage}&sort=${sort}">
                                            <c:out value="${i}"/>
                                        </a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link" href="${pageContext.request.contextPath}
                                        /controller?command=adminInvoices&page_number=${i}&recordPerPage=${recordPerPage}&sort=${sort}">
                                            <c:out value="${i}"/>
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
