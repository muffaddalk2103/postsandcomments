/**
 *
 */
package com.mk.postsandcomments.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mk.postsandcomments.dao.PostDao;
import com.mk.postsandcomments.dao.entity.Comment;
import com.mk.postsandcomments.dao.entity.Post;
import com.mk.postsandcomments.model.CommentResponse;
import com.mk.postsandcomments.model.PostRequest;
import com.mk.postsandcomments.model.PostResponse;
import com.mk.postsandcomments.model.Response;
import com.mk.postsandcomments.model.WeatherData;
import com.mk.postsandcomments.model.pagination.PagingRequest;
import com.mk.postsandcomments.model.pagination.PagingResponse;
import com.mk.postsandcomments.weatherinfo.model.WeatherDataResponse;
import com.mk.postsandcomments.weatherinfo.service.WeatherInfoService;

/**
 * @author muffa
 *
 */
@Service
public class PostService {
	private Logger logger = LoggerFactory.getLogger(PostService.class);
	private PostDao postDao;
	private WeatherInfoService weatherInfoService;

	/**
	 * @param postDao
	 * @param weatherInfoService
	 */
	@Autowired
	public PostService(PostDao postDao, WeatherInfoService weatherInfoService) {
		super();
		this.postDao = postDao;
		this.weatherInfoService = weatherInfoService;
	}

	/**
	 * Saves post to database
	 *
	 * @param postRequest post to be stored in database
	 * @return {@link Response}
	 */
	public Response addPost(PostRequest postRequest) {
		logger.info("inside addPost");
		if (logger.isDebugEnabled()) {
			logger.debug("post request " + postRequest);
		}
		Response response = new Response();
		try {
			Post post = savePost(postRequest);
			PostResponse postResponse = new PostResponse();
			postResponse.setCity(post.getCity());
			postResponse.setCreatedDate(post.getCreatedDate());
			postResponse.setPost(post.getPostText());
			postResponse.setPostId(post.getPostId());
			postResponse.setUserName(post.getUserName());
			response.setData(postResponse);
			response.setSuccess(true);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setMessages(Arrays.asList("Unable to save post, please try after sometime."));
			response.setSuccess(false);
		}
		return response;
	}

	/**
	 * Returns posts in paginated fashion
	 *
	 * @param pagingRequest page request received from the caller
	 * @return {@link PagingResponse}
	 */
	@Transactional
	public PagingResponse getAllPosts(PagingRequest pagingRequest) {
		logger.info("inside getAllPosts");
		if (logger.isDebugEnabled()) {
			logger.debug("paging request " + pagingRequest);
		}
		try {
			Page<Post> allPost = postDao.getAllPosts(pagingRequest.getStart() / pagingRequest.getLength(),
					pagingRequest.getLength());
			PagingResponse pagingResponse = new PagingResponse();
			pagingResponse.setDraw(pagingRequest.getDraw());
			pagingResponse.setRecordsTotal(allPost.getTotalElements());
			pagingResponse.setData(getPostResponses(allPost));
			pagingResponse.setRecordsFiltered(allPost.getTotalElements());
			return pagingResponse;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			PagingResponse pagingResponse = new PagingResponse();
			pagingResponse.setError("Unable to retrieve posts");
			return pagingResponse;
		}
	}

	/**
	 * Converts database entities to json model at the same time gathers weatehr
	 * data
	 *
	 * @param page data to be converted
	 * @return returns a list of {@link Map} as required by the UI
	 */
	private List<Map<String, PostResponse>> getPostResponses(Page<Post> page) {
		Map<String, List<PostResponse>> responseByCity = new HashMap<String, List<PostResponse>>();
		List<Map<String, PostResponse>> postResponses = new ArrayList<Map<String, PostResponse>>();
		for (Post post : page) {
			String cityName = post.getCity().toLowerCase();
			PostResponse postResponse = new PostResponse();
			postResponse.setCity(post.getCity());
			postResponse.setCreatedDate(post.getCreatedDate());
			postResponse.setPost(post.getPostText());
			postResponse.setPostId(post.getPostId());
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
			if (post.getComments() != null && !post.getComments().isEmpty()) {
				List<CommentResponse> commentResponses = new ArrayList<CommentResponse>();
				postResponse.setComments(commentResponses);
				for (Comment comment : post.getComments()) {
					CommentResponse commentResponse = new CommentResponse();
					commentResponse.setComment(comment.getComment());
					commentResponse.setCreatedDate(comment.getCreatedDate());
					commentResponse.setPostId(post.getPostId());
					commentResponses.add(commentResponse);
				}
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

	/**
	 * Converts database entities to json model
	 *
	 * @param page data to be converted
	 * @return returns a list of {@link PostResponse} as required by the UI
	 */
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

	/**
	 * Returns posts filtered by user name
	 *
	 * @param userName      user whose posts are to be returned
	 * @param pagingRequest page request received from the caller
	 * @return {@link PagingResponse}
	 */
	public PagingResponse getPostsByUserName(String userName, PagingRequest pagingRequest) {
		logger.info("inside getPostsByUserName");
		if (logger.isDebugEnabled()) {
			logger.debug("paging request " + pagingRequest + " user name " + userName);
		}
		try {
			Page<Post> allPosts = postDao.getPostsByUserName(userName,
					pagingRequest.getStart() / pagingRequest.getLength(), pagingRequest.getLength());
			PagingResponse pagingResponse = new PagingResponse();
			pagingResponse.setDraw(pagingRequest.getDraw());
			pagingResponse.setRecordsFiltered(allPosts.getTotalElements());
			pagingResponse.setRecordsTotal(allPosts.getTotalElements());
			pagingResponse.setData(getPostResponsesWithoutWeatherData(allPosts));
			return pagingResponse;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			PagingResponse pagingResponse = new PagingResponse();
			pagingResponse.setError("Unable to retrieve posts");
			return pagingResponse;
		}
	}

	/**
	 * saves post to database
	 *
	 * @param postRequest post to be saved
	 * @return {@link Post}
	 */
	@Transactional
	private Post savePost(PostRequest postRequest) {
		logger.info("inside savePost");
		Post post = new Post();
		post.setCity(postRequest.getCity());
		post.setPostText(postRequest.getPost());
		post.setUserName(postRequest.getUserName());
		return postDao.savePost(post);
	}
}
