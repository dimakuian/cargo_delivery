<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 05.02.2022
  Time: 22:49
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
<c:set var="title" value="Registration" scope="page"/>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<body>
<c:out value="${message}"/>
<c:remove var="message"/>
<div class="header">
    <%@include file="/WEB-INF/jspf/header.jspf" %>

    <!-- Language switcher begin -->
    <form name="locales" action="<c:url value="/controller"/>" method="post">
        <label><select name="lang" onchange="this.form.submit()">
            <option selected disabled><fmt:message
                    key="language.chooseLang"/></option>
            <option value="ua"><fmt:message key="language.ua"/></option>
            <option value="en"><fmt:message key="language.en"/></option>
        </select></label>
        <input type="hidden" name="command" value="setLocale">
        <input type="hidden" name="page" value="/controller?command=viewRegistrationPage">
    </form>
    <!-- end Language switcher -->
</div>

<!-- main content-->
<main class="my-form">
    <div class="cotainer">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header"><fmt:message key="button.register"/></div>
                    <div class="card-body">
                        <form name="my-form" onsubmit="return validform('${locale}')" action="/controller" method="post">
                            <input type="hidden" name="command" value="registration">

                            <div class="form-group row">
                                <label for="phone" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="registration.fields_name.login"/></label>
                                <div class="col-md-6">
                                    <input type="text" id="login" class="form-control" name="login" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="password" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="registration.fields_name.password"/></label>
                                <div class="col-md-6">
                                    <input type="password" id="password" class="form-control" name="password" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="confirm_password" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="registration.fields_name.confirm_password"/></label>
                                <div class="col-md-6">
                                    <input type="password" id="confirm_password" class="form-control" name="confirm_password"
                                           required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="name" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="registration.fields_name.name"/>
                                </label>
                                <div class="col-md-6">
                                    <input type="text" id="name" class="form-control" name="name" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="surname" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="registration.fields_name.surname"/></label>
                                <div class="col-md-6">
                                    <input type="text" id="surname" class="form-control" name="surname" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="patronymic" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="registration.fields_name.patronymic"/></label>
                                <div class="col-md-6">
                                    <input type="text" id="patronymic" class="form-control" name="patronymic" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="email" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="registration.fields_name.email"/></label>
                                <div class="col-md-6">
                                    <input type="text" id="email" class="form-control" name="email" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="phone" class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="registration.fields_name.phone"/></label>
                                <div class="col-md-6">
                                    <input type="text" id="phone" class="form-control" name="tel" required>
                                </div>
                            </div>
                            <div class="col-md-6 offset-md-4">
                                <button type="submit" class="btn btn-primary md">
                                    <fmt:message key="button.register"/></button>
                                <button type="button" onclick="window.location.href='/home'" class="btn btn-danger md">
                                    <fmt:message key="button.cancel"/></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>
