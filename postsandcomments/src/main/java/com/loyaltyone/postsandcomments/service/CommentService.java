/**
 *
 */
package com.loyaltyone.postsandcomments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyaltyone.postsandcomments.dao.CommentDao;
import com.loyaltyone.postsandcomments.dao.entity.Comment;
import com.loyaltyone.postsandcomments.model.CommentRequest;
import com.loyaltyone.postsandcomments.model.CommentResponse;
import com.loyaltyone.postsandcomments.model.Response;

/**
 * @author muffa
 *
 */
@Service
public class CommentService {

	private CommentDao commentDao;

	/**
	 * @param commentDao
	 */
	@Autowired
	public CommentService(CommentDao commentDao) {
		super();
		this.commentDao = commentDao;
	}

	public Response addComment(CommentRequest commentRequest) {
		Comment comment = new Comment();
		comment.setComment(commentRequest.getComment());
		comment.setPostId(commentRequest.getPostId());
		comment = commentDao.saveComment(comment);
		CommentResponse commentResponse = new CommentResponse();
		commentResponse.setComment(comment.getComment());
		commentResponse.setCreatedDate(comment.getCreatedDate());
		commentResponse.setPostId(comment.getPostId());
		Response response = new Response();
		response.setData(commentResponse);
		response.setSuccess(true);
		return response;
	}

}
