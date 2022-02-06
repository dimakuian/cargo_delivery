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

function countVolume() {
    var l = parseFloat(document.getElementById('length').value);
    var h = parseFloat(document.getElementById('height').value);
    var w = parseFloat(document.getElementById('width').value);
    var value = (l * h * w).toFixed(2);
    document.getElementById("vol").value=value;
}