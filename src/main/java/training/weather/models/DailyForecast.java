package training.weather.models;

import java.util.List;

public class DailyForecast {

    private List<Integer> weatherCode;
    private List<String> time;

    public DailyForecast(List<Integer> weatherCode, List<String> time) {
        this.weatherCode = weatherCode;
        this.time = time;
    }

    public List<Integer> getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(List<Integer> weatherCode) {
        this.weatherCode = weatherCode;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }
}
