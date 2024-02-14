package com.hotel.services;

import java.util.List;

import com.hotel.entities.Hotel;

public interface HotelService {
	//create
	Hotel create(Hotel hotel);
	
	
	//getAll
	List<Hotel> getAll();
	
	
	//get Single
	Hotel get(String id);
}
