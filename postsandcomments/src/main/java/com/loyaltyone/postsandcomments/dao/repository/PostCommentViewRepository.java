/**
 *
 */
package com.loyaltyone.postsandcomments.dao.repository;

import java.math.BigInteger;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.loyaltyone.postsandcomments.dao.entity.PostCommentView;

/**
 * @author muffa
 *
 */
@Repository
public interface PostCommentViewRepository extends PagingAndSortingRepository<PostCommentView, BigInteger> {
}
