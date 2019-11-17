/**
 *
 */
package com.loyaltyone.postsandcomments.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loyaltyone.postsandcomments.model.PagingRequest;
import com.loyaltyone.postsandcomments.model.PagingResponse;
import com.loyaltyone.postsandcomments.model.PostRequest;
import com.loyaltyone.postsandcomments.model.Response;
import com.loyaltyone.postsandcomments.service.PostService;

/**
 * @author muffa
 *
 */
@RestController
public class PostController {
	private PostService postService;

	/**
	 * @param postService
	 */
	@Autowired
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@PostMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response addPost(@Valid @RequestBody PostRequest postRequest) {
		return postService.addPost(postRequest);
	}

	@PostMapping(value = "/allposts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PagingResponse getAllPosts(@RequestBody PagingRequest pagingRequest) {
		return postService.getAllPosts(pagingRequest);
	}

	@PostMapping(value = "/post/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PagingResponse getPostByUserName(@PathVariable String userName, @RequestBody PagingRequest pagingRequest) {
		return postService.getPostsByUserName(userName, pagingRequest);
	}
}
