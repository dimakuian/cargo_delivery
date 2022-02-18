<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource"/>
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
    <fmt:message key="userCabinet.title" var="user_cabinet_title"/>
    <title>${user_cabinet_title}</title>
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
    <p><fmt:message key="userCabinet.user_balance"/><c:out value=" ${client.getBalance()}"/></p>
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="recharge">
        <input type="number" name="balance" value="0" autocomplete="false">
        <fmt:message key="userCabinet.recharge_balance" var="recharge_balance"/>
        <input type="submit" value="${recharge_balance}">
    </form>
</div>

<div>
    <c:if test="${clientOrders.size()>0}">
        <table>
            <tr>
                <th><fmt:message key="userCabinet.from"/></th>
                <th><fmt:message key="userCabinet.to"/></th>
                <th><fmt:message key="userCabinet.create_date"/></th>
                <th><fmt:message key="userCabinet.consignee"/></th>
                <th><fmt:message key="userCabinet.fare"/></th>
                <th><fmt:message key="userCabinet.shipping_status"/></th>
                <th><fmt:message key="userCabinet.details"/></th>
            </tr>
            <c:forEach var="order" items="${clientOrders}">
                <tr>
                    <td>${order.getShippingAddress().getName()}</td>
                    <td>${order.getDeliveryAddress().getName()}</td>
                    <td>${order.getCreationTime()}</td>
                    <td>${order.getConsignee()}</td>
                    <td>${order.getFare()}</td>
                    <c:choose>
                        <c:when test="${language=='en'}">
                            <td>${order.getShippingStatus().getName()}
                                <c:if test="${order.getShippingStatus().getName() eq 'not paid'}">
                                    <form action="/controller" method="post">
                                        <input type="hidden" name="command" value="payOrder">
                                        <input type="hidden" name="order" value="${order.getId()}">
                                        <fmt:message key="userCabinet.button.pay" var="button_pay"/>
                                        <input type="submit" value="${button_pay}">
                                    </form>
                                </c:if>
                            </td>
                        </c:when>
                        <c:when test="${language=='ua'}">
                            <td>${order.getShippingStatus().getName()}
                                <c:if test="${order.getShippingStatus().getName() eq 'не оплачено'}">
                                    <form action="/controller" method="post">
                                        <input type="hidden" name="command" value="payOrder">
                                        <input type="hidden" name="order" value="${order.getId()}">
                                        <fmt:message key="userCabinet.button.pay" var="button_pay"/>
                                        <input type="submit" value="${button_pay}">
                                    </form>
                                </c:if>
                            </td>
                        </c:when>
                    </c:choose>


                    </td>
                    <td>
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="editOrder">
                            <input type="hidden" name="order" value="${order.getId()}">
                            <fmt:message key="userCabinet.button.show" var="button_show"/>
                            <input type="submit" value="${button_show}">
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
