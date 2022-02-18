<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 15.02.2022
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
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
    </style>
</head>
<body>
<c:import url="heder.jsp"/>
<div id="edit" style="display: none">
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="editUser">
        <input type="text" name="name" value="${client.name}"><br>
        <input type="text" name="surname" value="${client.surname}"><br>
        <input type="text" name="patronymic" value="${client.patronymic}"><br>
        <input type="text" name="email" value="${client.email}"><br>
        <input type="text" name="phone" value="${client.phone}"><br>
        <input type="submit" value="Save Profile">
    </form>
</div>
<button id="button" type="button" onclick="showEditForm()">Edit user</button>
<div>
    <p>Your balance: <c:out value="${client.getBalance()}"/></p>
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="recharge">
        <input type="number" name="balance" value="0" autocomplete="false">
        <input type="submit" value="Recharge">
    </form>
</div>

<div>
    <c:if test="${clientOrders.size()>0}">
        <table>
            <tr>
                <th>From</th>
                <th>To</th>
                <th>Create date</th>
                <th>Consignee</th>
                <th>Fare</th>
                <th>Shipping status</th>
                <th>Payment status</th>
                <th>Details</th>
            </tr>
            <c:forEach var="order" items="${clientOrders}">
                <tr>
                    <td>${order.getShippingAddress().getName()}</td>
                    <td>${order.getDeliveryAddress().getName()}</td>
                    <td>${order.getCreationTime()}</td>
                    <td>${order.getConsignee()}</td>
                    <td>${order.getFare()}</td>
                    <td>${order.getShippingStatus().getNameEN()}</td>
                    <td>${order.getPaymentStatus().getNameEN()}
                        <c:if test="${order.getPaymentStatus().getNameEN()eq 'not paid'}">
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="payOrder">
                                <input type="hidden" name="order" value="${order.getId()}">
                                <input type="submit" value="pay">
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="editOrder">
                            <input type="hidden" name="order" value="${order.getId()}">
                            <input type="submit" value="show">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<script>
    function showEditForm() {
        var editDiv = document.getElementById('edit');
        var but = document.getElementById('button');
        if (editDiv.style.display == 'none' && but.innerHTML == "Edit user") {
            editDiv.style.display = "block";
            but.innerHTML = "Close";
        } else {
            editDiv.style.display = "none";
            but.innerHTML = "Edit user";
        }
    }
</script>
<c:out value="${message}"/>
<c:remove var="message"/>
</body>
</html>
