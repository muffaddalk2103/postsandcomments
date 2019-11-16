/**
 *
 */
package com.loyaltyone.postsandcomments.posts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loyaltyone.postsandcomments.dao.entity.Post;
import com.loyaltyone.postsandcomments.posts.model.PostRequest;
import com.loyaltyone.postsandcomments.posts.model.PostResponse;
import com.loyaltyone.postsandcomments.posts.model.Response;
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

	@GetMapping(value = "/post/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Post> getPostByUserName(@PathVariable String userName) {
		return postService.getPostsByUserName(userName, 0, 10);
	}

	@GetMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PostResponse> getPosts() {
		return postService.getAllPosts(0, 10);
	}
}
