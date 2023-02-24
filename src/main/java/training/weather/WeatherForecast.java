package training.weather;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.json.JSONObject;
import training.weather.mappers.CityLocationMapper;
import training.weather.mappers.WeatherMapper;
import training.weather.models.CityLocation;
import training.weather.models.ForecastEnum;
import training.weather.models.Weather;

public class WeatherForecast {

	private static final long DAYS_AHEAD = 6;
	private static final String GEOCODE_URL = "https://geocode.xyz/";
	private static final String WEATHER_URL = "https://api.open-meteo.com/v1/forecast";
	private static final String TIMEZONE = "Europe/Berlin";

	public static String getCityWeather(String city, Optional<Date> datetime) throws IOException, ParseException {
		Date date = datetime.orElse(new Date());
		if (date.before(getLimitDate()) && !city.isEmpty()) {
			String cityLocationUrl = GEOCODE_URL + city + "?json=1";
			CityLocation cityLocation = CityLocationMapper.jsonToCityLocation(getJSONInformation(cityLocationUrl));

			String weatherUrl = WEATHER_URL + "?latitude=" + cityLocation.getLatitude() + "&longitude=" + cityLocation.getLongitude() + "&daily=weathercode&current_weather=true&timezone=" + TIMEZONE;
			Weather weather = WeatherMapper.jsonToWeather(getJSONInformation(weatherUrl));

			if (weather.getDailyForecast() != null) {
				List<String> dailyResults = weather.getDailyForecast().getTime();
				List<Integer> weatherCodeResults = weather.getDailyForecast().getWeatherCode();

				OptionalInt index = IntStream.range(0, dailyResults.size())
						.filter(i -> {
							try {
								return isSameDate(dailyResults.get(i), date);
							} catch (ParseException e) {
								throw new RuntimeException(e);
							}
						})
						.findFirst();

				if (index.isPresent()){
					return ForecastEnum.getEnumByCode(weatherCodeResults.get(index.getAsInt())).getDescription();
				}
			}
		}
		return "";
	}

	private static Date getLimitDate (){
		LocalDate today = LocalDate.now();
		LocalDate limitDate = today.plusDays(DAYS_AHEAD);
		return Date.from(limitDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	private static JSONObject getJSONInformation (String url) throws IOException {
		HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
		HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
		String response = request.execute().parseAsString();
		return new JSONObject(response);
	}

	private static boolean isSameDate (String dailyDate, Date date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedDate = formatter.parse(dailyDate);
		return date.compareTo(parsedDate) == 0;
	}
}
