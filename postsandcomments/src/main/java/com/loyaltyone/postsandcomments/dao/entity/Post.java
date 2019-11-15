/**
 * 
 */
package com.loyaltyone.postsandcomments.dao.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author muffa
 *
 */
@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator = "post_id_seq")
	@Column(name = "post_id")
	private BigInteger postId;
	@Column(name = "post_text", nullable = false)
	private String postText;
	@Column(name = "user_name", nullable = false)
	private String userName;
	@Column(name = "location_city", nullable = false)
	private String city;
	@Column(name = "created_dt", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createdDate;
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
	 * @return the postText
	 */
	public String getPostText() {
		return postText;
	}
	/**
	 * @param postText the postText to set
	 */
	public void setPostText(String postText) {
		this.postText = postText;
	}
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
