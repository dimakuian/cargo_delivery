function loginButton() {
    const dir = document.getElementById(`log`);
    const button = document.getElementById("button1");
    if (dir.style.display === "none" && button.value === "Login") {
        dir.style.display = "block";
        button.value = "Close";
    } else {
        dir.style.display = "none";
        button.value = "Login";
    }
}

// function confirmPassword() {
//     var pass = document.getElementById('password').value;
//     var confirm = document.getElementById('confirm_password').value;
//     if (pass != confirm) {
//         confirm.setCustomValidity("Passwords Don't Match");
//     } else {
//         confirm.setCustomValidity('');
//     }

// }