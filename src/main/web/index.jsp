<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 02.02.2022
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:import url="heder.jsp"/>
<c:out value="${message}"/>
<c:remove var="message"/>
<h1>Some description about our company</h1>
<hr>
<c:import url="hello.jsp"/>
</body>
</html>
