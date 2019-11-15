/**
 * 
 */
package com.loyaltyone.postsandcomments.weatherinfo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author muffa
 *
 */
public class WeatherDataResponse {

	@JsonProperty("coord")
	private LocationData locationData;
	@JsonProperty("main")
	private WeatherData weatherData;
	/**
	 * @return the locationData
	 */
	public LocationData getLocationData() {
		return locationData;
	}
	/**
	 * @param locationData the locationData to set
	 */
	public void setLocationData(LocationData locationData) {
		this.locationData = locationData;
	}
	/**
	 * @return the weatherData
	 */
	public WeatherData getWeatherData() {
		return weatherData;
	}
	/**
	 * @param weatherData the weatherData to set
	 */
	public void setWeatherData(WeatherData weatherData) {
		this.weatherData = weatherData;
	}
}
