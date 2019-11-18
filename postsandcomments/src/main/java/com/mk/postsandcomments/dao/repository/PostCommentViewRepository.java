/**
 *
 */
package com.mk.postsandcomments.dao.repository;

import java.math.BigInteger;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mk.postsandcomments.dao.entity.PostCommentView;

/**
 * @author muffa
 *
 */
@Repository
public interface PostCommentViewRepository extends PagingAndSortingRepository<PostCommentView, BigInteger> {
}
