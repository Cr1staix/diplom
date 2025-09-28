const loginTab = document.getElementById("loginTab");
const registerTab = document.getElementById("registerTab");
const loginForm = document.getElementById("loginForm");
const registerForm = document.getElementById("registerForm");
const message = document.getElementById("message");

// Переключение вкладок
loginTab.addEventListener("click", () => {
    loginForm.classList.add("active");
    registerForm.classList.remove("active");
    loginTab.classList.add("active");
    registerTab.classList.remove("active");
});

registerTab.addEventListener("click", () => {
    loginForm.classList.remove("active");
    registerForm.classList.add("active");
    loginTab.classList.remove("active");
    registerTab.classList.add("active");
});

async function getUserRole() {
    try {
        const jwt = localStorage.getItem("jwt");
        const res = await fetch("http://localhost:8080/api/me", {
            headers: {
                "Authorization": "Bearer " + jwt
            }
        });
        if (!res.ok) throw new Error("Ошибка получения пользователя");
        const user = await res.json();

        if (user.role === "ADMIN") {
            window.location.href = "admin.html";
        } else {
            window.location.href = "profile.html";
        }
    } catch (err) {
        console.error(err);
        window.location.href = "profile.html";
    }
}


loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const phone = document.getElementById("loginPhone").value;
    const password = document.getElementById("loginPassword").value;

    try {
        const response = await fetch("http://localhost:8080/auth/sign-in", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({ phone, password })
        });

        if (response.ok) {
            const data = await response.json();
            message.style.color = "green";
            localStorage.setItem("jwt", data.token);
            await getUserRole();
        } else {
            message.style.color = "red";
            message.textContent = "Ошибка входа";
        }
    } catch (err) {
        message.textContent = "Ошибка сервера";
    }
});


registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const phone = document.getElementById("registerPhone").value;
    const password = document.getElementById("registerPassword").value;
    const dateOfBirth = document.getElementById("registerDob").value;

    try {
        const response = await fetch("http://localhost:8080/auth/sign-up", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({ phone, password, dateOfBirth })
        });

        if (response.ok) {
            const data = await response.json();
            message.style.color = "green";
            localStorage.setItem("jwt", data.token);
            window.location.href = "profile.html";
        } else {
            message.style.color = "red";
            message.textContent = "Ошибка регистрации";
        }
    } catch (err) {
        message.textContent = "Ошибка сервера";
    }
});