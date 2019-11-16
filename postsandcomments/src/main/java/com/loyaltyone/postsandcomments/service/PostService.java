/**
 *
 */
package com.loyaltyone.postsandcomments.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.loyaltyone.postsandcomments.dao.PostDao;
import com.loyaltyone.postsandcomments.dao.entity.Post;
import com.loyaltyone.postsandcomments.posts.model.PostRequest;
import com.loyaltyone.postsandcomments.posts.model.PostResponse;
import com.loyaltyone.postsandcomments.posts.model.Response;
import com.loyaltyone.postsandcomments.posts.model.WeatherData;
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
		Post post = postDao.savePost(postRequest);
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

	public List<PostResponse> getAllPosts(int page, int pageSize) {
		Page<Post> allPosts = postDao.getAllPosts(page, pageSize);
		List<PostResponse> postResponses = new ArrayList<PostResponse>();
		Map<String, List<PostResponse>> responseByCity = new HashMap<String, List<PostResponse>>();
		for (Post post : allPosts) {
			String cityName = post.getCity().toLowerCase();
			PostResponse postResponse = new PostResponse();
			postResponse.setCity(post.getCity());
			postResponse.setCreatedDate(post.getCreatedDate());
			postResponse.setPost(post.getPostText());
			postResponse.setPostId(post.getPostId());
			postResponse.setUserName(post.getUserName());
			postResponses.add(postResponse);
			if (!responseByCity.containsKey(cityName)) {
				List<PostResponse> list = new ArrayList<PostResponse>();
				responseByCity.put(cityName, list);
				list.add(postResponse);
			} else {
				responseByCity.get(cityName).add(postResponse);
			}
		}
		// parallely retrive weather data
		responseByCity.keySet().parallelStream().forEach(city -> {
			WeatherDataResponse weatherDataResponse = weatherInfoService.getWeatherDataByCity(city);
			WeatherData weatherData = new WeatherData();
			weatherData.setLatitude(weatherDataResponse.getLocationData().getLatitude());
			weatherData.setLongitude(weatherDataResponse.getLocationData().getLongitude());
			weatherData
					.setTemperature((int) Math.round(weatherDataResponse.getWeatherData().getTemperature() - 273.15));
			responseByCity.get(city).parallelStream().forEach(postResponse -> {
				postResponse.setWeatherData(weatherData);
			});
		});
		return postResponses;
	}

	public Page<Post> getPostsByUserName(String userName, int page, int pageSize) {
		return postDao.getPostsByUserName(userName, page, pageSize);
	}
}
