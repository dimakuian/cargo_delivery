
function validform(lang) {
    var login = document.forms["my-form"]["login"].value;
    var loginRegex = /(\w{4,15})/;
    var password = document.forms["my-form"]["password"].value;
    var passwordRegex = /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}/;
    var confirm_password = document.forms["my-form"]["confirm_password"].value;
    var nameRegex = /[a-zA-Zа-яА-Я]{2,}\b/;
    var name = document.forms["my-form"]["name"].value;
    var surname = document.forms["my-form"]["surname"].value;
    var patronymic = document.forms["my-form"]["patronymic"].value;
    var emailRegex = /[\w\-\.]+@([\w-]+\.)+[a-z]{2,4}\b/;
    var email = document.forms["my-form"]["email"].value;
    var phoneRegex = /(\+{1}(380){1}[0-9]{9}){1}\b/;
    var phone = document.forms["my-form"]["phone"].value;

    //check login
    if (login == null || login == "") {
        if (lang == "ua") {
            alert("Логін не повинен бути порожній.");
        } else if (lang == "en") {
            alert("Login should not be empty.");
        } else {
            alert("Логін не повинен бути порожній.");
        }
        return false;

    } else if (!loginRegex.test(login)) {
        if (lang == "ua") {
            alert("Логін повинен містити тільки англійські літери, принаймі 4.");
        } else if (lang == "en") {
            alert("The login must contain only English letters, at least 4.");
        } else {
            alert("Логін повинен містити тільки англійські літери, принаймі 4.");
        }
        return false;
    }

    //check password
    else if (password != confirm_password) {
        if (lang == "ua") {
            alert("Паролі не співпадають.");
        } else if (lang == "en") {
            alert("Passwords do not match.");
        } else {
            alert("Паролі не співпадають.");
        }
        return false;

    } else if (!passwordRegex.test(password)) {
        if (lang == "ua") {
            alert("Пароль повинен складатись з мінімум 8 літер, 1 цифри та 1 спеціальний знак.");
        } else if (lang == "en") {
            alert("The password must consist of at least 8 letters, 1 digit and 1 special character.");
        } else {
            alert("Пароль повинен складатись з мінімум 8 літер, 1 цифру та 1 спеціального знаку.");
        }
        return false;
    }

    //check name
    else if (name == null || name == "") {
        if (lang == "ua") {
            alert("Ім'я не повинено бути порожнє.");
        } else if (lang == "en") {
            alert("Name should not be empty.");
        } else {
            alert("Ім'я не повинено бути порожнє.");
        }
        return false;
    } else if (!nameRegex.test(name)) {
        if (lang == "ua") {
            alert("Не правильно введене ім'я.");
        } else if (lang == "en") {
            alert("Invalid name entered.");
        } else {
            alert("Не правильно введене ім'я.");
        }
        return false;
    }

    //check surname
    else if (surname == null || surname == "") {
        if (lang == "ua") {
            alert("Прізвище не повинено бути порожнє.");
        } else if (lang == "en") {
            alert("Surname should not be empty.");
        } else {
            alert("Прізвище не повинено бути порожнє.");
        }
        return false;
    } else if (!nameRegex.test(surname)) {
        if (lang == "ua") {
            alert("Не правильно введене прізвище.");
        } else if (lang == "en") {
            alert("Invalid surname entered.");
        } else {
            alert("Не правильно введене прізвище.");
        }
        return false;
    }

    //check patronymic
    else if (patronymic == null || patronymic == "") {
        if (lang == "ua") {
            alert("По-батькові не повинено бути порожнє.");
        } else if (lang == "en") {
            alert("Patronymic should not be empty.");
        } else {
            alert("По-батькові не повинено бути порожнє.");
        }
        return false;
    } else if (!nameRegex.test(patronymic)) {
        if (lang == "ua") {
            alert("Не правильно введене по-батькові.");
        } else if (lang == "en") {
            alert("Invalid patronymic entered.");
        } else {
            alert("Не правильно введене по-батькові.");
        }
        return false;
    }

    //check email
    else if (email == null || email == "") {
        if (lang == "ua") {
            alert("Електронна пошта не повинено бути порожня.");
        } else if (lang == "en") {
            alert("Email should not be empty.");
        } else {
            alert("Електронна пошта не повинено бути порожня.");
        }
        return false;
    } else if (!emailRegex.test(email)) {
        if (lang == "ua") {
            alert("Не правильно введене електронна пошта, зразок : mail@example.com.");
        } else if (lang == "en") {
            alert("E-mail entered incorrectly, sample: mail@example.com.");
        } else {
            alert("Не правильно введене електронна пошта, зразок : mail@example.com.");
        }
        return false;
    }

    //check phone
    else if (phone == null || phone == "") {
        if (lang == "ua") {
            alert("Номер телефону не повинен бути порожній.");
        } else if (lang == "en") {
            alert("Phone number should not be empty.");
        } else {
            alert("Номер телефону не повинен бути порожній.");
        }
        return false;
    } else if (!phoneRegex.test(phone)) {
        if (lang == "ua") {
            alert("Не правильно введений номер телефону, зразок : +380671234567.");
        } else if (lang == "en") {
            alert("Phone number entered incorrectly, sample: +380671234567.");
        } else {
            alert("Не правильно введений номер телефону, зразок : +380671234567.");
        }
        return false;
    }
}

