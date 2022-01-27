let table = document.getElementById("mainTable")
let modeSelector = document.getElementById("modeSelector")
let days = document.getElementById("days")
let samples = document.getElementById("samples")

const loadLatestCityData = (cityId, updateFunc) => {
    fetch("/latest?cityId=" + cityId).then(x => x.json()).then(updateFunc)
}

const loadLatestPolandData = (updateFunc) => {
    fetch("/latestPoland").then(x => x.json()).then(updateFunc)
}

const loadAverageCityData = (cityId, updateFunc, days) => {
    fetch("/averageDays?cityId=" + cityId + "&days=" + days).then(x => x.json()).then(updateFunc)
}

const loadAveragePolandData = (updateFunc, days) => {
    fetch("/polandAverageDays?days=" + days).then(x => x.json()).then(updateFunc)
}

const loadDataSize = () => {
    fetch("/dbsize").then(x => x.json()).then((data) => {
        days.innerText = data.days.toFixed(3)
        samples.innerText = data.samples
    })
}

const createTable = (table, cols, rows) => {
    for (let i = 0; i < rows; ++i) {
        row = table.insertRow()
        for (let j = 0; j < cols; ++j) {
            row.insertCell()
        }
    }
}

const fillTable = (cities, city, poland) => {
    table.rows[1].cells[0].innerText = "Temperature [C]"
    table.rows[2].cells[0].innerText = "Pressure [hPa]"
    table.rows[3].cells[0].innerText = "Humidity [%]"
    table.rows[4].cells[0].innerText = "Wind speed [m/s]"
    table.rows[5].cells[0].innerText = "Wind direction [deg]"

    table.rows[1].cells[0].classList.add("header")
    table.rows[2].cells[0].classList.add("header")
    table.rows[3].cells[0].classList.add("header")
    table.rows[4].cells[0].classList.add("header")
    table.rows[5].cells[0].classList.add("header")
    for (let i = 0; i < cities.length; ++i) {
        city(cities[i].id, (weatherData) => {
            table.rows[0].cells[i + 1].innerText = cities[i].name
            table.rows[0].cells[i + 1].classList.add("header")
            table.rows[1].cells[i + 1].innerText = weatherData.temperatureC.toFixed(1)
            table.rows[2].cells[i + 1].innerText = weatherData.pressureHPa.toFixed(1)
            table.rows[3].cells[i + 1].innerText = weatherData.relativeHumidity.toFixed(1)
            table.rows[4].cells[i + 1].innerText = weatherData.windSpeed.toFixed(1)
            table.rows[5].cells[i + 1].innerText = weatherData.windDirection.toFixed(1)
        });
    }
    table.rows[0].cells[cities.length + 1].innerText = "Poland"
    table.rows[0].cells[cities.length + 1].classList.add("header")
    poland((weatherData) => {
        table.rows[1].cells[cities.length + 1].innerText = weatherData.temperatureC.toFixed(1)
        table.rows[2].cells[cities.length + 1].innerText = weatherData.pressureHPa.toFixed(1)
        table.rows[3].cells[cities.length + 1].innerText = weatherData.relativeHumidity.toFixed(1)
        table.rows[4].cells[cities.length + 1].innerText = weatherData.windSpeed.toFixed(1)
        table.rows[5].cells[cities.length + 1].innerText = weatherData.windDirection.toFixed(1)
    });
}

const onModeChange = () => {
    let city
    let poland
    switch (modeSelector.value) {
        case 'latest':
            city = loadLatestCityData
            poland = loadLatestPolandData
            break
        default:
            city = (...args) => loadAverageCityData(...args, Number(modeSelector.value))
            poland = (...args) => loadAveragePolandData(...args, Number(modeSelector.value))
            break
    }
    loadDataSize()
    fetch("/cities").then((x) => x.json()).then((cities) => {
        console.log(cities)
        fillTable(cities, city, poland)
    })
}

loadDataSize()
fetch("/cities").then((x) => x.json()).then((cities) => {
    console.log(cities)
    createTable(table, cities.length + 2, 6)
})