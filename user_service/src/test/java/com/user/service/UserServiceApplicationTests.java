package com.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.user.service.entities.Rating;
import com.user.service.external.services.RatingService;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RatingService ratingService;
	
	@Test
	void createRating() {
		Rating rating = Rating.builder().rating(10).ratingId("").hotelId("").feedback("this is created using feign client").build();
		ResponseEntity<Rating> ratingRespEntt = ratingService.createRating(rating);
		System.out.println(ratingRespEntt.getBody().getFeedback());
		System.out.println(ratingRespEntt.getStatusCode());
		
		System.out.println("new rating created");
	}
}
