package com.rating.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rating.entities.Rating;
import com.rating.services.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	//create rating
	@PostMapping
	public ResponseEntity<Rating> create(@RequestBody Rating rating){
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
	}
	
	//getAll
	@GetMapping
	public ResponseEntity<List<Rating>> getRatings() {
		return ResponseEntity.ok(ratingService.getRatings());
	}
	
	//get all by userid
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable String userId) {
		return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
	}
	
	//get all by hotelId
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>> getRatingByHotelId(@PathVariable String hotelId) {
		System.out.println("rating by hotel called");
		return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
	}

}
