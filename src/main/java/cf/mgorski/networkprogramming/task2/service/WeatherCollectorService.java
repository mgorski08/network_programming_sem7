package cf.mgorski.networkprogramming.task2.service;

import cf.mgorski.networkprogramming.task2.message.OpenWeatherResponse;
import cf.mgorski.networkprogramming.task2.model.City;
import cf.mgorski.networkprogramming.task2.model.SampledWeatherData;
import cf.mgorski.networkprogramming.task2.repository.CityRepository;
import cf.mgorski.networkprogramming.task2.repository.WeatherDataRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class WeatherCollectorService {
    private final Log logger = LogFactory.getLog(this.getClass());
    WebClient client = WebClient.create();
    @Autowired
    CityRepository cityRepository;
    @Autowired
    WeatherDataRepository weatherDataRepository;
    @Value("${weather.apikey}")
    private String apikey;

    public SampledWeatherData getCurrentWeather(City city) {
        SampledWeatherData weatherData = new SampledWeatherData();
        ResponseSpec responseSpec = this.client.get().uri("http://api.openweathermap.org/data/2.5/weather?id=" + city.getOpenWeatherId() + "&appid=" + apikey + "&units=metric", new Object[0]).retrieve();
        OpenWeatherResponse response = responseSpec.bodyToMono(OpenWeatherResponse.class).block();
        weatherData.setTimestamp(Timestamp.from(Instant.now()));
        weatherData.setCity(city);
        weatherData.setTemperatureC(response.getMain().getTemp());
        weatherData.setPressureHPa(response.getMain().getPressure());
        weatherData.setRelativeHumidity(response.getMain().getHumidity());
        weatherData.setWindSpeed(response.getWind().getSpeed());
        weatherData.setWindDirection(response.getWind().getDeg());
        return weatherData;
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    void collectWeatherData() {
        for (City city : this.cityRepository.findAll()) {
            SampledWeatherData weatherData = this.getCurrentWeather(city);
            this.weatherDataRepository.save(weatherData);
            this.logger.info("Weather data collected for: " + city.getName());

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }

    }
}
