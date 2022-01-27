package cf.mgorski.networkprogramming.task2.service;

import cf.mgorski.networkprogramming.task2.message.ComputedWeatherData;
import cf.mgorski.networkprogramming.task2.model.SampledWeatherData;
import org.springframework.stereotype.Service;

@Service
public class WeatherCalculator {
    public WeatherCalculator() {
    }

    public ComputedWeatherData average(Iterable<SampledWeatherData> weatherDataIterable) {
        float rh = 0.0F;
        float pressure = 0.0F;
        float temperature = 0.0F;
        float windspeed = 0.0F;
        float winddir = 0.0F;
        int length = 0;

        for (SampledWeatherData sampledWeatherData : weatherDataIterable) {
            ++length;
            rh += sampledWeatherData.getRelativeHumidity();
            pressure += sampledWeatherData.getPressureHPa();
            temperature += sampledWeatherData.getTemperatureC();
            windspeed += sampledWeatherData.getWindSpeed();
            winddir += sampledWeatherData.getWindDirection();
        }

        if (length == 0) {
            return null;
        } else {
            rh /= (float)length;
            pressure /= (float)length;
            temperature /= (float)length;
            windspeed /= (float)length;
            winddir /= (float)length;
            ComputedWeatherData computedWeatherData = new ComputedWeatherData();
            computedWeatherData.setRelativeHumidity(rh);
            computedWeatherData.setPressureHPa(pressure);
            computedWeatherData.setTemperatureC(temperature);
            computedWeatherData.setWindSpeed(windspeed);
            computedWeatherData.setWindDirection(winddir);
            return computedWeatherData;
        }
    }
}
