package com.BScampOTJ.BRG.service;

import java.util.List;

import com.BScampOTJ.BRG.entity.Menu;
import com.BScampOTJ.BRG.entity.Restaurant;


public interface MenuService {

	public List<Menu> getAll();

	public Menu get(int id);

	public Menu create(Menu menu);

	public Menu update(int id, Menu menu);

	public boolean delete(int id);

	public List<Menu> getAllByRestaurantId(int restaurantId);
}
