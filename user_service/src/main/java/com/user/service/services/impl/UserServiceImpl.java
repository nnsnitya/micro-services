package com.user.service.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		// Generate unique userid
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// implement RATING SERVICE call using RestTemplate
		return userRepository.findAll();
	}

	//get single user
	@Override
	public User getUser(String userId) {
		// get user from db with the help of userRepository
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !!: "+userId));

		//fetch rating of the above user from the RATING SERVICE
		Rating[] ratingsByUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+userId, Rating[].class);
		logger.info("{} ", ratingsByUser);
		
		List<Rating> finalRatingList = Arrays.stream(ratingsByUser).map(r -> {
			//api call to hotel service to get the hotel 
			ResponseEntity<Hotel> hotelEntt = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+r.getHotelId(), Hotel.class);
			
			Hotel hotel = hotelEntt.getBody();
			logger.info("Response Status Code: {}"+hotelEntt.getStatusCode());
			
			//set the hotel to rating
			r.setHotel(hotel);
			
			//return the rating
			return r;
		}).collect(Collectors.toList());
		user.setRatings(finalRatingList);
		return user;
	}

	
	
}
