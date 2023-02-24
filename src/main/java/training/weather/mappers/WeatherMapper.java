package training.weather.mappers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import training.weather.models.Weather;

public class WeatherMapper {

    public static Weather jsonToWeather(JSONObject jsonObject){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonObject.toString(), Weather.class);
    }

    public static String weatherToJson(Weather weather){
        Gson gson = new Gson();
        return gson.toJson(weather);
    }
}
