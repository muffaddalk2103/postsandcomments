/**
 * 
 */
package com.loyaltyone.postsandcomments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.loyaltyone.postsandcomments.dao.PostDao;
import com.loyaltyone.postsandcomments.dao.entity.Post;
import com.loyaltyone.postsandcomments.posts.model.PostRequest;
import com.loyaltyone.postsandcomments.posts.model.PostResponse;
import com.loyaltyone.postsandcomments.posts.model.Response;

/**
 * @author muffa
 *
 */
@Service
public class PostService {
	private PostDao postDao;
	/**
	 * @param postDao
	 */
	@Autowired
	public PostService(PostDao postDao) {
		super();
		this.postDao = postDao;
	}

	public Response addPost(PostRequest postRequest) {
		Post post = postDao.savePost(postRequest);
		PostResponse postResponse = new PostResponse();
		postResponse.setCity(post.getCity());
		postResponse.setCreatedDate(post.getCreatedDate());
		postResponse.setPost(post.getPostText());
		postResponse.setPostId(post.getPostId());
		postResponse.setUserName(post.getUserName());
		Response response= new Response();
		response.setData(postResponse);
		response.setSuccess(true);
		return response;
	}

	public Page<Post> getAllPosts(int page, int pageSize) {
		return postDao.getAllPosts(page, pageSize);
	}
	
	public Page<Post> getPostsByUserName(String userName,int page, int pageSize) {
		return postDao.getPostsByUserName(userName,page, pageSize);
	}
}
