const jwt = localStorage.getItem("jwt");
const userPhone = document.getElementById("userPhone");
const userDob = document.getElementById("userDob");
const userBalance = document.getElementById("userBalance");
const userName = document.getElementById("userName");
const depositBtn = document.getElementById("depositBtn");
const chooseClubBtn = document.getElementById("chooseClubBtn");
const depositForm = document.getElementById("depositForm");
const confirmDeposit = document.getElementById("confirmDeposit");
const depositAmount = document.getElementById("depositAmount");
const message = document.getElementById("message");


async function loadUser() {
    try {
        const response = await fetch("http://localhost:8080/api/me", {
            headers: { "Authorization": "Bearer " + jwt }
        });

        if (response.ok) {
            const user = await response.json();

            if (user.firstName || user.lastName) {
                userName.textContent = `${user.firstName ?? ""} ${user.lastName ?? ""}`.trim();
            } else {
                userName.textContent = "(имя не указано)";
            }

            userPhone.textContent = user.phone;
            userDob.textContent = user.dateOfBirth;
            userBalance.textContent = user.balance ?? 0;
        } else {
            message.textContent = "Ошибка загрузки профиля";
        }
    } catch (err) {
        console.error(err);
        message.textContent = "Ошибка сервера";
    }
}

depositBtn.addEventListener("click", () => {
    depositForm.style.display = depositForm.style.display === "none" ? "block" : "none";
});


confirmDeposit.addEventListener("click", async () => {
    const amount = depositAmount.value;
    if (!amount || amount <= 0) {
        alert("Введите сумму!");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/balance/deposit?amount=${amount}`, {
            method: "PATCH",
            headers: { "Authorization": "Bearer " + jwt }
        });

        if (response.ok) {
            const text = await response.text();
            alert(text);
            loadUser(); // обновляем баланс
            depositAmount.value = "";
            depositForm.style.display = "none";
        } else {
            alert("Ошибка пополнения");
        }
    } catch (err) {
        console.error(err);
        alert("Ошибка сервера");
    }
});


chooseClubBtn.addEventListener("click", () => {
    window.location.href = "city.html";
});

loadUser();



const editProfileToggleBtn = document.getElementById("editProfileToggleBtn");
const editProfileForm = document.getElementById("editProfileForm");
const editFirstName = document.getElementById("editFirstName");
const editLastName = document.getElementById("editLastName");
const editDateOfBirth = document.getElementById("editDateOfBirth");
const editPhone = document.getElementById("editPhone");
const saveProfileBtn = document.getElementById("saveProfileBtn");

editProfileToggleBtn.addEventListener("click", () => {
    editProfileForm.style.display = editProfileForm.style.display === "none" ? "block" : "none";

    editFirstName.value = user.firstName || "";
    editLastName.value = user.lastName || "";
    editDateOfBirth.value = user.dateOfBirth || "";
    editPhone.value = user.phone || "";
});

saveProfileBtn.addEventListener("click", async () => {
    const dto = {
        firstName: editFirstName.value.trim() || null,
        lastName: editLastName.value.trim() || null,
        dateOfBirth: editDateOfBirth.value || null,
        phone: editPhone.value.trim() || null
    };

    try {
        const response = await fetch("http://localhost:8080/api/me", {
            method: "PUT",
            headers: {
                "Authorization": "Bearer " + jwt,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dto)
        });

        if (response.ok) {
            const updatedUser = await response.json();
            alert("Профиль успешно обновлён!");
            user = updatedUser;
            loadUser();
            editProfileForm.style.display = "none";
        } else {
            const err = await response.text();
            alert("Ошибка обновления: " + err);
        }
    } catch (err) {
        console.error(err);
        alert("Ошибка сервера");
    }
});

const logoutBtn = document.getElementById("logoutBtn");

logoutBtn.addEventListener("click", () => {
    localStorage.removeItem("jwt");
    window.location.href = "index.html";
});
