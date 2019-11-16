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

	@Scheduled(cron = "${weather.cron.expression}")
	public void clearMapAsCache() {
		Map mapAsCache = context.getBean("weatherMap", Map.class);
		mapAsCache.size();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@Qualifier("weatherMap")
	public Map<String, String> weatherMap() {
		return Collections.synchronizedMap(new PassiveExpiringMap<>(timeToCache));
	}
}
