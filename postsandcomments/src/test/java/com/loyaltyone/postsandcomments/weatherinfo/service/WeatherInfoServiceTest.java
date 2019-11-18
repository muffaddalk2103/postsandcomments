/**
 *
 */
package com.loyaltyone.postsandcomments.weatherinfo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.loyaltyone.postsandcomments.weatherinfo.model.LocationData;
import com.loyaltyone.postsandcomments.weatherinfo.model.WeatherData;
import com.loyaltyone.postsandcomments.weatherinfo.model.WeatherDataResponse;

/**
 * @author muffa
 *
 */
public class WeatherInfoServiceTest {

	@Mock
	private RestTemplate restTemplate;
	private Map<String, WeatherDataResponse> map = new HashMap<String, WeatherDataResponse>();

	private WeatherInfoService weatherInfoService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		weatherInfoService = new WeatherInfoService(map, restTemplate, "https://localhost", "apiKey");
	}

	@Test
	public void testGetWeatherByCity() {
		WeatherDataResponse weatherDataResponse = new WeatherDataResponse();
		weatherDataResponse.setCity("city");
		weatherDataResponse.setLocationData(new LocationData());
		weatherDataResponse.getLocationData().setLatitude(10);
		weatherDataResponse.getLocationData().setLongitude(10);
		weatherDataResponse.setWeatherData(new WeatherData());
		weatherDataResponse.getWeatherData().setTemperature(10);
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(weatherDataResponse);
		WeatherDataResponse response = weatherInfoService.getWeatherDataByCity("city");
		Assertions.assertEquals(response.getCity(), weatherDataResponse.getCity());
		Assertions.assertEquals(response.getLocationData().getLatitude(),
				weatherDataResponse.getLocationData().getLatitude());
		Assertions.assertEquals(response.getLocationData().getLongitude(),
				weatherDataResponse.getLocationData().getLongitude());
		Assertions.assertEquals(response.getWeatherData().getTemperature(),
				weatherDataResponse.getWeatherData().getTemperature());
	}

	@Test
	public void testGetWeatherByCitySynchronization() throws InterruptedException, ExecutionException {
		map.clear();
		WeatherDataResponse weatherDataResponse = new WeatherDataResponse();
		weatherDataResponse.setCity("city");
		weatherDataResponse.setLocationData(new LocationData());
		weatherDataResponse.getLocationData().setLatitude(10);
		weatherDataResponse.getLocationData().setLongitude(10);
		weatherDataResponse.setWeatherData(new WeatherData());
		weatherDataResponse.getWeatherData().setTemperature(10);
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(weatherDataResponse);
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<Future<WeatherDataResponse>> futureList = new ArrayList<Future<WeatherDataResponse>>();
		for (int i = 0; i < 10; i++) {
			futureList.add(executorService.submit(() -> {
				return weatherInfoService.getWeatherDataByCity("city");
			}));
		}
		executorService.shutdown();
		for (Future<WeatherDataResponse> future : futureList) {
			WeatherDataResponse response = future.get();
			Assertions.assertEquals(response.getCity(), weatherDataResponse.getCity());
			Assertions.assertEquals(response.getLocationData().getLatitude(),
					weatherDataResponse.getLocationData().getLatitude());
			Assertions.assertEquals(response.getLocationData().getLongitude(),
					weatherDataResponse.getLocationData().getLongitude());
			Assertions.assertEquals(response.getWeatherData().getTemperature(),
					weatherDataResponse.getWeatherData().getTemperature());
		}
	}

	@Test
	public void testGetWeatherByCityWhenException() {
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any()))
				.thenThrow(NullPointerException.class);
		WeatherDataResponse response = weatherInfoService.getWeatherDataByCity("city1");
		Assertions.assertNull(response);
	}

}
