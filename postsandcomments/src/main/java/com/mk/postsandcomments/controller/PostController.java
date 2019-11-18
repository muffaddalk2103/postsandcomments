/**
 *
 */
package com.mk.postsandcomments.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mk.postsandcomments.common.CommonUtil;
import com.mk.postsandcomments.model.PostRequest;
import com.mk.postsandcomments.model.Response;
import com.mk.postsandcomments.model.pagination.PagingRequest;
import com.mk.postsandcomments.model.pagination.PagingResponse;
import com.mk.postsandcomments.service.PostService;

/**
 * @author muffa
 *
 */
@RestController
public class PostController {
	private Logger logger = LoggerFactory.getLogger(PostController.class);
	private PostService postService;

	/**
	 * @param postService
	 */
	@Autowired
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	/**
	 * Handles post request which allows user submitting posts
	 *
	 * @param postRequest request to be saved
	 * @return {@link Response}
	 */
	@PostMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> addPost(@Valid @RequestBody PostRequest postRequest) {
		logger.info("inside addPost");
		return CommonUtil.generateResponseEntity(postService.addPost(postRequest));
	}

	/**
	 * Returns all posts in a paginated fashion
	 *
	 * @param pagingRequest paging request received
	 * @return {@link PagingResponse}
	 */
	@PostMapping(value = "/allposts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PagingResponse getAllPosts(@Valid @RequestBody PagingRequest pagingRequest) {
		logger.info("inside getAllPosts");
		return postService.getAllPosts(pagingRequest);
	}

	/**
	 * Returns all posts by a particular user in paginated fashion
	 *
	 * @param userName      user whose posts are to be returned
	 * @param pagingRequest page request received
	 * @return PagingResponse
	 */
	@PostMapping(value = "/post/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagingResponse getPostByUserName(@PathVariable String userName,
			@Valid @RequestBody PagingRequest pagingRequest) {
		logger.info("inside getPostByUserName");
		return postService.getPostsByUserName(userName, pagingRequest);
	}
}
