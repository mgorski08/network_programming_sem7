package cf.mgorski.networkprogramming.task2.message;

import cf.mgorski.networkprogramming.task2.data.WeatherData;

public class ComputedWeatherData implements WeatherData {
    private Float temperatureC;
    private Float pressureHPa;
    private Float relativeHumidity;
    private Float windSpeed;
    private Float windDirection;

    public ComputedWeatherData() {
    }

    public Float getWindDirection() {
        return this.windDirection;
    }

    public void setWindDirection(Float windDirection) {
        this.windDirection = windDirection;
    }

    public Float getWindSpeed() {
        return this.windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Float getRelativeHumidity() {
        return this.relativeHumidity;
    }

    public void setRelativeHumidity(Float relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public Float getPressureHPa() {
        return this.pressureHPa;
    }

    public void setPressureHPa(Float pressureHPa) {
        this.pressureHPa = pressureHPa;
    }

    public Float getTemperatureC() {
        return this.temperatureC;
    }

    public void setTemperatureC(Float temperatureC) {
        this.temperatureC = temperatureC;
    }
}
