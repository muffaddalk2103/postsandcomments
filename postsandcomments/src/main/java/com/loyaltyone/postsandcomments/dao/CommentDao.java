/**
 *
 */
package com.loyaltyone.postsandcomments.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltyone.postsandcomments.dao.entity.Comment;
import com.loyaltyone.postsandcomments.dao.repository.CommentRepository;
import com.loyaltyone.postsandcomments.model.CommentRequest;

/**
 * @author muffa
 *
 */
@Service
public class CommentDao {

	private CommentRepository commentRepository;

	/**
	 * @param postRepository
	 */
	@Autowired
	public CommentDao(CommentRepository commentRepository) {
		super();
		this.commentRepository = commentRepository;
	}

	public Comment saveComment(CommentRequest commentRequest) {
		Comment comment = new Comment();
		comment.setComment(commentRequest.getComment());
		comment.setPostId(commentRequest.getPostId());
		return commentRepository.save(comment);
	}
}
