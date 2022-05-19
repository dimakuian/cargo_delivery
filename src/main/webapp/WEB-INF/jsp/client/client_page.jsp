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
    <input type="hidden" name="page" value="/controller?command=viewClientPage">
</form>
<!-- end Language switcher -->

<jsp:useBean id="client" scope="session" type="com.epam.delivery.db.entities.Client"/>
<div class="container" style="margin: auto">

    <c:if test="${not empty message}">
        <jsp:useBean id="message" scope="application" type="java.lang.String"/>
        <div class="alert alert-warning alert-dismissible fade show" role="alert">
            <strong>${message}</strong>
            <!-- close message -->
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <c:remove var="message"/>
        </div>
    </c:if>

    <h1><fmt:message key="client_page.jsp.text.user_profile"/></h1>
    <hr>
    <div class="row">
        <!-- edit form column -->
        <div class="col-md-7 personal-info">
            <h3><fmt:message key="client_page.jsp.text.personal_info"/></h3>
            <form class="form-horizontal" role="form" action="<c:url value="/controller"/>"
                  onsubmit="return validateEditForm();" method="post">
                <input type="hidden" name="command" value="editClient">
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="login">
                        <strong><fmt:message key="client_page.jsp.label.login"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="login" name="login" class="form-control" type="text" value="${user.login}" readonly
                               required>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="name">
                        <strong><fmt:message key="client_page.jsp.label.name"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="name" name="name" class="form-control" type="text" value="${client.name}" readonly
                               required>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="surname">
                        <strong><fmt:message key="client_page.jsp.label.surname"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="surname" name="surname" class="form-control" type="text" value="${client.surname}"
                               readonly required>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="patronymic">
                        <strong><fmt:message key="client_page.jsp.label.patronymic"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="patronymic" name="patronymic" class="form-control" type="text"
                               value="${client.patronymic}" readonly required>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="email">
                        <strong><fmt:message key="client_page.jsp.label.email"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="email" name="email" class="form-control" type="text" value="${client.email}"
                               readonly required>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="phone">
                        <strong><fmt:message key="client_page.jsp.label.phone"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="phone" name="phone" class="form-control" type="text" value="${client.phone}"
                               readonly required>
                    </div>
                </div>
                <div class="row" style="padding: 5px">
                    <label class="col-lg-4 control-label" for="balance">
                        <strong><fmt:message key="client_page.jsp.label.balance"/></strong>
                    </label>
                    <div class="col-lg-8">
                        <input id="balance" class="form-control" type="text" value="${client.balance}" readonly>
                    </div>
                </div>

                <div class="row">
                    <!-- button for editing fields -->
                    <div class="col-md-2">
                        <fmt:message key="client_page.jsp.button.edit" var="editButton"/>
                        <input type="button" id="editButton" value="${editButton}" class="btn btn-primary">
                    </div>

                    <!-- button for save changes -->
                    <div class="col-md-2">
                        <button class="btn btn-success" type="submit" id="saveButton" style="display: none">
                            <fmt:message key="client_page.jsp.button.save"/>
                        </button>
                    </div>

                    <!-- change password button -->
                    <div class="col-md-2">
                        <fmt:message key="client_page.jsp.button.change_password" var="changePass"/>
                        <input class="btn btn-secondary float-left" type="button" id="changePassButton"
                               value="${changePass}">
                    </div>
                </div>
            </form>
        </div>

        <!-- left column -->
        <div class="col-md-5">
            <!-- payment form -->
            <h3><fmt:message key="client_page.jsp.text.top_up_balance"/></h3>
            <form action="<c:url value="/controller"/>" method="post" onsubmit="return validateRechargeForm();">
                <input type="hidden" name="command" value="recharge">
                <div class="input-group col-md-12" style="padding: 5px">
                    <div class="input-group-prepend">
                        <fmt:message key="client_page.jsp.button.recharge" var="recharge"/>
                        <input class="btn btn-secondary" type="button" id="rechargeButton" value="${recharge}">
                    </div>
                    <label for="sum"></label>
                    <fmt:message key="client_page.jsp.placeholder.enter_sum" var="enterSum"/>
                    <input type="text" id="sum" class="form-control" placeholder="${enterSum}" name="sum" required
                           pattern="\d*">
                </div>

                <!-- payment container -->
                <div class="text-center col-md-12" id="paymentContainer" style="display: none; padding: 5px">
                    <div class="card text-center">
                        <div class="card-header">
                            <strong><fmt:message key="client_page.jsp.label.credit_card"/></strong>
                            <small><fmt:message key="client_page.jsp.label.enter_cc_details"/></small>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="ccname"><fmt:message key="client_page.jsp.label.cc_name"/></label>
                                        <fmt:message key="client_page.jsp.placeholder.enter_name" var="enterName"/>
                                        <input class="form-control" id="ccname" type="text"
                                               placeholder="${enterName}" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <fmt:message key="client_page.jsp.label.cc_number" var="cc_number"/>
                                        <label for="ccnumber"><c:out value="${cc_number}"/></label>
                                        <div class="input-group">
                                            <input class="form-control" id="ccnumber" type="text"
                                                   placeholder="0000 0000 0000 0000" autocomplete="email" required>
                                            <div class="input-group-append">
                                                <span class="input-group-text"><i class="far fa-credit-card"></i></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-sm-4">
                                    <label for="ccmonth"><fmt:message key="client_page.jsp.label.cc_month"/></label>
                                    <select class="form-control" id="ccmonth" required>
                                        <c:forEach begin="1" end="12" var="i">
                                            <option><c:out value="${i}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-sm-4">
                                    <label for="ccyear"><fmt:message key="client_page.jsp.label.cc_year"/></label>
                                    <select class="form-control" id="ccyear" required>
                                        <c:forEach begin="2022" end="2030" var="i">
                                            <option><c:out value="${i}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <label for="cvv"><c:out value="CVV/CVC"/></label>
                                        <input class="form-control" id="cvv" type="text" placeholder="123"
                                               pattern="\d*" maxlength="3" required>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <fmt:message key="client_page.jsp.button.send" var="send"/>
                            <button class="btn btn-sm btn-success float-right" type="submit">
                                <span class="far fa-check-circle"></span><c:out value="${send}"/>
                            </button>
                            <button class="btn btn-sm btn-danger float-left" id="resetButton"
                                    onclick="HTMLFormElement.reset()" type="reset">
                                <span class="fas fa-undo"></span><fmt:message key="client_page.jsp.button.reset"/>
                            </button>
                        </div>
                    </div>
                </div>
            </form>


            <!-- change password form -->
            <div id="changePassContainer" class="text-center col-md-12" style="padding: 5px; display: none">
                <form action="${pageContext.request.contextPath}/controller" method="post" onsubmit="return validateChangePassForm();">
                    <input name="page" type="hidden" value="/controller?command=viewClientPage">
                    <input name="command" type="hidden" value="changePassword">
                    <div class="card">
                        <div class="card-header">
                            <fmt:message key="client_page.jsp.text.change_pass_form"/>
                        </div>
                        <div class="card-body text-center">
                            <div class="row" style="padding: 5px">
                                <div class="col-md-12">
                                    <label for="pass"></label>
                                    <fmt:message key="client_page.jsp.placeholder.enter_old_pass" var="oldPass"/>
                                    <input id="pass" name="pass" type="password" placeholder="${oldPass}"
                                           class="form-control" required>
                                </div>
                            </div>
                            <div class="row" style="padding: 5px">
                                <div class="col-md-12">
                                    <label for="newPass"></label>
                                    <fmt:message key="client_page.jsp.placeholder.enter_new_pass" var="newPass"/>
                                    <input id="newPass" name="newPass" type="password" placeholder="${newPass}"
                                           class="form-control" required>
                                </div>
                            </div>
                            <div class="row" style="padding: 5px">
                                <div class="col-md-12">
                                    <label for="newPassConf"></label>
                                    <fmt:message key="client_page.jsp.placeholder.repeat_pass" var="repeatPass"/>
                                    <input id="newPassConf" name="newPassConf" type="password"
                                           placeholder="${repeatPass}" class="form-control" required>
                                </div>
                            </div>
                            <div class="row" style="padding-top: 5px">
                                <div class="col-md-3">
                                    <fmt:message key="client_page.jsp.button.save_changes" var="saveButt"/>
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
        const login = document.getElementById('login');
        const name = document.getElementById('name');
        const surname = document.getElementById('surname');
        const patronymic = document.getElementById('patronymic');
        const email = document.getElementById('email');
        const phone = document.getElementById('phone');
        <fmt:message key="client_page.jsp.button.cancel" var="cancel"/>

        if (editButton.value === '${editButton}') {
            login.readOnly = false;
            name.readOnly = false;
            surname.readOnly = false;
            patronymic.readOnly = false;
            email.readOnly = false;
            phone.readOnly = false;
            editButton.value = '${cancel}';
            editButton.className = 'btn btn-danger';
            saveButton.style.display = 'block';
        } else {
            document.location.reload();
        }
    }

    //fields edit validate function
    function validateEditForm() {
        const login = document.getElementById('login').value;
        const loginRegEx = /(\w{4,15})/;
        const nameRegEx = /(^[а-яєіїґА-ЯЄІЇҐ,.' -/\D]{2,}$)|(^[a-zA-Z,.' -/\D]{2,}$)/;
        const name = document.getElementById('name').value;
        const surname = document.getElementById('surname').value;
        const patronymic = document.getElementById('patronymic').value;
        const emailRegex = /[\w\-.]+@([\w-]+\.)+[a-z]{2,4}\b/;
        const email = document.getElementById('email').value;
        const phoneRegex = /(\+(380)[0-9]{9})\b/;
        const phone = document.getElementById('phone').value;
        <fmt:message key="client_page.jsp.message.valid_login" var="validLogin"/>
        <fmt:message key="client_page.jsp.message.valid_name" var="validName"/>
        <fmt:message key="client_page.jsp.message.valid_surname" var="validSurname"/>
        <fmt:message key="client_page.jsp.message.valid_patronymic" var="validPatronymic"/>
        <fmt:message key="client_page.jsp.message.valid_email" var="validEmail"/>
        <fmt:message key="client_page.jsp.message.valid_phone" var="validPhone"/>

        if (!loginRegEx.test(login)) {
            alert("${validLogin}");
            return false;

        } else if (!nameRegEx.test(name)) {
            alert("${validName}");
            return false;

        } else if (!nameRegEx.test(surname)) {
            alert("${validSurname}");
            return false;

        } else if (!nameRegEx.test(patronymic)) {
            alert("${validPatronymic}");
            return false;

        } else if (!emailRegex.test(email)) {
            alert("${validEmail}");
            return false;

        } else if (!phoneRegex.test(phone)) {
            alert("${validPhone}");
            return false;
        }
    }


    //recharge button function
    document.getElementById('rechargeButton').addEventListener("click", recharge);

    function recharge() {
        const paymentContainer = document.getElementById('paymentContainer');
        const rechargeButton = document.getElementById('rechargeButton');
        if (rechargeButton.value === '${recharge}') {
            paymentContainer.style.display = 'block';
            rechargeButton.value = '${cancel}';
        } else {
            paymentContainer.style.display = 'none';
            rechargeButton.value = '${recharge}';
        }
    }

    //recharge validate fields
    function validateRechargeForm() {
        const ccnumber = document.getElementById('ccnumber').value;
        const ccname = document.getElementById('ccname').value;
        const ccnameRegEx = /([а-яєіїґА-ЯЄІЇҐ,.' -]{2,})|([a-zA-Z,.' -]{2,})/;
        const ccNumRegEx = /\b(5[1-5][0-9]{14})\b|\b(4[0-9]{12}[0-9]{3})\b/;
        <fmt:message key="client_page.jsp.message.enter_valid_cc_name" var="validName"/>
        <fmt:message key="client_page.jsp.message.enter_valid_cc_numb" var="validNumber"/>

        if (!ccNumRegEx.test(ccnumber)) {
            alert("${validNumber}");
            return false;
        } else if (!ccnameRegEx.test(ccname)) {
            alert("${validName}");
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
        <fmt:message key="client_page.jsp.message.valid_pass" var="validPass"/>
        <fmt:message key="client_page.jsp.message.different_pass" var="differentPass"/>
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
