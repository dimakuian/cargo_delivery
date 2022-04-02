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
<c:set var="title" value="Registration" scope="page" />
<%@include file="/WEB-INF/jspf/head.jspf"%>
<body>
<c:out value="${message}"/>
<c:remove var="message"/>
<div class="header">
    <%@include file="/WEB-INF/jspf/header.jspf"%>
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
<div class="container">
    <form action="<c:url value="/controller"/>" method="post">
        <input type="hidden" name="command" value="registration">
        <fmt:message key="placeholder.register.create_login" var="placeholder_create_login"/>
        <label>
            <input type="text" name="login" placeholder="${placeholder_create_login}" required pattern="^(\w{4,15})$">
        </label><br>
        <fmt:message key="placeholder.register.enter_password" var="placeholder_entre_password"/>
        <label for="psw"></label>
        <input type="password" id="psw" name="password" placeholder="${placeholder_entre_password}" required
               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}$"><br>
        <label>
            <input type="checkbox" onclick="showPsw('psw')">
        </label><fmt:message key="input.show_password"/>
        <fmt:message key="placeholder.confirm_password" var="placeholder_confirm_password"/>
        <label for="conf_psw"></label>
        <input type="password" id="conf_psw" name="confirm_password" placeholder="${placeholder_confirm_password}"
               required><br>
        <label>
            <input type="checkbox" onclick="showPsw('conf_psw')">
        </label><fmt:message key="input.show_password"/>
        <div id="message">
            <h3><fmt:message key="registration.message.validation_password"/></h3>
            <p id="letter" class="invalid">A <b><fmt:message key="registration.message.lowercase"/></b> letter</p>
            <p id="capital" class="invalid">A <b><fmt:message key="registration.message.uppercase"/></b> letter</p>
            <p id="number" class="invalid">A <b><fmt:message key="registration.message.number"/></b></p>
            <p id="length" class="invalid"><fmt:message key="registration.message.minimum"/>
                <b><fmt:message key="registration.message.characters"/></b></p>
        </div>
        <fmt:message key="registration.placeholder.enter_name" var="placeholder_name"/>
        <label><input type="text" name="name" placeholder="${placeholder_name}" required
                      pattern="^[a-zA-Zа-яА-Я]+$"></label><br>
        <fmt:message key="registration.placeholder.enter_surname" var="placeholder_surname"/>
        <label><input type="text" name="surname" placeholder="${placeholder_surname}" required
                      pattern="^[a-zA-Zа-яА-Я]+$"></label><br>
        <fmt:message key="registration.placeholder.enter_patronymic" var="placeholder_patronymic"/>
        <label><input type="text" name="patronymic" placeholder="${placeholder_patronymic}" required
                      pattern="^[a-zA-Zа-яА-Я]+$"></label><br>
        <fmt:message key="registration.placeholder.enter_email" var="placeholder_email"/>
        <label><input type="email" name="email" placeholder="${placeholder_email}" required
                      pattern='^[\w\-\.]+@([\w-]+\.)+[\w-]{2,4}$'></label><br>
        <fmt:message key="registration.placeholder.enter_tel" var="placeholder_tel"/>
        <label><input type="tel" name="tel" placeholder="${placeholder_tel}" required
                      pattern="^(\+{1}(380){1}[0-9]{9}){1}$"></label><br>
        <fmt:message key="button.register" var="register_button"/>
        <input type="submit" value="${register_button}">
        <button type="button" onclick="window.location.href='index.jsp'"><fmt:message key="button.cancel"/></button>
    </form>
</div>
</body>
</html>
