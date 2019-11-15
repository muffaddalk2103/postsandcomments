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

import com.loyaltyone.postsandcomments.posts.controller.PostController;

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

	@Test
	public void testPostText()
			throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/post").content("test")
				.contentType(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("test"));

	}
}
