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
    <script src="myJSFunction.js"></script>
    <title>Title</title>
    <style>
        .size_param {
            margin-right: 100px;
            margin-left: 10px;
        }

        #weight {
            width: 50%;
        }
    </style>
</head>
<body>
<c:set var="list" value="${applicationScope['localities']}"/>

<%--<button type="button" onclick="showLogForm('log');">Menu</button>--%>
<input type="button" value="Login" id="button1" onclick="loginButton();">
<c:if test="${empty user}">
    <div id="log" style="display: none">
        <c:import url="login.jsp"/>
    </div>
</c:if>
<div id="isUser">
    <c:if test="${not empty user}">
        <c:out value="Hello ${user.getLogin()}"/>
        <button type="button" onclick="window.location.href='logout'">Logout</button>
    </c:if>
</div>

<h1 style="text-align:center">Some description about our company</h1>
<hr>
<div>
    <form action="count_fare" method="post"
          oninput="volume.value=(parseFloat(length.value)*parseFloat(height.value)*parseFloat(width.value)).toFixed(2)">
        <h5>Route</h5>
        <input list="shipping" name="shipAddres" required>
        <datalist id="shipping">
            <c:forEach items="${list}" var="loc">
                <option value="${loc.name}">${loc.name}</option>
            </c:forEach>
        </datalist>
        <span>==&gt</span>
        <input list="delivery" name="delivAddres" required>
        <datalist id="delivery">
            <c:forEach items="${list}" var="loc">
                <option value="${loc.name}">${loc.name}</option>
            </c:forEach>
        </datalist>
        <br>
        <p>
            <span class="size_param">Length, сm.</span><span class="size_param">Height, сm.</span>
            <span class="size_param">Width, сm.</span><span class="size_param">Volume, сc.</span>
        </p>
        <label for="length"/>
        <input id="length" name="length" type="number" required min="0.1" step="any" value="1" oninput="countVolume()"
               title="length can't be less the 1mm"/>
        <label for="height"/>
        <input id="height" name="height" type="number" required min="0.1" step="any" value="1" oninput="countVolume()"
               title="height can't be less the 1mm"/>
        <label for="width"/>
        <input id="width" name="width" type="number" required min="0.1" step="any" value="1" oninput="countVolume()"
               title="width can't be less the 1mm"/>
        <input type="text" id="volume" name="volume" value="1" disabled>
        <br>
        <label for="weight">Weight, kg.</label><br>
        <input id="weight" name="weight" type="range" required min="0.1" max="100" step="0.1" value="1"
               oninput="this.nextElementSibling.value=this.value"/>
        <output>1</output>
        <span>kg</span><br>
        <input type="submit" value="Count"/>
    </form>
</div>
</body>
</html>
