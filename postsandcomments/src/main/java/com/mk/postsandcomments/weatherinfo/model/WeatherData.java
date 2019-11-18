/**
 * 
 */
package com.mk.postsandcomments.weatherinfo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author muffa
 *
 */
public class WeatherData {

	@JsonProperty("temp")
	private float temperature; //in kelvin

	/**
	 * @return the temperature
	 */
	public float getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
}
