/**
 *
 */
package com.loyaltyone.postsandcomments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.loyaltyone.postsandcomments.dao.PostDao;
import com.loyaltyone.postsandcomments.weatherinfo.service.WeatherInfoService;

/**
 * @author muffa
 *
 */
@SpringBootTest
public class PostServiceTest {

	@MockBean
	private PostDao postDao;

	@MockBean
	private WeatherInfoService weatherInfoService;

	@Autowired
	private PostService postService;

}
