/**
 *
 */
package com.loyaltyone.postsandcomments.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loyaltyone.postsandcomments.model.CommentRequest;
import com.loyaltyone.postsandcomments.model.Response;
import com.loyaltyone.postsandcomments.service.CommentService;

/**
 * @author muffa
 *
 */
@RestController
public class CommentController {
	private CommentService commentService;

	/**
	 * @param postService
	 */
	@Autowired
	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	@PostMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response addComment(@Valid @RequestBody CommentRequest commentRequest) {
		return commentService.addComment(commentRequest);
	}
}
