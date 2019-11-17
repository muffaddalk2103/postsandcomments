/**
 *
 */
package com.loyaltyone.postsandcomments.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loyaltyone.postsandcomments.common.CommonUtil;
import com.loyaltyone.postsandcomments.model.CommentRequest;
import com.loyaltyone.postsandcomments.model.Response;
import com.loyaltyone.postsandcomments.service.CommentService;

/**
 * @author muffa
 *
 */
@RestController
public class CommentController {
	private Logger logger = LoggerFactory.getLogger(CommentController.class);
	private CommentService commentService;

	/**
	 * @param commentService
	 */
	@Autowired
	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	/**
	 * Handles post request which allows user to add comment to a post
	 *
	 * @param commentRequest comment to be saved
	 * @return {@link Response}
	 */
	@PostMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> addComment(@Valid @RequestBody CommentRequest commentRequest) {
		logger.info("inside addComment");
		return CommonUtil.generateResponseEntity(commentService.addComment(commentRequest));
	}
}
