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
<fmt:message key="registration_new_admin.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page"/>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="header">
    <%@include file="/WEB-INF/jspf/header.jspf" %>

    <!-- Language switcher begin -->
    <form name="locales" action="<c:url value="/controller"/>" method="post">
        <label><select name="lang" onchange="this.form.submit()">
            <option selected disabled>
                <fmt:message key="language.chooseLang"/></option>
            <option value="ua"><fmt:message key="language.ua"/></option>
            <option value="en"><fmt:message key="language.en"/></option>
        </select></label>
        <input type="hidden" name="command" value="setLocale">
        <input type="hidden" name="page" value="/controller?command=viewRegNewAdmin">
    </form>
    <!-- end Language switcher -->
</div>
<div class="container">
    <c:if test="${not empty message}">
        <jsp:useBean id="message" scope="application" class="java.lang.String"/>
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <strong>${message}</strong>
            <!-- close message -->
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <c:remove var="message"/>
        </div>
    </c:if>
    <!-- main content-->
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><fmt:message key="registration_new_admin.jsp.text.reg_new_admin"/></div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/controller" method="post"
                          onsubmit="return validateForm()">
                        <input type="hidden" name="command" value="regNewAdmin">
                        <div class="form-group row">
                            <label for="login" class="col-md-4 col-form-label text-md-right">
                                <fmt:message key="registration_new_admin.jsp.label.login"/>
                            </label>
                            <div class="col-md-6">
                                <input type="text" id="login" class="form-control" name="login" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="password" class="col-md-4 col-form-label text-md-right">
                                <fmt:message key="registration_new_admin.jsp.label.password"/></label>
                            <div class="col-md-6">
                                <input type="password" id="password" class="form-control" name="password" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="confirm_password" class="col-md-4 col-form-label text-md-right">
                                <fmt:message key="registration_new_admin.jsp.label.confirm_password"/></label>
                            <div class="col-md-6">
                                <input type="password" id="confirm_password" class="form-control"
                                       name="confirm_password" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="name" class="col-md-4 col-form-label text-md-right">
                                <fmt:message key="registration_new_admin.jsp.label.name"/>
                            </label>
                            <div class="col-md-6">
                                <input type="text" id="name" class="form-control" name="name" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="surname" class="col-md-4 col-form-label text-md-right">
                                <fmt:message key="registration_new_admin.jsp.label.surname"/></label>
                            <div class="col-md-6">
                                <input type="text" id="surname" class="form-control" name="surname" required>
                            </div>
                        </div>
                        <div class="col-md-6 offset-md-4">
                            <button type="submit" class="btn btn-primary md">
                                <fmt:message key="registration_new_admin.jsp.button.register"/>
                            </button>
                            <button type="button" onclick="window.location.href='/home'"
                                    class="btn btn-danger md">
                                <fmt:message key="registration_new_admin.jsp.button.cancel"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function validateForm() {
        var login = document.getElementById('login').value;
        var loginRegEx = /(\w{4,15})/;
        var password = document.getElementById('password').value;
        var passwordRegEx = /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}/;
        var confirm_password = document.getElementById('confirm_password').value;
        var nameRegEx = /([а-яєіїґА-ЯЄІЇҐ,.' -ʼ]{2,})|([a-zA-Z,.' -ʼ]{2,})/;
        var name = document.getElementById('name').value;
        var surname = document.getElementById('surname').value;
        <fmt:message key="registration_new_admin.jsp.message.invalid_login" var="invalidLogin"/>
        <fmt:message key="registration_new_admin.jsp.message.invalid_pass" var="invalidPass"/>
        <fmt:message key="registration_new_admin.jsp.message.pass_not_same" var="difPass"/>
        <fmt:message key="registration_new_admin.jsp.message.invalid_name" var="invalidName"/>
        <fmt:message key="registration_new_admin.jsp.message.invalid_surname" var="invalidSurname"/>

        if (!loginRegEx.test(login)) {
            alert("${invalidLogin}");
            return false;
        } else if (password !== confirm_password) {
            alert("${difPass}");
            return false;
        } else if (!passwordRegEx.test(password)) {
            alert("${invalidPass}");
            return false;
        } else if (!nameRegEx.test(name)) {
            alert("${invalidName}");
            return false;
        } else if (!nameRegEx.test(surname)) {
            alert("${invalidSurname}");
            return false;
        }
    }
</script>
</body>
</html>
