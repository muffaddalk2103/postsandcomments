/**
 *
 */
package com.loyaltyone.postsandcomments.model;

import javax.validation.constraints.NotEmpty;

/**
 * @author muffa
 *
 */
public class PostRequest {
	@NotEmpty
	private String userName;
	@NotEmpty
	private String city;
	@NotEmpty
	private String post;

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the post
	 */
	public String getPost() {
		return post;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
