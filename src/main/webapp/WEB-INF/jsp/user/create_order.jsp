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
<c:set var="title" value="create order" scope="page"/>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%-- CONTENT --%>
<%@include file="/WEB-INF/jspf/header.jspf" %>

<!-- Language switcher begin -->
<form name="locales" action="<c:url value="/controller"/>" method="post">
    <select name="lang" onchange="this.form.submit()">
        <option selected disabled><fmt:message
                key="register.chooseLang"/></option>
        <option value="ua"><fmt:message key="register.ua"/></option>
        <option value="en"><fmt:message key="register.en"/></option>
    </select>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="controller?command=viewCreateOrderPage">
</form>
<!-- end Language switcher -->
<c:choose>
    <c:when test="${role.getName() eq 'client'}">
        <div class="count_container">
            <c:set var="localitiesBeanList" value="${applicationScope['localities']}"/>
            <form action="<c:url value="/controller"/>" method="post"
                  oninput="volume.value=(parseFloat(length.value)*parseFloat(height.value)*
                  parseFloat(width.value)).toFixed(2)">

                <input type="hidden" name="command" value="createOrder">
                <h5><fmt:message key="rout"/></h5>
                <label>
                    <select id="ship" class="address" list="shipping" name="shipping_address" required>
                        <c:forEach items="${localitiesBeanList}" var="bean">
                            <option value="${bean.localityID}"><c:out value="#${bean.localityID} "/>
                                <c:choose>
                                    <c:when test="${locale=='en'}">
                                        <c:out value=" ${bean.description.en}"/>
                                    </c:when>
                                    <c:when test="${locale=='ua'}">
                                        <c:out value="${bean.description.ua}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value=" ${bean.description.ua}"/>
                                    </c:otherwise>
                                </c:choose>
                            </option>
                        </c:forEach>
                    </select>
                </label>
                <span>==&gt</span>
                <label>
                    <select id="deliv" class="address" list="delivery" name="delivery_address" required>
                        <c:forEach items="${localitiesBeanList}" var="bean">
                            <option value="${bean.localityID}"><c:out value="#${bean.localityID} "/>
                                <c:choose>
                                    <c:when test="${locale=='en'}">
                                        <c:out value=" ${bean.description.en}"/>
                                    </c:when>
                                    <c:when test="${locale=='ua'}">
                                        <c:out value="${bean.description.ua}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${bean.description.ua}"/>
                                    </c:otherwise>
                                </c:choose>
                            </option>
                        </c:forEach>
                    </select>
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
                <label><input style="width: 48%" type="text" name="consignee" required
                              placeholder="${consignee_placeholder}"></label><br>
                <fmt:message key="placeholder.description" var="description_placeholder"/>
                <label><input style="width: 48%" type="text" name="description" required
                              placeholder="${description_placeholder}">
                </label><br>
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
