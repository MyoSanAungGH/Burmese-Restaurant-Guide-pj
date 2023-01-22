package com.BScampOTJ.BRG.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.BScampOTJ.BRG.entity.Review;

@EnableJpaRepositories
public interface ReviewRepo extends JpaRepository<Review, Integer> {

	public List<Review> findByUserId(int userId);
	
	public List<Review> findAllByRestaurantId(int restaurantId);
}
