/**
 * 
 */
package com.loyaltyone.postsandcomments.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.loyaltyone.postsandcomments.dao.entity.Post;
import com.loyaltyone.postsandcomments.dao.repository.PostRepository;
import com.loyaltyone.postsandcomments.posts.model.PostRequest;

/**
 * @author muffa
 *
 */
@Service
public class PostDao {

	private PostRepository postRepository;

	/**
	 * @param postRepository
	 */
	@Autowired
	public PostDao(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}

	public Post savePost(PostRequest postRequest) {
		Post post = new Post();
		post.setCity(postRequest.getCity());
		post.setPostText(postRequest.getPost());
		post.setUserName(postRequest.getUserName());
		return postRepository.save(post);
	}

	public Page<Post> getAllPosts(int page, int pageSize) {
		Pageable sortedByCreateDateDesc = 
				PageRequest.of(page, pageSize, Sort.by("createdDate").descending());
		return postRepository.findAll(sortedByCreateDateDesc);
	}

	public Page<Post> getPostsByUserName(String userName,int page, int pageSize) {
		Pageable sortedByCreateDateDesc = 
				PageRequest.of(page, pageSize, Sort.by("createdDate").descending());
		return postRepository.findAllByUserName(userName, sortedByCreateDateDesc);
	}
}
