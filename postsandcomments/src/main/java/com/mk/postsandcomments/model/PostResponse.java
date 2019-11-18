/**
 *
 */
package com.mk.postsandcomments.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

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
	private List<CommentResponse> comments;

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the post
	 */
	public String getPost() {
		return post;
	}

	/**
	 * @return the postId
	 */
	public BigInteger getPostId() {
		return postId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the weatherData
	 */
	public WeatherData getWeatherData() {
		return weatherData;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(BigInteger postId) {
		this.postId = postId;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param weatherData the weatherData to set
	 */
	public void setWeatherData(WeatherData weatherData) {
		this.weatherData = weatherData;
	}

	/**
	 * @return the comments
	 */
	public List<CommentResponse> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<CommentResponse> comments) {
		this.comments = comments;
	}
}
