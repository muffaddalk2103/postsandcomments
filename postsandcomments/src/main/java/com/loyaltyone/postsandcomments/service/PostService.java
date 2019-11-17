/**
 *
 */
package com.loyaltyone.postsandcomments.service;

import java.math.BigInteger;
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
	@Transactional
	public Response addPost(PostRequest postRequest) {
		logger.info("inside addPost");
		if (logger.isDebugEnabled()) {
			logger.debug("post request " + postRequest);
		}
		Response response = new Response();
		try {
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
	public PagingResponse getAllPosts(PagingRequest pagingRequest) {
		logger.info("inside getAllPosts");
		if (logger.isDebugEnabled()) {
			logger.debug("paging request " + pagingRequest);
		}
		try {
			Page<PostCommentView> allPosts = postDao.getAllPosts(pagingRequest.getStart() / pagingRequest.getLength(),
					pagingRequest.getLength());
			PagingResponse pagingResponse = new PagingResponse();
			pagingResponse.setDraw(pagingRequest.getDraw());
			long recordCount = postDao.getPostCount();
			pagingResponse.setRecordsFiltered(recordCount);
			pagingResponse.setRecordsTotal(recordCount);
			pagingResponse.setData(getPostResponses(allPosts));
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
}
