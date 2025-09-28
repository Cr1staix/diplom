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


        if (pc.isActive) {
            pcDiv.classList.add("busy");
        }

        pcDiv.addEventListener("click", () => {
            document.querySelectorAll(".computer").forEach(c => c.classList.remove("selected"));
            pcDiv.classList.add("selected");

            if (pc.computerSpecificationDTO) {
                showComputerSpec(pc);
            } else {
                fetch(`http://localhost:8080/api/pc/${pc.id}`, {
                    method: "GET",
                    headers: {
                        "Authorization": "Bearer " + jwt,
                        "Content-Type": "application/json"
                    }
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


    let content = `
        <h3>Компьютер ${pc.name}</h3>
        <p>CPU: ${spec.cpu}</p>
        <p>GPU: ${spec.gpu}</p>
        <p>RAM: ${spec.ram}</p>
        <p>Монитор: ${spec.monitor}</p>
        <p>Клавиатура: ${spec.keyboard}</p>
        <p>Мышь: ${spec.mouse}</p>
        <p>Наушники: ${spec.headphones}</p>
           <h3>Компьютер ${pc.name}</h3>
    <p><b>Цена за час:</b> ${pc.pricePerHour} ₽</p>
    
   
    `;


    if (pc.isActive) {
        fetch(`http://localhost:8080/api/reservation/active/${pc.id}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + jwt,
                "Content-Type": "application/json"
            }
        })
            .then(res => res.status === 204 ? null : res.json())
            .then(activeReservation => {
                if (activeReservation && activeReservation.endTime) {
                    const endTime = new Date(activeReservation.endTime.replace(" ", "T"));
                    const formatted = endTime.toLocaleString("ru-RU", {
                        hour: "2-digit",
                        minute: "2-digit",
                        day: "2-digit",
                        month: "2-digit"
                    });
                    content += `<p style="color:red;">Занят до: ${formatted}</p>`;
                } else {
                    content += `<p style="color:red;">Занят до: неизвестно</p>`;
                }
                computerInfo.innerHTML = content;
            })
            .catch(err => {
                console.error(err);
                content += `<p style="color:red;">Занят до: неизвестно</p>`;
                computerInfo.innerHTML = content;
            });
    } else {
        content += `
            <label>Начало: <input type="time" id="startTime"></label><br>
            <label>Длительность (часы): 
                <select id="duration">
                    <option value="1">1 час</option>
                    <option value="2">2 часа</option>
                    <option value="3">3 часа</option>
                    <option value="4">4 часа</option>
                    <option value="5">5 часов</option>
                    <option value="6">6 часов</option>
                    <option value="7">7 часов</option>
                    <option value="8">8 часов</option>
                    <option value="9">9 часов</option>
                    <option value="10">10 часов</option>
                    <option value="11">11 часов</option>
                    <option value="12">12 часов</option>
                </select>
            </label><br>
            <button id="reserveBtn">Забронировать</button>
        `;
        computerInfo.innerHTML = content;

        const reserveBtn = document.getElementById("reserveBtn");
        if (reserveBtn) {
            reserveBtn.addEventListener("click", () => {
                reserveComputer(pc.id);
            });
        }
    }
}

function reserveComputer(computerId) {
    const startTimeInput = document.getElementById("startTime").value;
    const duration = parseInt(document.getElementById("duration").value, 10);

    if (!startTimeInput) {
        alert("Выбери время начала!");
        return;
    }

    const today = new Date();
    const [hours, minutes] = startTimeInput.split(":");
    today.setHours(hours, minutes, 0, 0);

    const start = today;
    const end = new Date(start.getTime() + duration * 60 * 60 * 1000);

    function formatDate(date) {
        const pad = n => n < 10 ? "0" + n : n;
        return date.getFullYear() + "-" +
            pad(date.getMonth() + 1) + "-" +
            pad(date.getDate()) + " " +
            pad(date.getHours()) + ":" +
            pad(date.getMinutes());
    }

    fetch("http://localhost:8080/api/reservation", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + jwt,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            computerId: computerId,
            startTime: formatDate(start),
            endTime: formatDate(end)
        })
    })
        .then(res => {
            if (res.ok) return res.json();
            return res.json().then(err => {
                throw new Error(err.message);
            });
        })
        .then(reservation => {
            alert(`ПК успешно забронирован с ${reservation.startTime} до ${reservation.endTime}`);
            location.reload();
        })
        .catch(err => {
            console.error(err);
            alert(err.message);
        });
}

    backBtn.addEventListener("click", () => {
        window.location.href = "city.html";
    });

function refreshClub() {
    if (!clubId) return;
    fetch(`http://localhost:8080/api/club/${clubId}`, {
        headers: { "Authorization": "Bearer " + jwt }
    })
        .then(res => res.ok ? res.json() : Promise.reject("Ошибка " + res.status))
        .then(club => showClub(club))
        .catch(err => console.error("Ошибка обновления клуба:", err));
}

setInterval(refreshClub, 30000);
