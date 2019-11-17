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
import com.loyaltyone.postsandcomments.dao.entity.PostCommentView;
import com.loyaltyone.postsandcomments.dao.repository.PostCommentViewRepository;
import com.loyaltyone.postsandcomments.dao.repository.PostRepository;
import com.loyaltyone.postsandcomments.model.PostRequest;

/**
 * @author muffa
 *
 */
@Service
public class PostDao {

	private PostRepository postRepository;
	private PostCommentViewRepository postCommentViewRepository;

	/**
	 * @param postRepository
	 */
	@Autowired
	public PostDao(PostRepository postRepository, PostCommentViewRepository postCommentViewRepository) {
		super();
		this.postRepository = postRepository;
		this.postCommentViewRepository = postCommentViewRepository;
	}

	public Page<PostCommentView> getAllPosts(int page, int pageSize) {
		Pageable sortedByCreateDateDesc = PageRequest.of(page, pageSize,
				Sort.by("createdDate").descending().and(Sort.by("commentCreatedDate").descending()));
		return postCommentViewRepository.findAll(sortedByCreateDateDesc);
	}

	public long getPostCount() {
		return postRepository.count();
	}

	public Page<Post> getPostsByUserName(String userName, int page, int pageSize) {
		Pageable sortedByCreateDateDesc = PageRequest.of(page, pageSize, Sort.by("createdDate").descending());
		return postRepository.findAllByUserName(userName, sortedByCreateDateDesc);
	}

	public Post savePost(PostRequest postRequest) {
		Post post = new Post();
		post.setCity(postRequest.getCity());
		post.setPostText(postRequest.getPost());
		post.setUserName(postRequest.getUserName());
		return postRepository.save(post);
	}
}
