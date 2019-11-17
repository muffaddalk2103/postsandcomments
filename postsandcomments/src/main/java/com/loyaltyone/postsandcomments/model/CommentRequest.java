/**
 *
 */
package com.loyaltyone.postsandcomments.model;

import java.math.BigInteger;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author muffa
 *
 */
public class CommentRequest {
	@NotEmpty(message = "{comment.empty}")
	private String comment;
	@NotNull(message = "{comment.postid}")
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

	@Override
	public String toString() {
		return "CommentRequest [comment=" + comment + ", postId=" + postId + "]";
	}
}
