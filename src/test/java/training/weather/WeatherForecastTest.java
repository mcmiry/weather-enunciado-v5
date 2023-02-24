package training.weather;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import training.weather.mappers.CityLocationMapper;
import training.weather.mappers.WeatherMapper;
import training.weather.models.CityLocation;
import training.weather.models.Weather;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherForecastTest {

	@Mock
	private CityLocation cityLocation;
	@Mock
	private Weather weather;

	@Test
	public void testGetCityWeather() throws IOException, ParseException {
		String city = "Madrid";
		String dateString = "2023-02-23";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Optional<Date> datetime = Optional.of(dateFormat.parse(dateString));

		JSONObject cityLocationJson = new JSONObject("{\"latt\":\"40.4167\",\"longt\":\"-3.7037\"}");
		JSONObject weatherJson = new JSONObject("{\"daily\":{\"weathercode\":[0,1,2,2,2,1],\"time\":[\"2023-02-23\",\"2023-02-24\",\"2023-02-25\",\"2023-02-26\",\"2023-02-27\",\"2023-02-28\"]}}");
		when(CityLocationMapper.jsonToCityLocation(cityLocationJson)).thenReturn(cityLocation);
		when(WeatherMapper.jsonToWeather(weatherJson)).thenReturn(weather);

		String result = WeatherForecast.getCityWeather(city, datetime);

		assertEquals("Clear sky", result);
	}

	@Test
	public void testGetCityWeatherWithoutCity () throws IOException, ParseException {
		String city = "";
		String dateString = "2023-02-23";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Optional<Date> datetime = Optional.of(dateFormat.parse(dateString));

		String result = WeatherForecast.getCityWeather(city, datetime);

		assertEquals("", result);
	}

	@Test
	public void testGetCityWeatherWithoutDate () throws IOException, ParseException {
		String city = "Madrid";
		Optional<Date> datetime = Optional.empty();

		JSONObject cityLocationJson = new JSONObject("{\"latt\":\"40.4167\",\"longt\":\"-3.7037\"}");
		JSONObject weatherJson = new JSONObject("{\"daily\":{\"weathercode\":[0,1,2,2,2,1],\"time\":[\"2023-02-23\",\"2023-02-24\",\"2023-02-25\",\"2023-02-26\",\"2023-02-27\",\"2023-02-28\"]}}");
		when(CityLocationMapper.jsonToCityLocation(cityLocationJson)).thenReturn(cityLocation);
		when(WeatherMapper.jsonToWeather(weatherJson)).thenReturn(weather);

		// When
		String result = WeatherForecast.getCityWeather(city, datetime);

		// Then
		assertEquals("Clear sky", result);
	}
}
