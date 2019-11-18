/**
 *
 */
package com.mk.postsandcomments.dao.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "post_id_seq")
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
	@OneToMany(mappedBy = "postId", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("commentId desc")
	private List<Comment> comments;

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the postId
	 */
	public BigInteger getPostId() {
		return postId;
	}

	/**
	 * @return the postText
	 */
	public String getPostText() {
		return postText;
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
	 * @param comments the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(BigInteger postId) {
		this.postId = postId;
	}

	/**
	 * @param postText the postText to set
	 */
	public void setPostText(String postText) {
		this.postText = postText;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
