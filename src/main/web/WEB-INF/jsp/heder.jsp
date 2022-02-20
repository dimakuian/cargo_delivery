<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 08.02.2022
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
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
<html>
<head>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Raleway, serif;
        }

        .topnav {
            overflow: hidden;
            background-color: #e9e9e9;
        }

        .topnav a {
            float: left;
            display: block;
            color: black;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
            font-family: Raleway, serif;
        }

        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }

        .topnav a.active {
            background-color: #04AA6D;
            color: white;
        }

        .topnav .login-container {
            float: right;
            padding-right: 10px;
        }

        .topnav input[type=text], .topnav input[type=password] {
            padding: 6px;
            margin-top: 8px;
            font-size: 17px;
            border: none;
            width: 120px;
            font-family: Raleway, serif;
        }

        .topnav .login-container button {
            float: right;
            padding: 6px 10px;
            margin-top: 8px;
            background-color: #555;
            color: white;
            font-size: 17px;
            border: none;
            cursor: pointer;
            font-family: Raleway, serif;
        }

        .topnav .login-container button:hover {
            background-color: green;
        }

        @media screen and (max-width: 600px) {
            .topnav .login-container {
                float: none;
            }

            .topnav a, .topnav input[type=text], .topnav .login-container button {
                float: none;
                display: block;
                text-align: left;
                width: 100%;
                margin: 0;
                padding: 14px;
                font-family: Raleway, serif;
            }

            .topnav input[type=text] {
                border: 1px solid #ccc;
                font-family: Raleway, serif;
            }
        }
    </style>
    <title></title>
</head>
<body>
<div class="topnav">
    <a id="home_page" class="active" href="<c:url value="/index.jsp"/>"><fmt:message key="button.home"/></a>
    <a href="#about"><fmt:message key="button.about"/></a>
    <a href="<c:url value="/controller?command=enterCountFare"/>"><fmt:message key="button.count_coast"/></a>
    <c:choose>
        <c:when test="${role.getName() eq 'client'}">
            <a href="controller?command=enterCreateOrderForm"><fmt:message key="button.create_order"/></a>
        </c:when>
    </c:choose>

    <div class="login-container">
        <c:choose>
            <c:when test="${empty user}">
                <form action="<c:url value="/controller"/>" method="post" style="display: inline;">
                    <input type="hidden" name="command" value="login"/>
                    <label for="log"></label>
                    <fmt:message key="input.username" var="userName"/>
                    <input id="log" type="text" placeholder="${userName}" name="login" required>
                    <label for="psw"></label>
                    <fmt:message key="input.password" var="pass"/>
                    <input id="psw" type="password" placeholder="${pass}" name="password" required>
                    <button id="register_button" type="button"
                            onclick="window.location.href='/controller?command=enterRegistrationForm'">
                        <fmt:message key="button.register"/></button>
                    <button type="submit"><fmt:message key="button.login"/></button>
                </form>
            </c:when>
            <c:when test="${not empty user}">
                <button onclick="window.location.href='/controller?command=logout'"><fmt:message
                        key="button.logout"/></button>
                <button onclick="window.location.href='/controller?command=userCabinet'">${user.getLogin()}</button>
            </c:when>
        </c:choose>
    </div>
</div>
<script>
    const homePage = document.getElementById("home_page");
    if (window.location.href.indexOf("/index.jsp") !== -1) {
        homePage.style.display = "none";
    } else {
        homePage.style.display = "block";
    }

    const register_button = document.getElementById("register_button");
    if (window.location.href.indexOf("/registration.jsp") !== -1) {
        register_button.style.display = "none";
    } else {
        register_button.style.display = "block";
    }
</script>
</body>
</html>