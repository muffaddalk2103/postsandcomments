/**
 *
 */
package com.loyaltyone.postsandcomments.weatherinfo.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger logger = LoggerFactory.getLogger(WeatherInfoService.class);
	private Map<String, WeatherDataResponse> weatherMap;// ideally weather data should be stored in cache like redis or
	// hazelcast
	private RestTemplate restTemplate;

	private String weatherAPIEndpoint;

	private String apiKey;

	/**
	 * @param weatherMap
	 * @param restTemplate
	 */
	@Autowired
	public WeatherInfoService(Map<String, WeatherDataResponse> weatherMap, RestTemplate restTemplate,
			@Value("${weather.api.endpoint}") String weatherAPIEndpoint, @Value("${weather.api.key}") String apiKey) {
		super();
		this.weatherMap = weatherMap;
		this.restTemplate = restTemplate;
		this.weatherAPIEndpoint = weatherAPIEndpoint;
		this.apiKey = apiKey;
	}

	/**
	 * Calls the weather API to get the data and adds it to the weatherMap
	 *
	 * @param cityName city whose weather data is requested
	 */
	private void getWeatherData(String cityName) {
		logger.info("inside getWeatherData");
		try {
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(weatherAPIEndpoint)
					.queryParam("q", cityName + ",CA").queryParam("APPID", apiKey).build();// defaulting country to
			// Canada
			WeatherDataResponse weatherDataResponse = restTemplate.getForObject(uriComponents.toUriString(),
					WeatherDataResponse.class);
			weatherMap.putIfAbsent(cityName, weatherDataResponse);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			if (logger.isDebugEnabled()) {
				logger.info("getWeatherData failed for " + cityName);
			}
			weatherMap.putIfAbsent(cityName, null); // makes sure that weather data isn't requested again and again for
			// an unsupported or non-existent city for atleast 10 mins.
		}
	}

	/**
	 * Returns weather data for the requested city. Synchronizes the request on city
	 * name to make sure API calls for same city are not sent to Weather API
	 *
	 * @param cityName city whose weather data is required
	 * @return {@link WeatherDataResponse} for the request city
	 */
	public WeatherDataResponse getWeatherDataByCity(String cityName) {
		if (logger.isInfoEnabled()) {
			logger.info("inside getWeatherDataByCity " + cityName);
		}
		cityName = cityName.toLowerCase();
		if (!weatherMap.containsKey(cityName)) {
			// synchronized to make sure only one thread is fetching weather data for one
			// city
			synchronized (cityName.intern()) {
				if (!weatherMap.containsKey(cityName)) {
					getWeatherData(cityName);
				}
			}
		}
		return weatherMap.get(cityName);
	}
}
