/**
 * 
 */
package com.mk.postsandcomments.dao.repository;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mk.postsandcomments.dao.entity.Post;

/**
 * @author muffa
 *
 */
@Repository
public interface PostRepository extends CrudRepository<Post, BigInteger>, PagingAndSortingRepository<Post, BigInteger> {
	
	Page<Post> findAllByUserName(String userName, Pageable pageable);
}
