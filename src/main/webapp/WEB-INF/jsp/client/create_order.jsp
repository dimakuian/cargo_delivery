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
        <option selected disabled><fmt:message key="language.chooseLang"/></option>
        <option value="ua"><fmt:message key="language.ua"/></option>
        <option value="en"><fmt:message key="language.en"/></option>
    </select>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="controller?command=viewCreateOrderPage">
</form>
<!-- end Language switcher -->
<c:set var="localitiesBeanList" value="${applicationScope['localities']}"/>
<%-- CONTENT --%>
<main class="create_order-form">
    <div class="container">
        <c:if test="${not empty message}">
            <jsp:useBean id="message" scope="application" type="java.lang.String"/>
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <strong>${message}</strong>
                <!-- close message -->
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <c:remove var="message"/>
            </div>
        </c:if>
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header"><fmt:message key="button.create_order"/></div>
                    <div class="card-body">
                        <form name="create_order-form" onsubmit="return validformCreateOrder()" action="/controller"
                              method="post"
                              oninput="volume.value=(parseFloat(length.value)*parseFloat(height.value)*parseFloat(width.value)).toFixed(2)">
                            <input type="hidden" name="command" value="createOrder">

                            <!-- field for delivery shipping address -->
                            <div class="form-group row">
                                <label for="ship" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="inner_text.shipping_address"/></label>
                                <div class="col-md-6">
                                    <select id="ship" list="shipping" name="shipping_address" class="form-control"
                                            required>
                                        <c:forEach items="${localitiesBeanList}" var="bean">
                                            <option value="${bean.localityID}"><c:out value="#${bean.localityID} "/>
                                                <c:choose>
                                                    <c:when test="${locale=='en'}">
                                                        <c:out value="${bean.description.en}"/>
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
                                </div>
                            </div>

                            <!-- field for delivery delivery address -->
                            <div class="form-group row">
                                <label for="deliv" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="inner_text.delivery_address"/></label>
                                <div class="col-md-6">
                                    <select id="deliv" list="shipping" name="delivery_address" class="form-control"
                                            required>
                                        <c:forEach items="${localitiesBeanList}" var="bean">
                                            <option value="${bean.localityID}"><c:out value="#${bean.localityID} "/>
                                                <c:choose>
                                                    <c:when test="${locale=='en'}">
                                                        <c:out value="${bean.description.en}"/>
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
                                </div>
                            </div>

                            <!-- field for delivery length -->
                            <div class="form-group row">
                                <label for="length" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="inner_text.length_cm"/></label>
                                <div class="col-md-6">
                                    <input type="number" id="length" name="length" class="form-control" required
                                           min="0.1" max="70" step="any" value="1">
                                </div>
                            </div>

                            <!-- field for delivery height -->
                            <div class="form-group row">
                                <label for="height" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="inner_text.height_cm"/></label>
                                <div class="col-md-6">
                                    <input type="number" id="height" name="height" class="form-control" required
                                           min="0.1" max="70" step="any" value="1">
                                </div>
                            </div>

                            <!-- field for delivery width -->
                            <div class="form-group row">
                                <label for="width" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="inner_text.width_cm"/></label>
                                <div class="col-md-6">
                                    <input type="number" id="width" name="width" class="form-control" required
                                           min="0.1" max="70" step="any" value="1">
                                </div>
                            </div>

                            <!-- field for delivery volume -->
                            <div class="form-group row">
                                <label for="volume" class="col-md-4 col-form-label text-md-right">
                                    <span><fmt:message key="inner_text.volume_cc"/><sup>3</sup></span>
                                </label>
                                <div class="col-md-6">
                                    <input type="text" id="volume" name="volume" value="1" class="form-control"
                                           readonly>
                                </div>
                            </div>

                            <!-- field for delivery weight -->
                            <div class="form-group row">
                                <label for="weight" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="inner_text.weight_kg"/></label>
                                <div class="col-md-6">
                                    <input id="weight" name="weight" type="number" class="form-control" required
                                           min="0.1" max="100" step="any" value="1">
                                </div>
                            </div>

                            <!-- field for delivery consignee -->
                            <div class="form-group row">
                                <label for="consignee" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="inner_text.consignee"/></label>
                                <div class="col-md-6">
                                    <input id="consignee" name="consignee" type="text" class="form-control" required>
                                </div>
                            </div>

                            <!-- field for delivery description -->
                            <div class="form-group row">
                                <label for="description" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="inner_text.description"/></label>
                                <div class="col-md-6">
                                    <input type="text" id="description" class="form-control" name="description"
                                           required>
                                </div>
                            </div>

                            <!-- button for count coast -->
                            <div class="col-md-6 offset-md-4">
                                <button type="submit" class="btn btn-primary md">
                                    <fmt:message key="button.create_order"/></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<script>

    function validformCreateOrder() {
        var consignee = document.forms["create_order-form"]["consignee"].value;
        var consigneeRegex = /([а-яєіїґА-ЯЄІЇҐ,.' -ʼ]{2,})|([a-zA-Z,.' -ʼ]{2,})((\+38)?[0-9]{10}){1}/;
        var description = document.forms["create_order-form"]["description"].value;
        var descriptionRegex = /([0-9a-zA-Zа-яА-Я,.'\-]{3,}\s?)+/;
        var shipping_address = document.forms["create_order-form"]["shipping_address"].value;
        var delivery_address = document.forms["create_order-form"]["delivery_address"].value;

        <fmt:message key="message.not_validate_consignee" var="validate_consignee"/>
        <fmt:message key="message.not_validate_description" var="validate_description"/>
        <fmt:message key="message.address_mast_be_different" var="similar_address"/>
        //check consignee

         if (!consigneeRegex.test(consignee)) {
            alert("${validate_consignee}");
            return false;

        } else if (!descriptionRegex.test(description)) {
            alert("${validate_description}");
            return false;

        } else if (shipping_address == delivery_address) {
            alert("${similar_address}");
            return false;
        }
    }
</script>
</body>
</html>
