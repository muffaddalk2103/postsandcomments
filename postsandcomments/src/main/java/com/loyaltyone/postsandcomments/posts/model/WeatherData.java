/**
 *
 */
package com.loyaltyone.postsandcomments.posts.model;

/**
 * @author muffa
 *
 */
public class WeatherData {
	private float latitude;
	private float longitude;
	private int temperature;

	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * @return the temperature
	 */
	public int getTemperature() {
		return temperature;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
}
