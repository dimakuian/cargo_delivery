<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 08.02.2022
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
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
        }

        .topnav input[type=text], input[type=password] {
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
</head>
<body>
<div class="topnav">
    <a id="home_page" class="active" href="index.jsp">Home</a>
    <a href="#about">About</a>
    <a href="countCost.jsp">Count coast</a>
    <a href="createOrder.jsp">Create order</a>
    <div class="login-container">
        <%--        <input id="home_page" type="button" value="Home page" onclick="window.location.href='index.jsp'">--%>
        <c:choose>
            <c:when test="${empty user}">
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="login"/>
                    <label for="log"></label>
                    <input id="log" type="text" placeholder="Username" name="login">
                    <label for="psw"></label>
                    <input id="psw" type="password" placeholder="Password" name="password">
                    <button type="button" onclick="window.location.href='/registration.jsp'">Register</button>
                    <button type="submit">Login</button>
                </form>
            </c:when>
            <c:when test="${not empty user}">
<%--                <form action="/controller" method="post">--%>
<%--                    <input type="hidden" name="command" value="logout">--%>
<%--                    <button type="submit">Logout</button>--%>
<%--                </form>--%>
<%--                <form action="/controller" method="post">--%>
<%--                    <input type="hidden" name="command" value="userCabinet">--%>
<%--                    <button type="submit">${user.getLogin()}</button>--%>
<%--                </form>--%>
                <button onclick="window.location.href='/controller?command=logout'">Logout</button>
                <button onclick="window.location.href='/controller?command=userCabinet'">${user.getLogin()}</button>
            </c:when>
        </c:choose>
    </div>
</div>
<script>
    var homePage = document.getElementById("home_page");
    if (window.location.href.indexOf("/index.jsp") !== -1) {
        homePage.style.display = "none";
    } else {
        homePage.style.display = "block";
    }
</script>
</body>
</html>
