/**
 *
 */
package com.mk.postsandcomments.test.controller;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.postsandcomments.controller.CommentController;
import com.mk.postsandcomments.model.CommentRequest;
import com.mk.postsandcomments.model.Response;
import com.mk.postsandcomments.service.CommentService;

/**
 * @author muffa
 *
 */
@AutoConfigureMockMvc
@ContextConfiguration(classes = { CommentController.class })
@WebMvcTest
public class CommentControllerTest {
	@Autowired
	private MockMvc mvc;
	@MockBean
	private CommentService service;
	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testInvalidComment() throws Exception {
		CommentRequest commentRequest = new CommentRequest();
		String data = mapper.writeValueAsString(commentRequest);
		mvc.perform(MockMvcRequestBuilders.post("/comment").content(data).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void testValidComment() throws Exception {
		CommentRequest commentRequest = new CommentRequest();
		commentRequest.setComment("comment");
		commentRequest.setPostId(BigInteger.ONE);
		Response response = new Response();
		response.setSuccess(true);
		String reponseData = mapper.writeValueAsString(response);
		Mockito.when(service.addComment(Mockito.any(CommentRequest.class))).thenReturn(response);
		String data = mapper.writeValueAsString(commentRequest);
		mvc.perform(MockMvcRequestBuilders.post("/comment").content(data).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(reponseData));
	}
}
