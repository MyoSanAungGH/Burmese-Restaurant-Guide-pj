package com.BScampOTJ.BRG.service;

import java.util.List;

import com.BScampOTJ.BRG.entity.DivisionMState;
import com.BScampOTJ.BRG.entity.Restaurant;

public interface RestaurantService {

	public List<Restaurant> getAll();
	
	public Restaurant get(int id);
	
	public Restaurant create(Restaurant restaurant);
	
	public Restaurant update(int id, Restaurant restaurant);
	
	public boolean delete(int id);
	
	public List<Restaurant> getByRestaurantName(String name );
	
	public List<Restaurant> getAllByDivisionMStateId(DivisionMState divMstateId);


	
	
}
