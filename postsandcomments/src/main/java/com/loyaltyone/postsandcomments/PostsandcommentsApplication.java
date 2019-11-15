package com.loyaltyone.postsandcomments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PostsandcommentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsandcommentsApplication.class, args);
	}

}
