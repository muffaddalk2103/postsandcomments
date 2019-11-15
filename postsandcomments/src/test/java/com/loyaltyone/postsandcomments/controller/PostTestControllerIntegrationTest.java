/**
 * 
 */
package com.loyaltyone.postsandcomments.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loyaltyone.postsandcomments.posts.controller.PostController;
import com.loyaltyone.postsandcomments.posts.model.PostRequest;

/**
 * @author muffa
 *
 */
@AutoConfigureMockMvc
@ContextConfiguration(classes = {PostController.class})
@WebMvcTest
public class PostTestControllerIntegrationTest {
	@Autowired
	private MockMvc mvc;

	@Autowired 
	private ObjectMapper mapper;

	@Test
	public void testPostText()
			throws Exception {
		PostRequest postRequest = new PostRequest();
		String data = mapper.writeValueAsString(postRequest);
		mvc.perform(MockMvcRequestBuilders.post("/post").content(data)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(data));

	}
}
