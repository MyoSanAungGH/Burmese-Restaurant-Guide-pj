package com.BScampOTJ.BRG.service;

import java.util.List;

import com.BScampOTJ.BRG.entity.Review;


public interface ReviewService {

	//Get all review
	public List<Review> getAll();
	
	//Get review by id
	public Review get(int id);
	
	public Review create(Review review);
	
	public Review update(int id, Review review);
	
	public boolean delete(int id);
	
	public List<Review> getByUserId(int userId);
	
	public List<Review> getAllByRestaurantId(int restaurantId);
}
