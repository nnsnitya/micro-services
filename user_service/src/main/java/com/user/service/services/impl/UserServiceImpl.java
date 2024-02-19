package com.user.service.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
		//http://localhost:8083/ratings/users/8d509b7e-e5e6-4df8-b579-c4c5958211af
		ArrayList<Rating> ratingsByUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+userId, ArrayList.class);
		logger.info("{} ", ratingsByUser);
		
		user.setRatings(ratingsByUser);
		return user;
	}

	
	
}
