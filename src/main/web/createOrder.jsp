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
            font-size: 17px;
            border: none;
            font-family: Raleway;
            padding: 12px;
            margin-bottom: 20px;
            background-color: white;
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
            margin-right: 10px;
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
<c:choose>
    <c:when test="${role.getName() eq 'client'}">
        <div class="count_container">
            <form action="/controller" method="post"
                  oninput="volume.value=(parseFloat(length.value)*parseFloat(height.value)*parseFloat(width.value)).toFixed(2)">
                <input type="hidden" name="command" value="createOrder">
                <h5><fmt:message key="rout"/></h5>
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
                <label class="param" for="length"><fmt:message key="countCost.length_cm"/></label>
                <input id="length" name="length" type="number" required min="0.1" max="70" step="any" value="1"
                       title="length can't be less the 1mm"/>
                <label class="param" for="height"><fmt:message key="countCost.height_cm"/></label>
                <input id="height" name="height" type="number" required min="0.1" max="70" step="any" value="1"
                       title="height can't be less the 1mm"/>
                <label class="param" for="width"><fmt:message key="countCost.width_cm"/></label>
                <input id="width" name="width" type="number" required min="0.1" max="70" step="any" value="1"
                       title="width can't be less the 1mm"/>
                <label class="param" for="volume"><fmt:message key="countCost.volume_cc"/></label>
                <input type="text" id="volume" name="volume" value="1" readonly>
                <label class="param" for="weight"><fmt:message key="countCost.weight_kg"/></label>
                <input id="weight" name="weight" type="number" required min="0.1" max="100" step="any" value="1"/><br>
                <fmt:message key="placeholder.consignee" var="consignee_placeholder"/>
                <input style="width: 48%" type="text" name="consignee" required
                       placeholder="${consignee_placeholder}"><br>
                <fmt:message key="placeholder.description" var="description_placeholder"/>
                <input style="width: 48%" type="text" name="description" required placeholder="${description_placeholder}"><br>
                <fmt:message key="input.create_order" var="creteOrder"/>
                <input type="submit" value="${creteOrder}"/>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <p><fmt:message key="message.login_before"/></p>
    </c:otherwise>
</c:choose>

<%-- CONTENT --%>
</body>
</html>
