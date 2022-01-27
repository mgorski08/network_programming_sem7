package cf.mgorski.networkprogramming.task2.data;

public interface WeatherData {
    Float getWindDirection();

    void setWindDirection(Float windDirection);

    Float getWindSpeed();

    void setWindSpeed(Float windSpeed);

    Float getRelativeHumidity();

    void setRelativeHumidity(Float relativeHumidity);

    Float getPressureHPa();

    void setPressureHPa(Float pressureHPa);

    Float getTemperatureC();

    void setTemperatureC(Float temperatureC);
}
