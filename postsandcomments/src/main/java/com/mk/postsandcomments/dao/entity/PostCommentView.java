/**
 *
 */
package com.mk.postsandcomments.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author muffa
 *
 */
@Entity
@Table(name = "posts_comments")
public class PostCommentView {
	@EmbeddedId
	private PostCommentPK id;
	@Column(name = "post_text")
	private String postText;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "location_city")
	private String city;
	@Column(name = "created_dt")
	private LocalDateTime createdDate;
	@Column(name = "comment_text")
	private String comment;
	@Column(name = "comment_created_date")
	private LocalDateTime commentCreatedDate;

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the commentCreatedDate
	 */
	public LocalDateTime getCommentCreatedDate() {
		return commentCreatedDate;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the id
	 */
	public PostCommentPK getId() {
		return id;
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
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param commentCreatedDate the commentCreatedDate to set
	 */
	public void setCommentCreatedDate(LocalDateTime commentCreatedDate) {
		this.commentCreatedDate = commentCreatedDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(PostCommentPK id) {
		this.id = id;
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
