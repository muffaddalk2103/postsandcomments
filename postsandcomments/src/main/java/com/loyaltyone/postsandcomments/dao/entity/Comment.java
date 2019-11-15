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
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator = "comment_id_seq")
	@Column(name = "comment_id")
	private BigInteger commentId;
	@Column(name = "post_id", nullable = false)
	private BigInteger postId;
	@Column(name = "comment_text", nullable = false)
	private String comment;
	@Column(name = "created_dt", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createdDate;
	/**
	 * @return the commentId
	 */
	public BigInteger getCommentId() {
		return commentId;
	}
	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(BigInteger commentId) {
		this.commentId = commentId;
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
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
