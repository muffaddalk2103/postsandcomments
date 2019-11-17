package com.loyaltyone.postsandcomments;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
public class PostsandcommentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsandcommentsApplication.class, args);
	}

	@Autowired
	private ApplicationContext context;

	@Value("${weather.cache.time}")
	private long timeToCache;

	/**
	 * Clears expired weather data every 10 mins by calling size() on
	 * PassiveExpiringMap. Data is cleared every 10 mins as weather API refreshed
	 * it's own data every 10 mins
	 */
	@Scheduled(cron = "${weather.cron.expression}")
	public void clearMapAsCache() {
		Map mapAsCache = context.getBean("weatherMap", Map.class);
		mapAsCache.size();
	}

	/**
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Returns a synchronized version of PassiveExpiringMap
	 * 
	 * @return PassiveExpiringMap
	 */
	@Bean
	@Qualifier("weatherMap")
	public Map<String, String> weatherMap() {
		return Collections.synchronizedMap(new PassiveExpiringMap<>(timeToCache));
	}
}
