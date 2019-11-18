/**
 *
 */
package com.mk.postsandcomments.test.controller;

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
import com.mk.postsandcomments.controller.PostController;
import com.mk.postsandcomments.model.PostRequest;
import com.mk.postsandcomments.model.Response;
import com.mk.postsandcomments.model.pagination.PagingRequest;
import com.mk.postsandcomments.model.pagination.PagingResponse;
import com.mk.postsandcomments.service.PostService;

/**
 * @author muffa
 *
 */
@AutoConfigureMockMvc
@ContextConfiguration(classes = { PostController.class })
@WebMvcTest
public class PostControllerTest {
	@Autowired
	private MockMvc mvc;
	@MockBean
	private PostService service;
	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testAllPostsWithInvalidRequest() throws Exception {
		PagingRequest pagingRequest = new PagingRequest();
		String data = mapper.writeValueAsString(pagingRequest);
		mvc.perform(MockMvcRequestBuilders.post("/allposts").content(data).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testAllPostsWithValidRequest() throws Exception {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setStart(0);
		pagingRequest.setLength(10);
		String data = mapper.writeValueAsString(pagingRequest);
		PagingResponse response = new PagingResponse();
		response.setData(1);
		response.setDraw(1);
		String reponseData = mapper.writeValueAsString(response);
		Mockito.when(service.getAllPosts(Mockito.any(PagingRequest.class))).thenReturn(response);
		mvc.perform(MockMvcRequestBuilders.post("/allposts").content(data).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(reponseData));
	}

	@Test
	public void testGetPostsByUserNameWithInvalidRequest() throws Exception {
		PagingRequest pagingRequest = new PagingRequest();
		String data = mapper.writeValueAsString(pagingRequest);
		mvc.perform(MockMvcRequestBuilders.post("/post").content(data).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testGetPostsByUserNameWithValidRequest() throws Exception {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setStart(0);
		pagingRequest.setLength(10);
		String data = mapper.writeValueAsString(pagingRequest);
		PagingResponse response = new PagingResponse();
		response.setData(1);
		response.setDraw(1);
		String reponseData = mapper.writeValueAsString(response);
		Mockito.when(service.getPostsByUserName(Mockito.anyString(), Mockito.any(PagingRequest.class)))
				.thenReturn(response);
		mvc.perform(MockMvcRequestBuilders.post("/post/username").content(data).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(reponseData));
	}

	@Test
	public void testInvalidPostText() throws Exception {
		PostRequest postRequest = new PostRequest();
		String data = mapper.writeValueAsString(postRequest);
		mvc.perform(MockMvcRequestBuilders.post("/post").content(data).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void testValidPostText() throws Exception {
		PostRequest postRequest = new PostRequest();
		postRequest.setCity("city");
		postRequest.setPost("post");
		postRequest.setUserName("username");
		Response response = new Response();
		response.setSuccess(true);
		String reponseData = mapper.writeValueAsString(response);
		Mockito.when(service.addPost(Mockito.any(PostRequest.class))).thenReturn(response);
		String data = mapper.writeValueAsString(postRequest);
		mvc.perform(MockMvcRequestBuilders.post("/post").content(data).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(reponseData));
	}
}
