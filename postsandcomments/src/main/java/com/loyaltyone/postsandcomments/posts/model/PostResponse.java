/**
 * 
 */
package com.loyaltyone.postsandcomments.posts.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author muffa
 *
 */
public class PostResponse {
	private BigInteger postId;
	private String userName;
	private String city;
	private String post;
	private WeatherData weatherData;
	private LocalDateTime createdDate;
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the post
	 */
	public String getPost() {
		return post;
	}
	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}
	/**
	 * @return the postId
	 */
	public BigInteger getPostId() {
		return postId;
	}
	/**
	 * @param postId the postId to set
	 */
	public void setPostId(BigInteger postId) {
		this.postId = postId;
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
	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
}
