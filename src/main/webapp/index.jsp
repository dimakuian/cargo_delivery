<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 02.02.2022
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="WEB-INF/jspf/directive/taglib.jspf" %>
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
<c:set var="title" value="Home" scope="page"/>
<%@include file="WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="WEB-INF/jspf/header.jspf" %>
<!-- Language switcher begin -->

<form name="locales" action="/controller" method="post">
    <label>
        <select name="lang" onchange="this.form.submit()">
            <option selected disabled><fmt:message
                    key="language.chooseLang"/></option>
            <option value="ua"><fmt:message key="language.ua"/></option>
            <option value="en"><fmt:message key="language.en"/></option>
        </select>
    </label>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="index.jsp">
</form>
<!-- end Language switcher -->

<div class="container-fluid">
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="display-4"><fmt:message key="index.jsp.text.description"/></h1>
            <h1 class="display-4"><fmt:message key="index.jsp.text.description2"/></h1>
            <p class="lead"><fmt:message key="index.jsp.text.description_p"/></p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-4">
            <p><fmt:message key="index.jsp.text.description_principles"/></p>
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><strong><fmt:message key="index.jsp.text.professionalism"/></strong></li>
                <li class="list-group-item"><strong><fmt:message key="index.jsp.text.prompt"/></strong></li>
                <li class="list-group-item"><strong><fmt:message key="index.jsp.text.constant"/></strong></li>
                <li class="list-group-item"><strong><fmt:message key="index.jsp.text.strict"/></strong></li>
                <li class="list-group-item"><strong><fmt:message key="index.jsp.text.clear"/></strong></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6">
            <div id="accordion2">
                <div class="card">
                    <div class="card-header" id="headingThree">
                        <h5 class="mb-0">
                            <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                <fmt:message key="index.jsp.text.tariff"/>
                            </button>
                        </h5>
                    </div>
                    <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordion">
                        <div class="container">
                            <h4><fmt:message key="index.jsp.text.tariff_description"/></h4>
                            <p><fmt:message key="index.jsp.text.all_types"/></p>
                            <p><fmt:message key="index.jsp.text.peculiarity"/></p>
                            <p><strong><fmt:message key="index.jsp.text.example_title"/></strong></p>
                            <p><fmt:message key="index.jsp.text.example"/></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div id="accordion">
                <div class="card">
                    <div class="card-header" id="headingOne">
                        <h5 class="mb-0">
                            <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true"
                                    aria-controls="collapseOne">
                                <fmt:message key="index.jsp.text.our_department"/>
                            </button>
                        </h5>
                    </div>
                    <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                        <div class="card-body">
                            <ul class="list-group list-group-flush">
                                <c:forEach var="depart" items="${localities}">
                                    <jsp:useBean id="depart" type="com.epam.delivery.db.entities.bean.LocalityBean"/>
                                    <li class="list-group-item">
                                        <c:choose>
                                            <c:when test="${locale=='en'}">
                                                <c:out value="${depart.description.en}"/>
                                            </c:when>
                                            <c:when test="${locale=='ua'}">
                                                <c:out value="${depart.description.ua}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${depart.description.ua}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $('.collapse').collapse()
</script>
</body>
</html>
