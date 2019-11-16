/**
 *
 */
package com.loyaltyone.postsandcomments.weatherinfo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.loyaltyone.postsandcomments.weatherinfo.model.WeatherDataResponse;

/**
 * @author muffa
 *
 */
@Service
public class WeatherInfoService {

	private Map<String, WeatherDataResponse> weatherMap;

	private RestTemplate restTemplate;

	@Value("${weather.api.endpoint}")
	private String weatherAPIEndpoint;

	@Value("${weather.api.key}")
	private String apiKey;

	/**
	 * @param weatherMap
	 * @param restTemplate
	 */
	@Autowired
	public WeatherInfoService(Map<String, WeatherDataResponse> weatherMap, RestTemplate restTemplate) {
		super();
		this.weatherMap = weatherMap;
		this.restTemplate = restTemplate;
	}

	public Map<String, WeatherDataResponse> getWeatherData(Set<String> cityNames) {
		Map<String, WeatherDataResponse> weatherDataMap = new HashMap<String, WeatherDataResponse>();
		for (String cityName : cityNames) {
			weatherDataMap.put(cityName, getWeatherDataByCity(cityName));
		}
		return weatherDataMap;
	}

	private void getWeatherData(String cityName) {
		try {
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(weatherAPIEndpoint)
					.queryParam("q", cityName + ",CA").queryParam("APPID", apiKey).build();// defaulting country to
			// Canada
			WeatherDataResponse weatherDataResponse = restTemplate.getForObject(uriComponents.toUriString(),
					WeatherDataResponse.class);
			if (weatherDataResponse != null) {
				weatherMap.putIfAbsent(cityName, weatherDataResponse);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public WeatherDataResponse getWeatherDataByCity(String cityName) {
		cityName = cityName.toLowerCase();
		if (!weatherMap.containsKey(cityName)) {
			synchronized (cityName.intern()) {// synchronized to make sure only one thread is fetching weather data for
												// one
				// city
				if (!weatherMap.containsKey(cityName)) {
					getWeatherData(cityName);
				}
			}
		}
		return weatherMap.get(cityName);
	}
}
