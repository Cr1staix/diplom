const jwt = localStorage.getItem("jwt");

const clubNameEl = document.getElementById("clubName");
const clubMap = document.getElementById("clubMap");
const computerInfo = document.getElementById("computerInfo");
const backBtn = document.getElementById("backBtn");

const urlParams = new URLSearchParams(window.location.search);
const clubId = urlParams.get("id");

if (clubId) {
    fetch(`http://localhost:8080/api/club/${clubId}`, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + jwt,
            "Content-Type": "application/json"
        },
        credentials: "include"
    })
        .then(res => res.ok ? res.json() : Promise.reject("Ошибка " + res.status))
        .then(club => {
            console.log("Ответ клуба:", club);
            showClub(club);
        })
        .catch(err => {
            console.error(err);
            alert("Ошибка загрузки клуба. Проверь консоль.");
        });
}

function showClub(club) {
    clubNameEl.textContent = club.name || "Без имени";

    clubMap.innerHTML = "";
    computerInfo.innerHTML = "";

    if (!club.computers || club.computers.length === 0) {
        clubMap.innerHTML = "<p>Компьютеры не найдены</p>";
        return;
    }

    club.computers.forEach(pc => {
        const pcDiv = document.createElement("div");
        pcDiv.classList.add("computer");
        pcDiv.textContent = pc.name;

        pcDiv.addEventListener("click", () => {
            document.querySelectorAll(".computer").forEach(c => c.classList.remove("selected"));
            pcDiv.classList.add("selected");

            // Если DTO уже есть, показываем сразу
            if (pc.computerSpecificationDTO) {
                showComputerSpec(pc);
            } else {
                // Иначе делаем fetch на конкретный ПК
                fetch(`http://localhost:8080/api/pc/${pc.id}`, {
                    method: "GET",
                    headers: {
                        "Authorization": "Bearer " + jwt,
                        "Content-Type": "application/json"
                    },
                    credentials: "include"
                })
                    .then(res => res.ok ? res.json() : Promise.reject("Ошибка " + res.status))
                    .then(fullPc => showComputerSpec(fullPc))
                    .catch(err => {
                        console.error(err);
                        alert("Ошибка загрузки характеристик ПК");
                    });
            }
        });

        clubMap.appendChild(pcDiv);
    });
}

function showComputerSpec(pc) {
    const spec = pc.computerSpecificationDTO;
    if (!spec) {
        computerInfo.innerHTML = "<p>Характеристики отсутствуют</p>";
        return;
    }

    computerInfo.innerHTML = `
        <h3>Компьютер ${pc.name}</h3>
        <p>CPU: ${spec.cpu}</p>
        <p>GPU: ${spec.gpu}</p>
        <p>RAM: ${spec.ram}</p>
        <p>Монитор: ${spec.monitor}</p>
        <p>Клавиатура: ${spec.keyboard}</p>
        <p>Мышь: ${spec.mouse}</p>
        <p>Наушники: ${spec.headphones}</p>
    `;
}

backBtn.addEventListener("click", () => {
    window.location.href = "city.html";
});