<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 29.01.2022
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="login" method="post">
    <fieldset>
        <legend>Login:</legend>
        <input type="text" name="login" placeholder="login"/> <br/>
        <input type="password" name="password" placeholder="password"/> <br/>
        <input type="submit" value="Submit"/>
        <button type="button" onclick="window.location.href='/register.jsp'">Register</button>
    </fieldset>
</form>
</body>
</html>
