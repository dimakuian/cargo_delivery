<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 02.02.2022
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="WEB-INF/jspf/directive/taglib.jspf" %>
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
<c:set var="title" value="Home" scope="page" />
<%@include file="WEB-INF/jspf/head.jspf"%>
<body>
<%@include file="WEB-INF/jspf/header.jspf"%>
<!-- Language switcher begin -->
<form name="locales" action="/controller" method="post">
    <label>
        <select name="lang" onchange="this.form.submit()">
            <option selected disabled><fmt:message
                    key="register.chooseLang"/></option>
            <option value="ua"><fmt:message key="register.ua"/></option>
            <option value="en"><fmt:message key="register.en"/></option>
        </select>
    </label>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="index.jsp">
</form>
<!-- end Language switcher -->
<c:out value="${message}"/>
<c:remove var="message"/>
<h1>Some description about our company</h1>
<hr>
<c:set var="tariffs" value="${applicationScope['tariff']}"/>
<c:set var="localities" value="${applicationScope['localities']}"/>
<h1><fmt:message key="index.our_department"/></h1>
<div class="tariff_table">
    <table>
        <tr>
            <th><fmt:message key="index.department"/></th>
        </tr>
        <c:forEach items="${localities}" var="local">
            <tr>
                <td>${local.name}</td>
            </tr>
        </c:forEach>
    </table>
    <h1><fmt:message key="index.tariffs"/></h1>
    <table>
        <tr>
            <th>Weight, including up to ...kg.</th> <!--replace to fmt:-->
            <th>including up to 500km, hrn.</th> <!--replace to fmt:-->
            <th>including up to 700km, hrn.</th> <!--replace to fmt:-->
            <th>including up to 900km, hrn.</th> <!--replace to fmt:-->
            <th>including up to 1200km, hrn.</th> <!--replace to fmt:-->
            <th>including up to 1500km, hrn.</th> <!--replace to fmt:-->
        </tr>
        <c:forEach items="${tariffs}" var="tarif">
            <tr>
                <td>${tarif.weight}</td>
                <td>${tarif.priceUpTo500km}</td>
                <td>${tarif.priceUpTo700km}</td>
                <td>${tarif.priceUpTo900km}</td>
                <td>${tarif.priceUpTo1200km}</td>
                <td>${tarif.priceUpTo1500km}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
