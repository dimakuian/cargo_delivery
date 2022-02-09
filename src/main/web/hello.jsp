<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 07.02.2022
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello</title>
    <style>
        table {
            width: 70%;
            border: 1px solid #dddddd;
            border-collapse: collapse;
            margin: auto;
            font-family: Raleway, serif;
        }

        table th {
            color: white;
            font-weight: bold;
            padding: 5px;
            background: #555;
            border: 1px solid #dddddd;
        }

        table td {
            border: 1px solid #dddddd;
            padding: 5px;
        }

        h1 {
            margin-left: 15%;
            font-family: Raleway, serif;
        }

        tr:hover {
            background-color: #C3C3C2;
        }
    </style>
</head>
<body>
<c:set var="tariffs" value="${applicationScope['tariff']}"/>
<c:set var="localities" value="${applicationScope['localities']}"/>
<h1>Our department</h1>
<div>
    <table>
        <tr>
            <th>Department</th>
        </tr>
        <c:forEach items="${localities}" var="local">
            <tr>
                <td>${local.name}</td>
            </tr>
        </c:forEach>
    </table>
    <h1>Tariffs</h1>
    <table>
        <tr>
            <th>Weight, including up to ...kg.</th>
            <th>including up to 500km, hrn.</th>
            <th>including up to 700km, hrn.</th>
            <th>including up to 900km, hrn.</th>
            <th>including up to 1200km, hrn.</th>
            <th>including up to 1500km, hrn.</th>
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
