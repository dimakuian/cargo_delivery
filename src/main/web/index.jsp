<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 02.02.2022
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource"/>
<html>
<head>
    <title>Title</title>
    <style>
        .tariff_table table {
            width: 70%;
            border: 1px solid #dddddd;
            border-collapse: collapse;
            margin: auto;
            font-family: Raleway, serif;
        }

        .tariff_table table th {
            color: white;
            font-weight: bold;
            padding: 5px;
            background: #555;
            border: 1px solid #dddddd;
        }

        .tariff_table table td {
            border: 1px solid #dddddd;
            padding: 5px;
        }

        .tariff_table h1 {
            margin-left: 15%;
            font-family: Raleway, serif;
        }

        .tariff_table tr:hover {
            background-color: #C3C3C2;
        }
    </style>
</head>
<body>

<c:import url="heder.jsp"/>
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
