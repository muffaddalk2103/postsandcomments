/**
 *
 */
package com.mk.postsandcomments.test.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.mk.postsandcomments.dao.PostDao;
import com.mk.postsandcomments.dao.entity.Comment;
import com.mk.postsandcomments.dao.entity.Post;
import com.mk.postsandcomments.model.PostRequest;
import com.mk.postsandcomments.model.PostResponse;
import com.mk.postsandcomments.model.Response;
import com.mk.postsandcomments.model.pagination.PagingRequest;
import com.mk.postsandcomments.model.pagination.PagingResponse;
import com.mk.postsandcomments.service.PostService;
import com.mk.postsandcomments.weatherinfo.model.LocationData;
import com.mk.postsandcomments.weatherinfo.model.WeatherData;
import com.mk.postsandcomments.weatherinfo.model.WeatherDataResponse;
import com.mk.postsandcomments.weatherinfo.service.WeatherInfoService;

/**
 * @author muffa
 *
 */
public class PostServiceTest {

	@Mock
	private PostDao postDao;

	@Mock
	private WeatherInfoService weatherInfoService;

	@InjectMocks
	private PostService postService;

	private ArgumentMatcher<String> matchRequestId(final String target) {
		return request -> request != null && target.equals(request);
	}

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddPost() {
		PostRequest postRequest = new PostRequest();
		postRequest.setCity("city");
		postRequest.setPost("post");
		postRequest.setUserName("username");
		Post post = new Post();
		post.setCity("city");
		post.setCreatedDate(LocalDateTime.now());
		post.setPostId(BigInteger.ONE);
		post.setPostText("post");
		post.setUserName("username");
		Mockito.when(postDao.savePost(Mockito.any(Post.class))).thenReturn(post);
		Response response = postService.addPost(postRequest);
		Assertions.assertTrue(response.getData() instanceof PostResponse);
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNull(response.getMessages());
		PostResponse postResponse = (PostResponse) response.getData();
		Assertions.assertEquals(postResponse.getCity(), postRequest.getCity());
		Assertions.assertEquals(postResponse.getPost(), postRequest.getPost());
		Assertions.assertEquals(postResponse.getUserName(), postRequest.getUserName());
		Assertions.assertEquals(postResponse.getPostId(), post.getPostId());
		Assertions.assertEquals(postResponse.getCreatedDate(), post.getCreatedDate());
		Assertions.assertNull(postResponse.getWeatherData());
		Assertions.assertNull(postResponse.getComments());
	}

	@Test
	public void testAddPostWhenException() {
		PostRequest postRequest = new PostRequest();
		postRequest.setCity("city");
		postRequest.setPost("post");
		postRequest.setUserName("username");
		Mockito.when(postDao.savePost(Mockito.any(Post.class))).thenThrow(NullPointerException.class);
		Response response = postService.addPost(postRequest);
		Assertions.assertNull(response.getData());
		Assertions.assertFalse(response.isSuccess());
		Assertions.assertFalse(response.getMessages().isEmpty());
	}

	@Test
	public void testGetAllPosts() {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setLength(10);
		pagingRequest.setStart(0);
		Post post = new Post();
		post.setCity("city");
		post.setCreatedDate(LocalDateTime.now());
		post.setPostText("post");
		post.setUserName("username");
		post.setPostId(BigInteger.ONE);
		Post post1 = new Post();
		post1.setPostId(BigInteger.valueOf(2));
		post1.setCity("city1");
		post1.setCreatedDate(LocalDateTime.now());
		post1.setPostText("post");
		post1.setUserName("username");
		List<Comment> comments = new ArrayList<Comment>();
		post1.setComments(comments);
		Comment comment1 = new Comment();
		comment1.setComment("comment1");
		comment1.setCreatedDate(LocalDateTime.now());
		comment1.setCommentId(BigInteger.ONE);
		comment1.setPostId(post1.getPostId());
		Comment comment2 = new Comment();
		comment2.setCommentId(BigInteger.valueOf(2));
		comment2.setComment("comment2");
		comment2.setCreatedDate(LocalDateTime.now());
		comment2.setPostId(post1.getPostId());
		comments.add(comment1);
		comments.add(comment2);
		Page<Post> page = new PageImpl<Post>(Arrays.asList(post, post1));
		Mockito.when(postDao.getAllPosts(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);
		WeatherDataResponse weatherDataResponse = new WeatherDataResponse();
		weatherDataResponse.setCity("city");
		weatherDataResponse.setLocationData(new LocationData());
		weatherDataResponse.getLocationData().setLatitude(10);
		weatherDataResponse.getLocationData().setLongitude(10);
		weatherDataResponse.setWeatherData(new WeatherData());
		weatherDataResponse.getWeatherData().setTemperature(10);
		WeatherDataResponse weatherDataResponse1 = new WeatherDataResponse();
		weatherDataResponse1.setCity("city1");
		weatherDataResponse1.setLocationData(new LocationData());
		weatherDataResponse1.getLocationData().setLatitude(10);
		weatherDataResponse1.getLocationData().setLongitude(10);
		weatherDataResponse1.setWeatherData(new WeatherData());
		weatherDataResponse1.getWeatherData().setTemperature(20);
		Mockito.when(weatherInfoService.getWeatherDataByCity(Mockito.argThat(matchRequestId("city"))))
				.thenReturn(weatherDataResponse);
		Mockito.when(weatherInfoService.getWeatherDataByCity(Mockito.argThat(matchRequestId("city1"))))
				.thenReturn(weatherDataResponse1);
		// Mockito.when(weatherInfoService.getWeatherDataByCity(Mockito.anyString())).thenReturn(weatherDataResponse);
		PagingResponse response = postService.getAllPosts(pagingRequest);
		Assertions.assertTrue(response.getData() instanceof List);
		Assertions.assertNull(response.getError());
		List<Map<String, PostResponse>> postResponses = (List<Map<String, PostResponse>>) response.getData();
		Assertions.assertTrue(response.getDraw() == pagingRequest.getDraw());
		Assertions.assertTrue(response.getRecordsFiltered() == 2);
		Assertions.assertTrue(response.getRecordsTotal() == 2);
		Assertions.assertEquals(postResponses.get(0).get("post").getUserName(), post.getUserName());
		Assertions.assertEquals(postResponses.get(0).get("post").getPostId(), post.getPostId());
		Assertions.assertEquals(postResponses.get(0).get("post").getCreatedDate(), post.getCreatedDate());
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getLatitude(),
				weatherDataResponse.getLocationData().getLatitude());
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getLongitude(),
				weatherDataResponse.getLocationData().getLongitude());
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getTemperature(), -263);
		Assertions.assertEquals(postResponses.get(1).get("post").getWeatherData().getTemperature(), -253);
		Assertions.assertNull(postResponses.get(0).get("post").getComments());
		Assertions.assertEquals(postResponses.get(1).get("post").getUserName(), post1.getUserName());
		Assertions.assertEquals(postResponses.get(1).get("post").getPostId(), post1.getPostId());
		Assertions.assertEquals(postResponses.get(1).get("post").getCreatedDate(), post1.getCreatedDate());
		Assertions.assertNotNull(postResponses.get(1).get("post").getComments());
		Assertions.assertEquals(postResponses.get(1).get("post").getComments().get(0).getComment(),
				post1.getComments().get(0).getComment());
		Assertions.assertEquals(postResponses.get(1).get("post").getComments().get(1).getComment(),
				post1.getComments().get(1).getComment());
	}

	@Test
	public void testGetAllPostsWhenEmptyResult() {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setLength(10);
		pagingRequest.setStart(0);
		Page<Post> page = new PageImpl<Post>(new ArrayList<>());
		Mockito.when(postDao.getAllPosts(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);
		WeatherDataResponse weatherDataResponse = new WeatherDataResponse();
		weatherDataResponse.setCity("city");
		weatherDataResponse.setLocationData(new LocationData());
		weatherDataResponse.getLocationData().setLatitude(10);
		weatherDataResponse.getLocationData().setLongitude(10);
		weatherDataResponse.setWeatherData(new WeatherData());
		weatherDataResponse.getWeatherData().setTemperature(10);
		Mockito.when(weatherInfoService.getWeatherDataByCity(Mockito.anyString())).thenReturn(weatherDataResponse);
		PagingResponse response = postService.getAllPosts(pagingRequest);
		Assertions.assertTrue(response.getData() instanceof List);
		Assertions.assertNull(response.getError());
		Assertions.assertTrue(response.getDraw() == pagingRequest.getDraw());
		Assertions.assertTrue(response.getRecordsFiltered() == page.getTotalElements());
		Assertions.assertTrue(response.getRecordsTotal() == page.getTotalElements());
	}

	@Test
	public void testGetAllPostsWhenException() {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setLength(10);
		pagingRequest.setStart(0);
		Mockito.when(postDao.getAllPosts(Mockito.anyInt(), Mockito.anyInt())).thenThrow(NullPointerException.class);
		PagingResponse response = postService.getAllPosts(pagingRequest);
		Assertions.assertNull(response.getData());
		Assertions.assertNotNull(response.getError());
	}

	@Test
	public void testGetAllPostsWhenWeatherDataIsNull() {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setLength(10);
		pagingRequest.setStart(0);
		Post post = new Post();
		post.setCity("city");
		post.setCreatedDate(LocalDateTime.now());
		post.setPostText("post");
		post.setUserName("username");
		post.setPostId(BigInteger.ONE);
		Post post1 = new Post();
		post1.setPostId(BigInteger.valueOf(2));
		post1.setCity("city1");
		post1.setCreatedDate(LocalDateTime.now());
		post1.setPostText("post");
		post1.setUserName("username");
		List<Comment> comments = new ArrayList<Comment>();
		post1.setComments(comments);
		Comment comment1 = new Comment();
		comment1.setComment("comment1");
		comment1.setCreatedDate(LocalDateTime.now());
		comment1.setCommentId(BigInteger.ONE);
		comment1.setPostId(post1.getPostId());
		Comment comment2 = new Comment();
		comment2.setCommentId(BigInteger.valueOf(2));
		comment2.setComment("comment2");
		comment2.setCreatedDate(LocalDateTime.now());
		comment2.setPostId(post1.getPostId());
		comments.add(comment1);
		comments.add(comment2);
		Page<Post> page = new PageImpl<Post>(Arrays.asList(post, post1));
		Mockito.when(postDao.getAllPosts(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);
		Mockito.when(weatherInfoService.getWeatherDataByCity(Mockito.anyString())).thenReturn(null);
		PagingResponse response = postService.getAllPosts(pagingRequest);
		Assertions.assertTrue(response.getData() instanceof List);
		Assertions.assertNull(response.getError());
		List<Map<String, PostResponse>> postResponses = (List<Map<String, PostResponse>>) response.getData();
		Assertions.assertTrue(response.getDraw() == pagingRequest.getDraw());
		Assertions.assertTrue(response.getRecordsFiltered() == 2);
		Assertions.assertTrue(response.getRecordsTotal() == 2);
		Assertions.assertEquals(postResponses.get(0).get("post").getUserName(), post.getUserName());
		Assertions.assertEquals(postResponses.get(0).get("post").getPostId(), post.getPostId());
		Assertions.assertEquals(postResponses.get(0).get("post").getCreatedDate(), post.getCreatedDate());
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getLatitude(), 0);
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getLongitude(), 0);
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getTemperature(), 0);
		Assertions.assertNull(postResponses.get(0).get("post").getComments());
		Assertions.assertEquals(postResponses.get(1).get("post").getUserName(), post1.getUserName());
		Assertions.assertEquals(postResponses.get(1).get("post").getPostId(), post1.getPostId());
		Assertions.assertEquals(postResponses.get(1).get("post").getCreatedDate(), post1.getCreatedDate());
		Assertions.assertNotNull(postResponses.get(1).get("post").getComments());
		Assertions.assertEquals(postResponses.get(1).get("post").getComments().get(0).getComment(),
				post1.getComments().get(0).getComment());
		Assertions.assertEquals(postResponses.get(1).get("post").getComments().get(1).getComment(),
				post1.getComments().get(1).getComment());
	}

	@Test
	public void testGetPostByUserName() {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setLength(10);
		pagingRequest.setStart(0);
		Post post = new Post();
		post.setCity("city");
		post.setCreatedDate(LocalDateTime.now());
		post.setPostId(BigInteger.ONE);
		post.setPostText("post");
		post.setUserName("username");
		Page<Post> page = new PageImpl<Post>(Arrays.asList(post));
		Mockito.when(postDao.getPostsByUserName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(page);
		PagingResponse response = postService.getPostsByUserName("username", pagingRequest);
		Assertions.assertTrue(response.getData() instanceof List);
		Assertions.assertNull(response.getError());
		List<PostResponse> postResponses = (List<PostResponse>) response.getData();
		Assertions.assertTrue(response.getDraw() == pagingRequest.getDraw());
		Assertions.assertTrue(response.getRecordsFiltered() == page.getTotalElements());
		Assertions.assertTrue(response.getRecordsTotal() == page.getTotalElements());
		Assertions.assertEquals(postResponses.get(0).getUserName(), post.getUserName());
		Assertions.assertEquals(postResponses.get(0).getPostId(), post.getPostId());
		Assertions.assertEquals(postResponses.get(0).getCreatedDate(), post.getCreatedDate());
		Assertions.assertNull(postResponses.get(0).getWeatherData());
		Assertions.assertNull(postResponses.get(0).getComments());
	}

	@Test
	public void testGetPostByUserNameWhenException() {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setLength(10);
		pagingRequest.setStart(0);
		Mockito.when(postDao.getPostsByUserName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
				.thenThrow(NullPointerException.class);
		PagingResponse response = postService.getPostsByUserName("username", pagingRequest);
		Assertions.assertNull(response.getData());
		Assertions.assertNotNull(response.getError());
	}

	@Test
	public void testGetPostByUserNameWhenResultEmpty() {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setLength(10);
		pagingRequest.setStart(0);
		Page<Post> page = new PageImpl<Post>(new ArrayList<>());
		Mockito.when(postDao.getPostsByUserName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(page);
		PagingResponse response = postService.getPostsByUserName("username", pagingRequest);
		Assertions.assertTrue(response.getData() instanceof List);
		Assertions.assertNull(response.getError());
		List<PostResponse> postResponses = (List<PostResponse>) response.getData();
		Assertions.assertTrue(postResponses.isEmpty());
		Assertions.assertTrue(response.getDraw() == pagingRequest.getDraw());
		Assertions.assertTrue(response.getRecordsFiltered() == page.getTotalElements());
		Assertions.assertTrue(response.getRecordsTotal() == page.getTotalElements());
	}

}
