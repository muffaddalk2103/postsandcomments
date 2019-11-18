/**
 *
 */
package com.mk.postsandcomments.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mk.postsandcomments.dao.entity.Post;
import com.mk.postsandcomments.dao.entity.PostCommentView;
import com.mk.postsandcomments.dao.repository.PostCommentViewRepository;
import com.mk.postsandcomments.dao.repository.PostRepository;

/**
 * @author muffa
 *
 */
@Service
public class PostDao {
	private Logger logger = LoggerFactory.getLogger(PostDao.class);
	private PostRepository postRepository;
	private PostCommentViewRepository postCommentViewRepository;

	/**
	 * @param postRepository
	 * @param postCommentViewRepository
	 */
	@Autowired
	public PostDao(PostRepository postRepository, PostCommentViewRepository postCommentViewRepository) {
		super();
		this.postRepository = postRepository;
		this.postCommentViewRepository = postCommentViewRepository;
	}

	/**
	 * Gets posts from the database
	 *
	 * @param page     page number to be retrieved
	 * @param pageSize size of the data to be returned
	 * @return page
	 */
	public Page<PostCommentView> getAllPosts(int page, int pageSize) {
		logger.info("Inside getAllPosts");
		Pageable sortedByCreateDateDesc = PageRequest.of(page, pageSize,
				Sort.by("createdDate").descending().and(Sort.by("commentCreatedDate").descending()));
		return postCommentViewRepository.findAll(sortedByCreateDateDesc);
	}

	/**
	 * Gets total posts from database
	 *
	 * @return count of records
	 */
	public long getPostCount() {
		logger.info("Inside getPostCount");
		return postRepository.count();
	}

	/**
	 * Returns posts filtered by user name
	 *
	 * @param userName used for filtering by username
	 * @param page     page number to be retrieved
	 * @param pageSize size of the data to be returned
	 * @return page
	 */
	public Page<Post> getPostsByUserName(String userName, int page, int pageSize) {
		logger.info("Inside getPostsByUserName");
		Pageable sortedByCreateDateDesc = PageRequest.of(page, pageSize, Sort.by("createdDate").descending());
		return postRepository.findAllByUserName(userName, sortedByCreateDateDesc);
	}

	/**
	 * Saves data to post table
	 *
	 * @param post post to save
	 * @return post entity
	 */
	public Post savePost(Post post) {
		logger.info("Inside savePost");
		return postRepository.save(post);
	}
}
