<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 16.02.2022
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .edit_order input {
            width: 30%;
            margin-bottom: 2px;
            margin-left: 35%;
        }

        .edit_order {
        }
    </style>
</head>
<body>
<c:import url="WEB-INF/jsp/heder.jsp"/>
<%--<c:out value="${showOrder}"></c:out>--%>

<div class="edit_order">
    <input type="text" value="${showOrder.getShippingAddress().getName()}"><br>
    <input type="text" value="${showOrder.getDeliveryAddress().getName()}"><br>
    <input type="text" value="${showOrder.getCreationTime()}"><br>
    <input type="text" value="${showOrder.getConsignee()}"><br>
    <input type="text" value="${showOrder.getDescription()}"><br>
    <input type="text" value="${showOrder.getLength()}"><br>
    <input type="text" value="${showOrder.getHeight()}"><br>
    <input type="text" value="${showOrder.getWidth()}"><br>
    <input type="text" value="${showOrder.getWeight()}"><br>
    <input type="text" value="${showOrder.getFare()}"><br>
    <c:choose>
        <c:when test="${locale=='en'}">
            <input type="text" value="${showOrder.getStatus().getEnDescription()}"><br>
        </c:when>
        <c:when test="${locale=='ua'}">
            <input type="text" value="${showOrder.getStatus().getUaDescription()}"><br>
        </c:when>
        <c:otherwise>
            <input type="text" value="${showOrder.getStatus().getUaDescription()}"><br>
        </c:otherwise>
    </c:choose>
    <input type="text" value="${showOrder.getDeliveryDate()}"><br>
</div>

</body>
</html>
