<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 05.02.2022
  Time: 22:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        /* Style all input fields */
        input, button {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            margin-top: 6px;
            margin-bottom: 16px;
            font-family: Raleway;
        }

        /* Style the submit button */
        input[type=submit], button[type=button] {
            background-color: #555;
            color: white;
            font-size: 17px;
            border: none;
            cursor: pointer;
            font-family: Raleway;
        }
        input[type=checkbox]{
            font-family: Raleway;
        }

        /* Style the container for inputs */
        .container {
            background-color: #f1f1f1;
            padding: 20px;
            font-family: Raleway;
        }

        /* The message box is shown when the user clicks on the password field */
        #message {
            display: none;
            background: #f1f1f1;
            color: #000;
            position: relative;
            padding: 20px;
            margin-top: 10px;
            font-family: Raleway;
        }

        #message p {
            padding: 10px 35px;
            font-size: 18px;
            font-family: Raleway;
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

        body {
            width: 50%;
            margin: auto;
        }
    </style>
</head>
<body>
<p>REGISTER JSP</p>
<c:out value="${message}"></c:out>
<div class="container">
    <form action="/register_new_user" method="post">
        <input type="text" name="login" placeholder="create login" required pattern="^(\w{4,15})$"><br>
        <input type="password" id="psw" name="password" placeholder="enter password" required
               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}$"><br>
        <input type="checkbox" onclick="showPsw('psw')">show password
        <input type="password" id="conf_psw" name="confirm_password" placeholder="repeat entered password"
               required><br>
        <input type="checkbox" onclick="showPsw('conf_psw')">show password
        <div id="message">
            <h3>Password must contain the following:</h3>
            <p id="letter" class="invalid">A <b>lowercase</b> letter</p>
            <p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
            <p id="number" class="invalid">A <b>number</b></p>
            <p id="length" class="invalid">Minimum <b>8 characters</b></p>
        </div>
        <input type="text" name="name" placeholder="enter your name" required pattern="^[a-zA-Zа-яА-Я]+$"><br>
        <input type="text" name="surname" placeholder="enter your surname" required pattern="^[a-zA-Zа-яА-Я]+$"><br>
        <input type="text" name="patronymic" placeholder="enter your patronymic" required
               pattern="^[a-zA-Zа-яА-Я]+$"><br>
        <input type="email" name="email" placeholder="enter your email" required
               pattern='^[\w\-\.]+@([\w-]+\.)+[\w-]{2,4}$'><br>
        <input type="tel" name="tel" placeholder="enter your phone number in format: +380677777777" required
               pattern="^(\+{1}(380){1}[0-9]{9}){1}$"><br>
        <input type="submit" value="Register">
        <button type="button" onclick="window.location.href='index.jsp'">Cancel</button>
    </form>
</div>

<script>
    var password = document.getElementById("psw")
        , confirm_password = document.getElementById("conf_psw");

    function validatePassword() {
        if (password.value != confirm_password.value) {
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
