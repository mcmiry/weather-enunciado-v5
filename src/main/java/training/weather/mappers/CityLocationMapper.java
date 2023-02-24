package training.weather.mappers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import training.weather.models.CityLocation;

public class CityLocationMapper {

    public static CityLocation jsonToCityLocation (JSONObject jsonObject) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonObject.toString(), CityLocation.class);
    }

    public static String cityLocationToJson (CityLocation cityLocation) {
        Gson gson = new Gson();
        return gson.toJson(cityLocation);
    }
}
