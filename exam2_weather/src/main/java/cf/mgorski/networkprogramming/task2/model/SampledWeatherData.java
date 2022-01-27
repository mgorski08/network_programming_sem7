package cf.mgorski.networkprogramming.task2.model;

import cf.mgorski.networkprogramming.task2.data.WeatherData;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class SampledWeatherData implements WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "temperature_c", nullable = false)
    private Float temperatureC;

    @Column(name = "pressure_h_pa")
    private Float pressureHPa;

    @Column(name = "relative_humidity")
    private Float relativeHumidity;

    @Column(name = "wind_speed")
    private Float windSpeed;

    @Column(name = "wind_direction")
    private Float windDirection;

    public Float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Float windDirection) {
        this.windDirection = windDirection;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Float getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(Float relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public Float getPressureHPa() {
        return pressureHPa;
    }

    public void setPressureHPa(Float pressureHPa) {
        this.pressureHPa = pressureHPa;
    }

    public Float getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(Float temperatureC) {
        this.temperatureC = temperatureC;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
