/**
 *
 */
package com.loyaltyone.postsandcomments.service;

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

import com.loyaltyone.postsandcomments.dao.PostDao;
import com.loyaltyone.postsandcomments.dao.entity.Post;
import com.loyaltyone.postsandcomments.dao.entity.PostCommentPK;
import com.loyaltyone.postsandcomments.dao.entity.PostCommentView;
import com.loyaltyone.postsandcomments.model.PostRequest;
import com.loyaltyone.postsandcomments.model.PostResponse;
import com.loyaltyone.postsandcomments.model.Response;
import com.loyaltyone.postsandcomments.model.pagination.PagingRequest;
import com.loyaltyone.postsandcomments.model.pagination.PagingResponse;
import com.loyaltyone.postsandcomments.weatherinfo.model.LocationData;
import com.loyaltyone.postsandcomments.weatherinfo.model.WeatherData;
import com.loyaltyone.postsandcomments.weatherinfo.model.WeatherDataResponse;
import com.loyaltyone.postsandcomments.weatherinfo.service.WeatherInfoService;

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
		PostCommentPK postCommentPK = new PostCommentPK();
		postCommentPK.setCommentId(BigInteger.ONE);
		postCommentPK.setPostId(BigInteger.ONE);
		PostCommentView post = new PostCommentView();
		post.setCity("city");
		post.setCreatedDate(LocalDateTime.now());
		post.setPostText("post");
		post.setUserName("username");
		post.setId(postCommentPK);
		PostCommentView post1 = new PostCommentView();
		postCommentPK = new PostCommentPK();
		postCommentPK.setCommentId(BigInteger.ONE);
		postCommentPK.setPostId(BigInteger.valueOf(2));
		post1.setCity("city1");
		post1.setCreatedDate(LocalDateTime.now());
		post1.setPostText("post");
		post1.setUserName("username");
		post1.setComment("comment1");
		post1.setCommentCreatedDate(post1.getCreatedDate());
		post1.setId(postCommentPK);
		PostCommentView post2 = new PostCommentView();
		postCommentPK = new PostCommentPK();
		postCommentPK.setCommentId(BigInteger.valueOf(2));
		postCommentPK.setPostId(BigInteger.valueOf(2));
		post2.setCity("city1");
		post2.setCreatedDate(LocalDateTime.now());
		post2.setPostText("post");
		post2.setUserName("username");
		post2.setComment("comment2");
		post2.setCommentCreatedDate(post2.getCreatedDate());
		post2.setId(postCommentPK);
		Page<PostCommentView> page = new PageImpl<PostCommentView>(Arrays.asList(post, post1, post2));
		Mockito.when(postDao.getAllPosts(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);
		Mockito.when(postDao.getPostCount()).thenReturn(2L);
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
		Assertions.assertEquals(postResponses.get(0).get("post").getPostId(), post.getId().getPostId());
		Assertions.assertEquals(postResponses.get(0).get("post").getCreatedDate(), post.getCreatedDate());
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getLatitude(),
				weatherDataResponse.getLocationData().getLatitude());
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getLongitude(),
				weatherDataResponse.getLocationData().getLongitude());
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getTemperature(), -263);
		Assertions.assertEquals(postResponses.get(1).get("post").getWeatherData().getTemperature(), -253);
		Assertions.assertNull(postResponses.get(0).get("post").getComments());
		Assertions.assertEquals(postResponses.get(1).get("post").getUserName(), post1.getUserName());
		Assertions.assertEquals(postResponses.get(1).get("post").getPostId(), post1.getId().getPostId());
		Assertions.assertEquals(postResponses.get(1).get("post").getCreatedDate(), post1.getCreatedDate());
		Assertions.assertNotNull(postResponses.get(1).get("post").getComments());
		Assertions.assertEquals(postResponses.get(1).get("post").getComments().get(0).getComment(), post1.getComment());
		Assertions.assertEquals(postResponses.get(1).get("post").getComments().get(1).getComment(), post2.getComment());
	}

	@Test
	public void testGetAllPostsWhenEmptyResult() {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setLength(10);
		pagingRequest.setStart(0);
		Page<PostCommentView> page = new PageImpl<PostCommentView>(new ArrayList<>());
		Mockito.when(postDao.getAllPosts(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);
		Mockito.when(postDao.getPostCount()).thenReturn(0L);
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
	public void testGetAllPostsWhenWeatherDataisNull() {
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setDraw(1);
		pagingRequest.setLength(10);
		pagingRequest.setStart(0);
		PostCommentPK postCommentPK = new PostCommentPK();
		postCommentPK.setCommentId(BigInteger.ONE);
		postCommentPK.setPostId(BigInteger.ONE);
		PostCommentView post = new PostCommentView();
		post.setCity("city");
		post.setCreatedDate(LocalDateTime.now());
		post.setPostText("post");
		post.setUserName("username");
		post.setId(postCommentPK);
		PostCommentView post1 = new PostCommentView();
		postCommentPK = new PostCommentPK();
		postCommentPK.setCommentId(BigInteger.ONE);
		postCommentPK.setPostId(BigInteger.valueOf(2));
		post1.setCity("city");
		post1.setCreatedDate(LocalDateTime.now());
		post1.setPostText("post");
		post1.setUserName("username");
		post1.setComment("comment1");
		post1.setCommentCreatedDate(post1.getCreatedDate());
		post1.setId(postCommentPK);
		PostCommentView post2 = new PostCommentView();
		postCommentPK = new PostCommentPK();
		postCommentPK.setCommentId(BigInteger.valueOf(2));
		postCommentPK.setPostId(BigInteger.valueOf(2));
		post2.setCity("city");
		post2.setCreatedDate(LocalDateTime.now());
		post2.setPostText("post");
		post2.setUserName("username");
		post2.setComment("comment2");
		post2.setCommentCreatedDate(post2.getCreatedDate());
		post2.setId(postCommentPK);
		Page<PostCommentView> page = new PageImpl<PostCommentView>(Arrays.asList(post, post1, post2));
		Mockito.when(postDao.getAllPosts(Mockito.anyInt(), Mockito.anyInt())).thenReturn(page);
		Mockito.when(postDao.getPostCount()).thenReturn(2L);
		Mockito.when(weatherInfoService.getWeatherDataByCity(Mockito.anyString())).thenReturn(null);
		PagingResponse response = postService.getAllPosts(pagingRequest);
		Assertions.assertTrue(response.getData() instanceof List);
		Assertions.assertNull(response.getError());
		List<Map<String, PostResponse>> postResponses = (List<Map<String, PostResponse>>) response.getData();
		Assertions.assertTrue(response.getDraw() == pagingRequest.getDraw());
		Assertions.assertTrue(response.getRecordsFiltered() == 2);
		Assertions.assertTrue(response.getRecordsTotal() == 2);
		Assertions.assertEquals(postResponses.get(0).get("post").getUserName(), post.getUserName());
		Assertions.assertEquals(postResponses.get(0).get("post").getPostId(), post.getId().getPostId());
		Assertions.assertEquals(postResponses.get(0).get("post").getCreatedDate(), post.getCreatedDate());
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getLatitude(), 0);
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getLongitude(), 0);
		Assertions.assertEquals(postResponses.get(0).get("post").getWeatherData().getTemperature(), 0);
		Assertions.assertNull(postResponses.get(0).get("post").getComments());
		Assertions.assertEquals(postResponses.get(1).get("post").getUserName(), post1.getUserName());
		Assertions.assertEquals(postResponses.get(1).get("post").getPostId(), post1.getId().getPostId());
		Assertions.assertEquals(postResponses.get(1).get("post").getCreatedDate(), post1.getCreatedDate());
		Assertions.assertNotNull(postResponses.get(1).get("post").getComments());
		Assertions.assertEquals(postResponses.get(1).get("post").getComments().get(0).getComment(), post1.getComment());
		Assertions.assertEquals(postResponses.get(1).get("post").getComments().get(1).getComment(), post2.getComment());
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
