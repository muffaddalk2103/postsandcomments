/**
 *
 */
package com.loyaltyone.postsandcomments.dao.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author muffa
 *
 */
@Embeddable
public class PostCommentPK implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "post_id")
	private BigInteger postId;
	@Column(name = "comment_id")
	private BigInteger commentId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PostCommentPK other = (PostCommentPK) obj;
		if (commentId == null) {
			if (other.commentId != null) {
				return false;
			}
		} else if (!commentId.equals(other.commentId)) {
			return false;
		}
		if (postId == null) {
			if (other.postId != null) {
				return false;
			}
		} else if (!postId.equals(other.postId)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the commentId
	 */
	public BigInteger getCommentId() {
		return commentId;
	}

	/**
	 * @return the postId
	 */
	public BigInteger getPostId() {
		return postId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentId == null) ? 0 : commentId.hashCode());
		result = prime * result + ((postId == null) ? 0 : postId.hashCode());
		return result;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(BigInteger commentId) {
		this.commentId = commentId;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(BigInteger postId) {
		this.postId = postId;
	}
}
