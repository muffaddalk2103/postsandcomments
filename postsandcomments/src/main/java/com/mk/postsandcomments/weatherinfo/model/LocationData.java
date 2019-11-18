/**
 * 
 */
package com.mk.postsandcomments.weatherinfo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author muffa
 *
 */
public class LocationData {
	@JsonProperty("lon")
	private float longitude;
	@JsonProperty("lat")
	private float latitude;
	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
}
