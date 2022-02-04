<%@ page import="com.epam.delivery.entities.Locality" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%--

  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 02.02.2022
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>Title</title>
</head>
<body>

<form action="login.jsp">
    <input type="submit" value="Login"/>
</form>
<h2 style="text-align:center">Some description about our company</h2>
<hr>
<c:forEach var="list" items="${applicationScope['localities']}">
    <li>
        <c:out value="${list.name}"/>
    </li>
</c:forEach>


</body>
</html>
