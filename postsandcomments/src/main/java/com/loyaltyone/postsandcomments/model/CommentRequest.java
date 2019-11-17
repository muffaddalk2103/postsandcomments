/**
 *
 */
package com.loyaltyone.postsandcomments.model;

import java.math.BigInteger;

import javax.validation.constraints.NotEmpty;

/**
 * @author muffa
 *
 */
public class CommentRequest {
	@NotEmpty
	private String comment;
	@NotEmpty
	private BigInteger postId;

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the postId
	 */
	public BigInteger getPostId() {
		return postId;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(BigInteger postId) {
		this.postId = postId;
	}
}
