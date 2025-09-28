const jwt = localStorage.getItem("jwt");
let currentPage = 0; // текущая страница
let lastPage = 0;    // всего страниц (прилетает с бэка)

// ====== Поиск пользователей ======
function loadUsers(page = 0) {
    const firstName = document.getElementById("searchName").value;
    const lastName = document.getElementById("searchLastName")?.value || ""; // если добавим отдельное поле для фамилии

    fetch(`http://localhost:8080/api/user?firstName=${firstName}&lastName=${lastName}&page=${page}&size=5`, {
        headers: { "Authorization": "Bearer " + jwt }
    })
        .then(res => res.json())
        .then(data => {
            const users = data.content;
            currentPage = data.number;
            lastPage = data.totalPages;

            let html = "<ul>";
            users.forEach(u => {
                html += `<li>${u.firstName} ${u.lastName} (${u.phone})</li>`;
            });
            html += "</ul>";

            // пагинация
            html += `<div class="pagination">`;
            if (!data.first) {
                html += `<button onclick="loadUsers(${currentPage - 1})">⬅ Назад</button>`;
            }
            if (!data.last) {
                html += `<button onclick="loadUsers(${currentPage + 1})">Вперёд ➡</button>`;
            }
            html += `</div>`;

            document.getElementById("userResults").innerHTML = html;
        });
}

document.getElementById("searchBtn").addEventListener("click", () => {
    loadUsers(0); // при новом поиске всегда с первой страницы
});

// ====== Создание клуба ======
function loadCities() {
    fetch("http://localhost:8080/api/cities", {
        headers: { "Authorization": "Bearer " + jwt }
    })
        .then(res => res.json())
        .then(cities => {
            const select = document.getElementById("citySelect");
            select.innerHTML = "";
            cities.forEach(c => {
                const option = document.createElement("option");
                option.value = c.id;
                option.textContent = c.name;
                select.appendChild(option);
            });
        });
}
loadCities();

document.getElementById("createClubBtn").addEventListener("click", () => {
    const clubName = document.getElementById("clubName").value;
    const cityId = document.getElementById("citySelect").value;
    const address = document.getElementById("clubAddress").value;

    fetch("http://localhost:8080/api/club", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + jwt,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ name: clubName, city_id: cityId, address })
    }).then(res => res.json())
        .then(club => alert("Клуб создан: " + club.name));
});

// ====== Создание компьютера ======
function loadSpecs() {
    fetch("http://localhost:8080/api/pc_spec", {
        headers: { "Authorization": "Bearer " + jwt }
    })
        .then(res => res.json())
        .then(page => {
            const select = document.getElementById("specSelect");
            select.innerHTML = "";
            page.content.forEach(s => {
                const option = document.createElement("option");
                option.value = s.id;
                option.textContent = `${s.cpu} / ${s.gpu} / ${s.ram}`;
                select.appendChild(option);
            });
        });
}
loadSpecs();

// переключение между режимами
document.querySelectorAll("input[name='specMode']").forEach(radio => {
    radio.addEventListener("change", () => {
        const newSpecFields = document.getElementById("newSpecFields");
        if (document.querySelector("input[name='specMode']:checked").value === "new") {
            newSpecFields.style.display = "block";
        } else {
            newSpecFields.style.display = "none";
        }
    });
});

document.getElementById("createPcBtn").addEventListener("click", () => {
    const pcName = document.getElementById("pcName").value;
    const specMode = document.querySelector("input[name='specMode']:checked").value;

    if (specMode === "existing") {
        // вариант 1 — выбираем готовую спецификацию
        const specId = document.getElementById("specSelect").value;

        fetch("http://localhost:8080/api/pc", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + jwt,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name: pcName, specId })
        })
            .then(res => res.json())
            .then(pc => alert("Компьютер создан: " + pc.name));

    } else {
        // вариант 2 — создаём новую спецификацию
        const cpu = document.getElementById("cpu").value;
        const gpu = document.getElementById("gpu").value;
        const ram = document.getElementById("ram").value;

        fetch("http://localhost:8080/api/pc_spec", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + jwt,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ cpu, gpu, ram })
        })
            .then(res => res.json())
            .then(spec => {
                return fetch("http://localhost:8080/api/pc", {
                    method: "POST",
                    headers: {
                        "Authorization": "Bearer " + jwt,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ name: pcName, specId: spec.id })
                });
            })
            .then(res => res.json())
            .then(pc => alert("Компьютер создан: " + pc.name));
    }
});

document.getElementById("createPcBtn").addEventListener("click", () => {
    const pcName = document.getElementById("pcName").value;
    const specId = document.getElementById("specSelect").value;

    fetch("http://localhost:8080/api/pc", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + jwt,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ name: pcName, specId })
    }).then(res => res.json())
        .then(pc => alert("Компьютер создан: " + pc.name));
});
