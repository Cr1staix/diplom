const jwt = localStorage.getItem("jwt"); // Берем токен

const citySelect = document.getElementById("citySelect");
const clubSelect = document.getElementById("clubSelect");
const clubsDiv = document.getElementById("clubs");


async function loadCities() {
    try {
        const response = await fetch("http://localhost:8080/api/cities", {
            headers: { "Authorization": "Bearer " + jwt }
        });
        const cities = await response.json();

        cities.forEach(city => {
            const option = document.createElement("option");
            option.value = city.id;
            option.textContent = city.name;
            citySelect.appendChild(option);
        });
    } catch (err) {
        console.error("Ошибка при загрузке городов", err);
    }
}


citySelect.addEventListener("change", async () => {
    const cityId = citySelect.value;


    clubSelect.innerHTML = '<option value="">-- Выберите клуб --</option>';
    clubsDiv.innerHTML = '';

    if (!cityId) return;

    try {
        const response = await fetch(`http://localhost:8080/api/club/city/${cityId}`, {
            headers: { "Authorization": "Bearer " + jwt }
        });
        const data = await response.json();

        data.content.forEach(club => {

            const option = document.createElement("option");
            option.value = club.id;
            option.textContent = club.name;
            clubSelect.appendChild(option);


            const div = document.createElement("div");
            div.classList.add("club-item");
            div.textContent = `${club.name} — ${club.address}`;


            div.addEventListener("click", () => {

                window.location.href = `club.html?id=${club.id}`;
            });

            clubsDiv.appendChild(div);

        });
    } catch (err) {
        console.error("Ошибка при загрузке клубов", err);
    }
});

loadCities();

document.getElementById("backToProfileBtn").addEventListener("click", () => {
    window.location.href = "profile.html";
});
