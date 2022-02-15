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
</head>
<body>
<c:set var="client" value="${client}"/>
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
