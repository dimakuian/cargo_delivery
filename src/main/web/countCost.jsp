<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 06.02.2022
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>count cost</title>
    <style>
        .count_container {
            width: 50%;
            *padding: 20px;
            font-family: Raleway;
            margin: auto;
        }

        .count_container select {
            width: 30%;
            height: 30px;
            font-size: 17px;
            border: none;
            font-family: Raleway;
            border: 1px;
            box-sizing: border-box;
        }

        .count_container input[type=submit], .count_container button[type=button] {
            background-color: #555;
            color: white;
            font-size: 17px;
            border: none;
            cursor: pointer;
            font-family: Raleway;
        }

        .count_container input, .count_container button {
            width: 30%;
            padding: 12px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            margin-top: 6px;
            margin-bottom: 16px;
            font-size: 17px;
            font-family: Raleway;
        }

        body {
            background-color: #f1f1f1;
        }

        .param {
            width: 15%;
            display: inline-block;
        }

        .count_container #shipping {
            width: 60%;
        }

        .count_container input[type=number], .count_container input[type=text] {
            width: 15%;
        }

        .count_container .address {
            width: 45%;
            display: inline-block;
        }
    </style>
</head>
<body>
<%-- CONTENT --%>
<c:set var="list" value="${applicationScope['localities']}"/>
<c:import url="heder.jsp"></c:import>
<div class="count_container">
    <form action="/controller" method="post"
          oninput="volume.value=(parseFloat(length.value)*parseFloat(height.value)*parseFloat(width.value)).toFixed(2)">
        <input type="hidden" name="command" value="countFare">
        <h5>Route</h5>
        <label>
            <label>
                <select id="ship" class="address" list="shipping" name="shipping_address" required>
                    <c:forEach items="${list}" var="loc">
                        <option value="${loc.id}">${loc.name}</option>
                    </c:forEach>
                </select>
            </label>
            <span>==&gt</span>
            <label>
                <select id="deliv" class="address" list="delivery" name="delivery_address" required>
                    <c:forEach items="${list}" var="loc">
                        <option value="${loc.id}">${loc.name}</option>
                    </c:forEach>
                </select>
            </label>
        </label>
        <br>
        <label class="param" for="length">Length, сm.</label>
        <input id="length" name="length" type="number" required min="0.1" max="70" step="any" value="1"
               title="length can't be less the 1mm"/><br>
        <label class="param" for="height">Height, сm.</label>
        <input id="height" name="height" type="number" required min="0.1" max="70" step="any" value="1"
               title="height can't be less the 1mm"/><br>
        <label class="param" for="width">Width, сm.</label>
        <input id="width" name="width" type="number" required min="0.1" max="70" step="any" value="1"
               title="width can't be less the 1mm"/><br>
        <label class="param" for="volume">Volume, сc.</label>
        <input type="text" id="volume" name="volume" value="1" readonly>
        <br>
        <label class="param" for="weight">Weight, kg.</label>
        <input id="weight" name="weight" type="number" required min="0.1" max="100" step="any" value="1"/><br>
        <input type="submit" value="Count"/>
    </form>
    <c:if test="${not empty total}">
        <c:out value="Total to pay: "/><c:out value="${total}"/><br>
        <c:set var="tot" value="${total}"></c:set>
        <form action="<c:url value="/create_order"/>" method="get">
            <input type="hidden" name="total" value="${total}">
            <input type="submit" value="Create order">
            <button type="button" onclick="location.href='countCost.jsp'">Cancel</button>
        </form>
        <c:remove var="total"/>
    </c:if>
</div>
<%-- CONTENT --%>
</body>
</html>
