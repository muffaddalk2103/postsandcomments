/**
 * 
 */
package com.loyaltyone.postsandcomments.posts.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muffa
 *
 */
@RestController
public class PostController {
	@PostMapping(value="/post",produces=MediaType.TEXT_PLAIN_VALUE,consumes=MediaType.TEXT_PLAIN_VALUE)
	public String postText(@Valid @RequestBody String text){
		return text;
	}
}
