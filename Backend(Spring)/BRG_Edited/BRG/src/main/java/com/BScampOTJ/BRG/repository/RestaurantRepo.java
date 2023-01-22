package com.BScampOTJ.BRG.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.BScampOTJ.BRG.entity.DivisionMState;
import com.BScampOTJ.BRG.entity.Restaurant;

@EnableJpaRepositories
public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {
	
	public List<Restaurant> findByRestaurantName(String name);
	
	public List<Restaurant> findAllBydivisionMstate(DivisionMState divMstateId);
	
	
}
