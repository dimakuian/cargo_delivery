<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 05.02.2022
  Time: 22:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <style>
        /* Style all input fields */
        .container input, .container button {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            margin-top: 6px;
            margin-bottom: 16px;
            font-family: Raleway, serif;
        }

        /* Style the submit button */
        .container input[type=submit], .container button[type=button] {
            background-color: #555;
            color: white;
            font-size: 17px;
            border: none;
            cursor: pointer;
            font-family: Raleway, serif;
        }

        .container input[type=checkbox] {
            font-family: Raleway, serif;
        }

        /* Style the container for inputs */
        .container {
            background-color: #f1f1f1;
            padding: 20px;
            font-family: Raleway, serif;
        }

        /* The message box is shown when the user clicks on the password field */
        #message {
            display: none;
            background: #f1f1f1;
            color: #000;
            position: relative;
            padding: 20px;
            margin-top: 10px;
            font-family: Raleway, serif;
        }

        #message p {
            padding: 10px 35px;
            font-size: 18px;
            font-family: Raleway, serif;
        }

        /* Add a green text color and a checkmark when the requirements are right */
        .valid {
            color: green;
        }

        .valid:before {
            position: relative;
            left: -35px;
            content: "✔";
        }

        /* Add a red text color and an "x" when the requirements are wrong */
        .invalid {
            color: red;
        }

        .invalid:before {
            position: relative;
            left: -35px;
            content: "✖";
        }

        .container {
            width: 50%;
            margin: auto;
        }
    </style>
</head>

<body>
<c:out value="${message}"/>
<c:remove var="message"/>
<div class="header">
    <c:import url="heder.jsp"/>
    <!-- Language switcher begin -->
    <form name="locales" action="<c:url value="/controller"/>" method="post">
        <label><select name="lang" onchange="this.form.submit()">
            <option selected disabled><fmt:message
                    key="register.chooseLang"/></option>
            <option value="ua"><fmt:message key="register.ua"/></option>
            <option value="en"><fmt:message key="register.en"/></option>
        </select></label>
        <input type="hidden" name="command" value="setLocale">
        <input type="hidden" name="page" value="/controller?command=enterRegistrationForm">
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

<script>
    var password = document.getElementById("psw")
        , confirm_password = document.getElementById("conf_psw");

    function validatePassword() {
        if (password.value !== confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>
<script>
    var myInput = document.getElementById("psw");
    var letter = document.getElementById("letter");
    var capital = document.getElementById("capital");
    var number = document.getElementById("number");
    var length = document.getElementById("length");

    // When the user clicks on the password field, show the message box
    myInput.onfocus = function () {
        document.getElementById("message").style.display = "block";
    }

    // When the user clicks outside of the password field, hide the message box
    myInput.onblur = function () {
        document.getElementById("message").style.display = "none";
    }
    // When the user starts to type something inside the password field
    myInput.onkeyup = function () {
        // Validate lowercase letters
        var lowerCaseLetters = /[a-z]/g;
        if (myInput.value.match(lowerCaseLetters)) {
            letter.classList.remove("invalid");
            letter.classList.add("valid");
        } else {
            letter.classList.remove("valid");
            letter.classList.add("invalid");
        }
        // Validate capital letters
        var upperCaseLetters = /[A-Z]/g;
        if (myInput.value.match(upperCaseLetters)) {
            capital.classList.remove("invalid");
            capital.classList.add("valid");
        } else {
            capital.classList.remove("valid");
            capital.classList.add("invalid");
        }
        // Validate numbers
        var numbers = /[0-9]/g;
        if (myInput.value.match(numbers)) {
            number.classList.remove("invalid");
            number.classList.add("valid");
        } else {
            number.classList.remove("valid");
            number.classList.add("invalid");
        }
        // Validate length
        if (myInput.value.length >= 8) {
            length.classList.remove("invalid");
            length.classList.add("valid");
        } else {
            length.classList.remove("valid");
            length.classList.add("invalid");
        }
    }

    function showPsw(id) {
        var x = document.getElementById(id);
        if (x.type === "password") {
            x.type = "text";
        } else {
            x.type = "password";
        }
    }
</script>
</body>
</html>
