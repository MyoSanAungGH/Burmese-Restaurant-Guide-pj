package com.BScampOTJ.BRG.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.BScampOTJ.BRG.entity.Menu;
import com.BScampOTJ.BRG.entity.Restaurant;

@EnableJpaRepositories
public interface MenuRepo extends JpaRepository<Menu , Integer> {

	public List<Menu> findAllByRestaurantId(int restaurantId);
}
