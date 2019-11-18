/**
 *
 */
package com.mk.postsandcomments.service;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.postsandcomments.dao.CommentDao;
import com.mk.postsandcomments.dao.entity.Comment;
import com.mk.postsandcomments.model.CommentRequest;
import com.mk.postsandcomments.model.CommentResponse;
import com.mk.postsandcomments.model.Response;

/**
 * @author muffa
 *
 */
@Service
public class CommentService {
	private Logger logger = LoggerFactory.getLogger(CommentService.class);
	private CommentDao commentDao;

	/**
	 * @param commentDao
	 */
	@Autowired
	public CommentService(CommentDao commentDao) {
		super();
		this.commentDao = commentDao;
	}

	/**
	 * saves comment to database
	 *
	 * @param commentRequest comment to be saved
	 * @return {@link Response}
	 */
	public Response addComment(CommentRequest commentRequest) {
		logger.info("inside addComment");
		if (logger.isDebugEnabled()) {
			logger.debug("common request " + commentRequest);
		}
		Response response = new Response();
		try {
			Comment comment = saveComment(commentRequest);
			CommentResponse commentResponse = new CommentResponse();
			commentResponse.setComment(comment.getComment());
			commentResponse.setCreatedDate(comment.getCreatedDate());
			commentResponse.setPostId(comment.getPostId());
			response.setData(commentResponse);
			response.setSuccess(true);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setMessages(Arrays.asList("Unable to save comment, please try after sometime."));
			response.setSuccess(false);
		}
		return response;
	}

	/**
	 * saves comment to database
	 *
	 * @param commentRequest comment to be saved
	 * @return {@link Comment}
	 */
	@Transactional
	private Comment saveComment(CommentRequest commentRequest) {
		logger.info("inside saveComment");
		Comment comment = new Comment();
		comment.setComment(commentRequest.getComment());
		comment.setPostId(commentRequest.getPostId());
		return commentDao.saveComment(comment);
	}

}
