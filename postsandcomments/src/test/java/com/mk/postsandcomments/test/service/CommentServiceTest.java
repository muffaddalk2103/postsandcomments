/**
 *
 */
package com.mk.postsandcomments.test.service;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mk.postsandcomments.dao.CommentDao;
import com.mk.postsandcomments.dao.entity.Comment;
import com.mk.postsandcomments.model.CommentRequest;
import com.mk.postsandcomments.model.CommentResponse;
import com.mk.postsandcomments.model.Response;
import com.mk.postsandcomments.service.CommentService;

/**
 * @author muffa
 *
 */
public class CommentServiceTest {

	@Mock
	private CommentDao commentDao;

	@InjectMocks
	private CommentService commentService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddComment() {
		CommentRequest commentRequest = new CommentRequest();
		commentRequest.setComment("comment");
		commentRequest.setPostId(BigInteger.ONE);
		Comment comment = new Comment();
		comment.setCommentId(BigInteger.ONE);
		comment.setCreatedDate(LocalDateTime.now());
		comment.setComment("comment");
		comment.setPostId(BigInteger.ONE);
		Mockito.when(commentDao.saveComment(Mockito.any(Comment.class))).thenReturn(comment);
		Response response = commentService.addComment(commentRequest);
		Assertions.assertTrue(response.getData() instanceof CommentResponse);
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNull(response.getMessages());
		CommentResponse commentResponse = (CommentResponse) response.getData();
		Assertions.assertEquals(commentResponse.getComment(), comment.getComment());
		Assertions.assertEquals(commentResponse.getPostId(), comment.getPostId());
		Assertions.assertEquals(commentResponse.getCreatedDate(), comment.getCreatedDate());
	}

	@Test
	public void testAddCommentWhenException() {
		CommentRequest commentRequest = new CommentRequest();
		commentRequest.setComment("comment");
		commentRequest.setPostId(BigInteger.ONE);
		Mockito.when(commentDao.saveComment(Mockito.any(Comment.class))).thenThrow(NullPointerException.class);
		Response response = commentService.addComment(commentRequest);
		Assertions.assertNull(response.getData());
		Assertions.assertFalse(response.isSuccess());
		Assertions.assertFalse(response.getMessages().isEmpty());
	}
}
