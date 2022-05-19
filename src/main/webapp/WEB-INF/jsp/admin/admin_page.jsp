
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
<fmt:message key="admin_page.jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page"/>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>

<!-- Language switcher begin -->
<form name="locales" action="${pageContext.request.contextPath}/controller" method="post">
    <label for="lang"></label>
    <select id="lang" name="lang" onchange="this.form.submit()">
        <option selected disabled><fmt:message key="language.chooseLang"/></option>
        <option value="ua"><fmt:message key="language.ua"/></option>
        <option value="en"><fmt:message key="language.en"/></option>
    </select>
    <input type="hidden" name="command" value="setLocale">
    <input type="hidden" name="page" value="/controller?command=viewAdminPage">
</form>
<!-- end Language switcher -->
<jsp:useBean id="admin" scope="session" type="com.epam.delivery.db.entities.Admin"/>
<div class="container" style="margin: auto">
    <c:if test="${not empty message}">
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <strong>${message}</strong>
            <!-- close message -->
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <c:remove var="message"/>
        </div>
    </c:if>

    <h1><fmt:message key="admin_page.jsp.text.user_profile"/></h1>
    <hr>
    <div class="row">
        <!-- edit form column -->
        <div class="col-md-7 personal-info">
            <h3><fmt:message key="admin_page.jsp.text.personal_info"/></h3>
            <form class="form-horizontal" role="form" action="<c:url value="/controller"/>"
                  onsubmit="return validateEditForm();" method="post">
                <input type="hidden" name="command" value="editAdmin">
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="login">
                        <strong><fmt:message key="admin_page.jsp.label.login"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="login" name="login" class="form-control" type="text" value="${user.login}" readonly
                               required>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="name">
                        <strong><fmt:message key="admin_page.jsp.label.name"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="name" name="name" class="form-control" type="text" value="${admin.name}" readonly
                               required>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="surname">
                        <strong><fmt:message key="admin_page.jsp.label.surname"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="surname" name="surname" class="form-control" type="text" value="${admin.surname}"
                               readonly required>
                    </div>
                </div>


                <div class="row">
                    <!-- button for editing fields -->
                    <div class="col-md-2">
                        <fmt:message key="admin_page.jsp.button.edit" var="editButton"/>
                        <input type="button" id="editButton" value="${editButton}" class="btn btn-primary">
                    </div>

                    <!-- button for save changes -->
                    <div class="col-md-2">
                        <button class="btn btn-success" type="submit" id="saveButton" style="display: none">
                            <fmt:message key="admin_page.jsp.button.save"/>
                        </button>
                    </div>

                    <!-- change password button -->
                    <div class="col-md-2">
                        <fmt:message key="admin_page.jsp.button.change_password" var="changePass"/>
                        <input class="btn btn-secondary float-left" type="button" id="changePassButton"
                               value="${changePass}">
                    </div>
                </div>
            </form>
        </div>

        <!-- left column -->
        <div class="col-md-5">

            <!-- change password form -->
            <div id="changePassContainer" class="text-center col-md-12" style="padding: 5px; display: none">
                <form action="<c:url value="/controller"/>" method="post" onsubmit="return validateChangePassForm();">
                    <input type="hidden" name="page" value="/admin_page">
                    <input type="hidden" name="command" value="changePassword">
                    <div class="card">
                        <div class="card-header">
                            <fmt:message key="admin_page.jsp.text.change_pass_form"/>
                        </div>
                        <div class="card-body text-center">
                            <div class="row" style="padding: 5px">
                                <div class="col-md-12">
                                    <label for="pass"></label>
                                    <fmt:message key="admin_page.jsp.placeholder.enter_old_pass" var="oldPass"/>
                                    <input id="pass" name="pass" type="password" placeholder="${oldPass}"
                                           class="form-control" required>
                                </div>
                            </div>
                            <div class="row" style="padding: 5px">
                                <div class="col-md-12">
                                    <label for="newPass"></label>
                                    <fmt:message key="admin_page.jsp.placeholder.enter_new_pass" var="newPass"/>
                                    <input id="newPass" name="newPass" type="password" placeholder="${newPass}"
                                           class="form-control" required>
                                </div>
                            </div>
                            <div class="row" style="padding: 5px">
                                <div class="col-md-12">
                                    <label for="newPassConf"></label>
                                    <fmt:message key="admin_page.jsp.placeholder.repeat_pass" var="repeatPass"/>
                                    <input id="newPassConf" name="newPassConf" type="password"
                                           placeholder="${repeatPass}" class="form-control" required>
                                </div>
                            </div>
                            <div class="row" style="padding-top: 5px">
                                <div class="col-md-3">
                                    <fmt:message key="admin_page.jsp.button.save_changes" var="saveButt"/>
                                    <input id="subBut" name="subBut" type="submit" value="${saveButt}"
                                           class="btn btn-sm btn-success float-left">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>

    //fields edit button function
    document.getElementById('editButton').addEventListener("click", editParam);

    function editParam() {
        const editButton = document.getElementById('editButton');
        const saveButton = document.getElementById("saveButton");
        const name = document.getElementById('name');
        const surname = document.getElementById('surname');
        <fmt:message key="admin_page.jsp.button.cancel" var="cancel"/>

        if (editButton.value === '${editButton}') {
            name.readOnly = false;
            surname.readOnly = false;
            editButton.value = '${cancel}';
            editButton.className = 'btn btn-danger';
            saveButton.style.display = 'block';
        } else {
            document.location.reload();
        }
    }

    //fields edit validate function
    function validateEditForm() {
        const nameRegEx = /(^[а-яєіїґА-ЯЄІЇҐ,.' -/\D]{2,}$)|(^[a-zA-Z,.' -/\D]{2,}$)/;
        const name = document.getElementById('name').value;
        const surname = document.getElementById('surname').value;
        <fmt:message key="admin_page.jsp.message.valid_name" var="validName"/>
        <fmt:message key="admin_page.jsp.message.valid_surname" var="validSurname"/>

        if (!nameRegEx.test(name)) {
            alert("${validName}");
            return false;

        } else if (!nameRegEx.test(surname)) {
            alert("${validSurname}");
            return false;

        }
    }

    //change password button function
    document.getElementById('changePassButton').addEventListener("click", changePass);

    function changePass() {
        const changePassContainer = document.getElementById('changePassContainer');
        const changePassButton = document.getElementById('changePassButton');
        if (changePassButton.value === '${changePass}') {
            changePassContainer.style.display = 'block';
            changePassButton.value = '${cancel}';
        } else {
            changePassContainer.style.display = 'none';
            changePassButton.value = '${changePass}';
        }
    }

    //change password validate fields
    function validateChangePassForm() {
        const newPass = document.getElementById('newPass').value;
        const newPassConf = document.getElementById('newPassConf').value;
        const passwordRegEx = /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}/;
        <fmt:message key="admin_page.jsp.message.valid_pass" var="validPass"/>
        <fmt:message key="admin_page.jsp.message.different_pass" var="differentPass"/>
        if (!passwordRegEx.test(newPass)) {
            alert("${validPass}");
            return false;
        } else if (newPass !== newPassConf) {
            alert("${differentPass}");
            return false;
        }
    }
</script>
</body>
</html>
