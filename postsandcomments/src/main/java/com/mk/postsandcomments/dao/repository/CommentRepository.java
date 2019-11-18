/**
 * 
 */
package com.mk.postsandcomments.dao.repository;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mk.postsandcomments.dao.entity.Comment;

/**
 * @author muffa
 *
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, BigInteger> {
}
