/**
 * 
 */
package com.loyaltyone.postsandcomments.posts.model;

import java.math.BigInteger;

/**
 * @author muffa
 *
 */
public class CommentRequest {
	private String comment;
	private BigInteger postId;
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
}
