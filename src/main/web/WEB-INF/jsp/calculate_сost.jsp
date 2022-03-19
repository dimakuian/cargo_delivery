<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 06.02.2022
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!-- Set actual locale -->
<c:choose>
    <c:when test="${empty locale}">
        <fmt:setLocale value="ua" scope="session"/>
        <c:set var="locale" value="ua" scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="${locale}" scope="session"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="resource"/>

<html>
<head>
    <c:set var="title" value="Count cost" scope="page"/>
    <%@include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%-- CONTENT --%>
<c:set var="list" value="${applicationScope['localities']}"/>
<%@include file="/WEB-INF/jspf/header.jspf" %>

<!-- Language switcher begin -->
<form name="locales" action="/controller" method="post">
    <select name="lang" onchange="this.form.submit()">
        <option selected disabled><fmt:message
                key="register.chooseLang"/></option>
        <option value="ua"><fmt:message key="register.ua"/></option>
        <option value="en"><fmt:message key="register.en"/></option>
    </select>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="/controller?command=viewCalculateCost">
</form>
<!-- end Language switcher -->
<div class="count_container">
    <form action="/controller" method="post"
          oninput="volume.value=(parseFloat(length.value)*parseFloat(height.value)*parseFloat(width.value)).toFixed(2)">
        <input type="hidden" name="command" value="calculateCost">
        <h5><fmt:message key="rout"/></h5>
        <label>
            <label>
                <select id="ship" class="address" list="shipping" name="shipping_address" required>
                    <c:forEach items="${list}" var="loc">
                        <option value="${loc.getId()}">${loc.getName()}</option>
                    </c:forEach>
                </select>
            </label>
            <span>==&gt</span>
            <label>
                <select id="deliv" class="address" list="delivery" name="delivery_address" required>
                    <c:forEach items="${list}" var="loc">
                        <option value="${loc.getId()}">${loc.getName()}</option>
                    </c:forEach>
                </select>
            </label>
        </label>
        <br>
        <label class="param" for="length"><fmt:message key="countCost.length_cm"/> </label>
        <input id="length" name="length" type="number" required min="0.1" max="70" step="any" value="1"
               title="length can't be less the 1mm"/><br>
        <label class="param" for="height"><fmt:message key="countCost.height_cm"/> </label>
        <input id="height" name="height" type="number" required min="0.1" max="70" step="any" value="1"
               title="height can't be less the 1mm"/><br>
        <label class="param" for="width"><fmt:message key="countCost.width_cm"/></label>
        <input id="width" name="width" type="number" required min="0.1" max="70" step="any" value="1"
               title="width can't be less the 1mm"/><br>
        <label class="param" for="volume"><fmt:message key="countCost.volume_cc"/></label>
        <input type="text" id="volume" name="volume" value="1" readonly>
        <br>
        <label class="param" for="weight"><fmt:message key="countCost.weight_kg"/></label>
        <input id="weight" name="weight" type="number" required min="0.1" max="100" step="any" value="1"/><br>
        <button type="submit"><fmt:message key="button.count"/></button>
    </form>
    <c:if test="${not empty total}">
        <fmt:message key="countCost.total_to_pay"/><c:out value=" ${total} "/><fmt:message key="currency"/> <br>
        <button type="button" onclick="location.href='/controller?command=viewCalculateCost'"><fmt:message
                key="button.cancel"/></button>
        <c:remove var="total"/>
    </c:if>
</div>
<%-- CONTENT --%>
</body>
</html>