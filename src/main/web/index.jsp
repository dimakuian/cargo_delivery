<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 02.02.2022
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>Title</title>
    <style>
        .elem {
            width: 30%;
            margin: 2px;
        }

        body {
            font-family: AppleSystemUIFont, serif;
        }

        div {
            text-align: center;
        }
    </style>
</head>
<body>
<c:set var="list" value="${applicationScope['localities']}"/>

<form action="login.jsp">
    <input type="submit" value="Login"/>
</form>

<h2 style="text-align:center">Some description about our company</h2>
<hr>
<div>
    <form action="count_fare" method="post">
        <label for="shipping">Choose shipping address</label><br>
        <input class="elem" list="shipping" name="shipAddres">
        <datalist id="shipping">
            <c:forEach items="${list}" var="loc">
                <option value="${loc.name}">${loc.name}</option>
            </c:forEach>
        </datalist>
        <br>
        <label for="delivery">Choose delivery address</label><br>
        <input list="delivery" class="elem" name="delivAddres">
        <datalist id="delivery">
            <c:forEach items="${list}" var="loc">
                <option value="${loc.name}">${loc.name}</option>
            </c:forEach>
        </datalist>
        <br>
        <label for="length">Enter length of your parcel</label><br>
        <input class="elem" id="length" name="length" type="text" min="1" pattern="[0-9]*"
               title="length can't be less the 1mm"/><br>
        <label for="height">Enter height of your parcel</label><br>
        <input class="elem" id="height" name="height" type="text" min="1" pattern="[0-9]*"
               title="height can't be less the 1mm"/><br>
        <label for="width">Enter width of your parcel</label><br>
        <input class="elem" id="width" name="width" type="text" min="1" pattern="[0-9]*"
               title="width can't be less the 1mm"/><br>
        <label for="weight">Enter weight of your parcel</label><br>
        <input class="elem" id="weight" name="weight" type="text" pattern="[0-9]*"
               title="weight can't be less the 1mm"/><br>
        <input type="submit" value="Count"/>
    </form>
    <p><c:out value="${str}"></c:out></p>

</div>
</body>
</html>
