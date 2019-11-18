/**
 *
 */
package com.mk.postsandcomments.model;

import javax.validation.constraints.NotEmpty;

/**
 * @author muffa
 *
 */
public class PostRequest {
	@NotEmpty(message = "{username.empty}")
	private String userName;
	@NotEmpty(message = "{city.empty}")
	private String city;
	@NotEmpty(message = "{post.empty}")
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

	@Override
	public String toString() {
		return "PostRequest [userName=" + userName + ", city=" + city + ", post=" + post + "]";
	}
}
