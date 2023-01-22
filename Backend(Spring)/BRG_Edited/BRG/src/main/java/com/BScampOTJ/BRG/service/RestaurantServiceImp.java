package com.BScampOTJ.BRG.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BScampOTJ.BRG.entity.DivisionMState;
//import com.BScampOTJ.BRG.entity.DivisionMState;
import com.BScampOTJ.BRG.entity.Restaurant;
import com.BScampOTJ.BRG.repository.RestaurantRepo;

@Service
public class RestaurantServiceImp implements RestaurantService {
	
	@Autowired
	RestaurantRepo restaurantRepo;

	@Override
	public List<Restaurant> getAll() {
		return restaurantRepo.findAll();
	}

	@Override
	public Restaurant get(int id) {
		return restaurantRepo.findById(id).orElse(null);
	}
	

	@Override
	public Restaurant create(Restaurant restaurant) {
		restaurant.setCreatedAt(LocalDateTime.now());
		return restaurantRepo.save(restaurant);
	}

	@Override
	public Restaurant update(int id, Restaurant restaurant) {
		Restaurant updateRest = this.get(id);
		if(updateRest != null) {
			updateRest.setRestaurantName(restaurant.getRestaurantName());
			updateRest.setAddress(restaurant.getAddress());
			updateRest.setOverview(restaurant.getOverview());
			updateRest.setPhone(restaurant.getPhone());
			updateRest.setUpdatedAt(LocalDateTime.now());
			updateRest.setPicPath(restaurant.getPicPath());
			restaurantRepo.save(updateRest);
		}
			
		return updateRest;
	}

	
	@Override
	public boolean delete(int id) {
		Restaurant delRest= this.get(id);
		if(delRest != null) {
			restaurantRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<Restaurant> getByRestaurantName(String name ) {
		return restaurantRepo.findByRestaurantName(name);
	}


	@Override
	public List<Restaurant> getAllByDivisionMStateId(DivisionMState divMstateId) {
		return restaurantRepo.findAllBydivisionMstate(divMstateId);
	}
}

















