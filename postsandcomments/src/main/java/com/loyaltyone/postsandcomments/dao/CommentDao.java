/**
 *
 */
package com.loyaltyone.postsandcomments.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltyone.postsandcomments.dao.entity.Comment;
import com.loyaltyone.postsandcomments.dao.repository.CommentRepository;

/**
 * @author muffa
 *
 */
@Service
public class CommentDao {

	private Logger logger = LoggerFactory.getLogger(CommentDao.class);
	private CommentRepository commentRepository;

	/**
	 * @param commentRepository
	 */
	@Autowired
	public CommentDao(CommentRepository commentRepository) {
		super();
		this.commentRepository = commentRepository;
	}

	/**
	 * Saves comment in database
	 * 
	 * @param comment comment to be saed
	 * @return saved comment
	 */
	public Comment saveComment(Comment comment) {
		logger.info("Inside saveComment");
		return commentRepository.save(comment);
	}
}
