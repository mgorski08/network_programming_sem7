package cf.mgorski.networkprogramming.task2.message;

public class OpenWeatherResponse {
    OpenWeatherResponse.Main main;
    OpenWeatherResponse.Wind wind;

    public OpenWeatherResponse() {
    }

    public OpenWeatherResponse.Main getMain() {
        return this.main;
    }

    public void setMain(OpenWeatherResponse.Main main) {
        this.main = main;
    }

    public OpenWeatherResponse.Wind getWind() {
        return this.wind;
    }

    public void setWind(OpenWeatherResponse.Wind wind) {
        this.wind = wind;
    }

    public class Main {
        float temp;
        float pressure;
        float humidity;

        public Main() {
        }

        public float getTemp() {
            return this.temp;
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }

        public float getPressure() {
            return this.pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public float getHumidity() {
            return this.humidity;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }
    }

    public class Wind {
        float speed;
        float deg;

        public Wind() {
        }

        public float getSpeed() {
            return this.speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public float getDeg() {
            return this.deg;
        }

        public void setDeg(float deg) {
            this.deg = deg;
        }
    }
}
