/**
 *
 */
package com.loyaltyone.postsandcomments.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.loyaltyone.postsandcomments.dao.PostDao;
import com.loyaltyone.postsandcomments.dao.entity.Post;
import com.loyaltyone.postsandcomments.dao.entity.PostCommentView;
import com.loyaltyone.postsandcomments.model.CommentResponse;
import com.loyaltyone.postsandcomments.model.PostRequest;
import com.loyaltyone.postsandcomments.model.PostResponse;
import com.loyaltyone.postsandcomments.model.Response;
import com.loyaltyone.postsandcomments.model.WeatherData;
import com.loyaltyone.postsandcomments.model.pagination.PagingRequest;
import com.loyaltyone.postsandcomments.model.pagination.PagingResponse;
import com.loyaltyone.postsandcomments.weatherinfo.model.WeatherDataResponse;
import com.loyaltyone.postsandcomments.weatherinfo.service.WeatherInfoService;

/**
 * @author muffa
 *
 */
@Service
public class PostService {
	private PostDao postDao;
	private WeatherInfoService weatherInfoService;

	/**
	 * @param postDao
	 */
	@Autowired
	public PostService(PostDao postDao, WeatherInfoService weatherInfoService) {
		super();
		this.postDao = postDao;
		this.weatherInfoService = weatherInfoService;
	}

	public Response addPost(PostRequest postRequest) {
		Post post = new Post();
		post.setCity(postRequest.getCity());
		post.setPostText(postRequest.getPost());
		post.setUserName(postRequest.getUserName());
		post = postDao.savePost(post);
		PostResponse postResponse = new PostResponse();
		postResponse.setCity(post.getCity());
		postResponse.setCreatedDate(post.getCreatedDate());
		postResponse.setPost(post.getPostText());
		postResponse.setPostId(post.getPostId());
		postResponse.setUserName(post.getUserName());
		Response response = new Response();
		response.setData(postResponse);
		response.setSuccess(true);
		return response;
	}

	public PagingResponse getAllPosts(PagingRequest pagingRequest) {
		Page<PostCommentView> allPosts = postDao.getAllPosts(pagingRequest.getStart() / pagingRequest.getLength(),
				pagingRequest.getLength());
		return getPagingResponse(pagingRequest, allPosts);
	}

	private PagingResponse getPagingResponse(PagingRequest pagingRequest, Page<Post> page,
			Function<Page<Post>, Object> function) {
		PagingResponse pagingResponse = new PagingResponse();
		pagingResponse.setDraw(pagingRequest.getDraw());
		pagingResponse.setRecordsFiltered(page.getTotalElements());
		pagingResponse.setRecordsTotal(page.getTotalElements());
		pagingResponse.setData(function.apply(page));
		return pagingResponse;
	}

	private PagingResponse getPagingResponse(PagingRequest pagingRequest, Page<PostCommentView> page) {
		PagingResponse pagingResponse = new PagingResponse();
		pagingResponse.setDraw(pagingRequest.getDraw());
		long recordCount = postDao.getPostCount();
		pagingResponse.setRecordsFiltered(recordCount);
		pagingResponse.setRecordsTotal(recordCount);
		pagingResponse.setData(getPostResponses(page));
		return pagingResponse;
	}

	private List<Map<String, PostResponse>> getPostResponses(Page<PostCommentView> page) {
		Map<String, List<PostResponse>> responseByCity = new HashMap<String, List<PostResponse>>();
		Map<BigInteger, PostResponse> responseById = new HashMap<BigInteger, PostResponse>();
		List<Map<String, PostResponse>> postResponses = new ArrayList<Map<String, PostResponse>>();
		for (PostCommentView post : page) {
			String cityName = post.getCity().toLowerCase();
			PostResponse postResponse = null;
			if (responseById.containsKey(post.getId().getPostId())) {
				postResponse = responseById.get(post.getId().getPostId());
			} else {
				postResponse = new PostResponse();
				responseById.put(post.getId().getPostId(), postResponse);
				postResponse.setCity(post.getCity());
				postResponse.setCreatedDate(post.getCreatedDate());
				postResponse.setPost(post.getPostText());
				postResponse.setPostId(post.getId().getPostId());
				postResponse.setUserName(post.getUserName());
				Map<String, PostResponse> map = new HashMap<String, PostResponse>();
				map.put("post", postResponse);
				postResponses.add(map);
				if (!responseByCity.containsKey(cityName)) {
					List<PostResponse> list = new ArrayList<PostResponse>();
					responseByCity.put(cityName, list);
					list.add(postResponse);
				} else {
					responseByCity.get(cityName).add(postResponse);
				}
			}
			if (post.getComment() != null) {
				List<CommentResponse> commentResponses = null;
				if (postResponse.getComments() == null) {
					commentResponses = new ArrayList<CommentResponse>();
				} else {
					commentResponses = postResponse.getComments();
				}
				postResponse.setComments(commentResponses);
				CommentResponse commentResponse = new CommentResponse();
				commentResponse.setComment(post.getComment());
				commentResponse.setCreatedDate(post.getCommentCreatedDate());
				commentResponse.setPostId(post.getId().getPostId());
				commentResponses.add(commentResponse);

			}
		}
		// parallely retrive weather data
		responseByCity.keySet().parallelStream().forEach(city -> {
			WeatherDataResponse weatherDataResponse = weatherInfoService.getWeatherDataByCity(city);
			WeatherData weatherData = new WeatherData();
			if (weatherDataResponse != null) {
				weatherData.setLatitude(weatherDataResponse.getLocationData().getLatitude());
				weatherData.setLongitude(weatherDataResponse.getLocationData().getLongitude());
				weatherData.setTemperature(
						(int) Math.round(weatherDataResponse.getWeatherData().getTemperature() - 273.15));
			}
			responseByCity.get(city).parallelStream().forEach(postResponse -> {
				postResponse.setWeatherData(weatherData);
			});
		});
		return postResponses;
	}

	private List<PostResponse> getPostResponsesWithoutWeatherData(Page<Post> page) {
		List<PostResponse> postResponses = new ArrayList<PostResponse>();
		for (Post post : page) {
			PostResponse postResponse = new PostResponse();
			postResponse.setCity(post.getCity());
			postResponse.setCreatedDate(post.getCreatedDate());
			postResponse.setPost(post.getPostText());
			postResponse.setPostId(post.getPostId());
			postResponse.setUserName(post.getUserName());
			postResponses.add(postResponse);
		}
		return postResponses;
	}

	public PagingResponse getPostsByUserName(String userName, PagingRequest pagingRequest) {
		Page<Post> allPosts = postDao.getPostsByUserName(userName, pagingRequest.getStart() / pagingRequest.getLength(),
				pagingRequest.getLength());
		return getPagingResponse(pagingRequest, allPosts, this::getPostResponsesWithoutWeatherData);
	}
}
