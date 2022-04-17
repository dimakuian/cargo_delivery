<%--
  Created by IntelliJ IDEA.
  User: dimakuian
  Date: 30.03.2022
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!-- Set actual locale -->
<jsp:useBean id="locale" scope="session" type="java.lang.String"/>
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
<fmt:message key="inner_text_title_view_order" var="title"/>
<c:set var="title" value="${title}" scope="page"/>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<!-- Language switcher begin -->
<form name="locales" action="<c:url value="/controller"/>" method="post">
    <label for="lang"></label>
    <select id="lang" name="lang" onchange="this.form.submit()">
        <option selected disabled><fmt:message key="language.chooseLang"/></option>
        <option value="ua"><fmt:message key="language.ua"/></option>
        <option value="en"><fmt:message key="language.en"/></option>
    </select>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="/client_page.jsp">
</form>
<!-- end Language switcher -->

<jsp:useBean id="client" scope="session" type="com.epam.delivery.db.entities.Client"/>
<jsp:useBean id="user" scope="session" type="com.epam.delivery.db.entities.User"/>
<div class="container" style="margin: auto">
    <h1>Edit Profile</h1>
    <hr>
    <div class="row">
        <%--        <!-- left column -->--%>
        <%--        <div class="col-md-3">--%>
        <%--            <div class="text-center">--%>
        <%--                <img src="//placehold.it/100" class="avatar img-circle" alt="avatar">--%>
        <%--                <h6>Upload a different photo...</h6>--%>

        <%--                <input type="file" class="form-control">--%>
        <%--            </div>--%>
        <%--        </div>--%>

        <!-- edit form column -->
        <div class="col-md-7 personal-info">
            <!-- add alert here -->
            <h3>Personal info</h3>

            <form class="form-horizontal" role="form">
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="login">
                        <strong><fmt:message key="create_order.jsp.label.login"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="login" class="form-control" type="text" value="${user.login}" readonly>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="name">
                        <strong><fmt:message key="create_order.jsp.label.name"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="name" class="form-control" type="text" value="${client.name}" readonly>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="surname">
                        <strong><fmt:message key="create_order.jsp.label.surname"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="surname" class="form-control" type="text" value="${client.surname}" readonly>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="patronymic">
                        <strong><fmt:message key="create_order.jsp.label.patronymic"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="patronymic" class="form-control" type="text" value="${client.patronymic}" readonly>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="email">
                        <strong><fmt:message key="create_order.jsp.label.email"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="email" class="form-control" type="text" value="${client.email}" readonly>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="phone">
                        <strong><fmt:message key="create_order.jsp.label.phone"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="phone" class="form-control" type="text" value="${client.phone}" readonly>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="balance">
                        <strong><fmt:message key="create_order.jsp.label.balance"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="balance" class="form-control" type="text" value="${client.balance}" readonly>
                    </div>
                </div>
                <div class="row" style="margin: auto">
                    <fmt:message key="create_order.jsp.button.edit" var="editButton"/>
                    <button type="button" class="btn btn-primary" id="editButton" >
                       ${editButton}
                    </button>
                    <button class="btn btn-success" type="submit" id="saveButton" style="display: none">
                        <fmt:message key="create_order.jsp.button.save"/>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>

    document.getElementById('editButton').addEventListener("click",editParam);
    function editParam() {
        var editButton = document.getElementById('editButton');
        var saveButton = document.getElementById("saveButton");
        const login = document.getElementById('login');
        const name = document.getElementById('name');
        const surname = document.getElementById('surname');
        const patronymic = document.getElementById('patronymic');
        const email = document.getElementById('email');
        const phone = document.getElementById('phone');
        <fmt:message key="create_order.jsp.button.cancel" var="cancel"/>
        if  (editButton.innerHTML === '${editButton}'){
            login.readOnly = false;
            name.readOnly = false;
            surname.readOnly = false;
            patronymic.readOnly = false;
            email.readOnly = false;
            phone.readOnly = false;
            editButton.innerHTML = '${cancel}';
            editButton.className = 'btn btn-danger';
            saveButton.style.display = 'block';
        } else {
            login.readOnly = true;
            name.readOnly = true;
            surname.readOnly = true;
            patronymic.readOnly = true;
            email.readOnly = true;
            phone.readOnly = true;
            editButton.innerHTML = '${editButton}';
            editButton.className = 'btn btn-primary'
            saveButton.style.display = 'none'
        }
    }
</script>
</body>
</html>
