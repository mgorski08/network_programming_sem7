package cf.mgorski.networkprogramming.task2.controller;

import cf.mgorski.networkprogramming.task2.data.WeatherData;
import cf.mgorski.networkprogramming.task2.helper.Functional;
import cf.mgorski.networkprogramming.task2.message.DBsize;
import cf.mgorski.networkprogramming.task2.model.City;
import cf.mgorski.networkprogramming.task2.model.SampledWeatherData;
import cf.mgorski.networkprogramming.task2.repository.CityRepository;
import cf.mgorski.networkprogramming.task2.repository.WeatherDataRepository;
import cf.mgorski.networkprogramming.task2.service.WeatherCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
public class HomeController {

    @Autowired
    WeatherCalculator weatherCalculator;

    @Autowired
    WeatherDataRepository weatherDataRepository;

    @Autowired
    CityRepository cityRepository;

    @GetMapping("/dbsize")
    Object dbsize() {
        SampledWeatherData first = weatherDataRepository.findFirstByOrderByTimestampAsc().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        SampledWeatherData last = weatherDataRepository.findFirstByOrderByTimestampDesc().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        double days = Duration.between(first.getTimestamp().toInstant(), last.getTimestamp().toInstant()).get(ChronoUnit.SECONDS)/86400.0;
        long samples = weatherDataRepository.count();
        return new DBsize(days, samples);
    }

    @GetMapping("/cities")
    Iterable<City> cities() {
        return cityRepository.findAll();
    }

    @GetMapping("/averageDays")
    WeatherData averageDays(@RequestParam long cityId, @RequestParam long days) {
        Timestamp daysAgo = Timestamp.from(Instant.now().minus(3, ChronoUnit.DAYS));
        City city = cityRepository.findById(cityId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        Iterable<SampledWeatherData> weatherData = weatherDataRepository.findByCityAndTimestampAfter(city, daysAgo);
        return weatherCalculator.average(weatherData);
    }

    @GetMapping("/polandAverageDays")
    WeatherData polandAverageDays(@RequestParam long days) {
        Timestamp daysAgo = Timestamp.from(Instant.now().minus(3, ChronoUnit.DAYS));
        Iterable<SampledWeatherData> weatherData = weatherDataRepository.findByTimestampAfter(daysAgo);
        return weatherCalculator.average(weatherData);
    }

    @GetMapping("/latest")
    WeatherData latest(@RequestParam long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return weatherDataRepository.findFirstByCityOrderByTimestampDesc(city).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/latestPoland")
    WeatherData latestPoland() {
        Iterable<City> cities = cityRepository.findAll();
        Iterable<Optional<SampledWeatherData>> optionals = Functional.map(cities, weatherDataRepository::findFirstByCityOrderByTimestampDesc);
        Iterable<SampledWeatherData> weatherDataIterable = Functional.map(optionals, (Optional<SampledWeatherData> o)->o.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return weatherCalculator.average(weatherDataIterable);
    }
}
