package com.BScampOTJ.BRG.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BScampOTJ.BRG.entity.Menu;
import com.BScampOTJ.BRG.entity.Restaurant;
import com.BScampOTJ.BRG.repository.MenuRepo;

@Service
public class MenuServiceImp implements MenuService {
	
	@Autowired
	MenuRepo menuRepo;

	@Override
	public List<Menu> getAll() {
		return menuRepo.findAll();
	}

	@Override
	public Menu get(int id) {
		return menuRepo.findById(id).orElse(null);
	}

	@Override
	public Menu create(Menu menu) {
		return menuRepo.save(menu);
	}

	@Override
	public Menu update(int id, Menu menu) {
		Menu updateMenu = this.get(id);
		if(updateMenu == null){
			return null;
		}
		updateMenu.setName(menu.getName());
		updateMenu.setPrice(menu.getPrice());
		return menuRepo.save(updateMenu);
	}

	@Override
	public boolean delete(int id) {
		Menu delMenu = this.get(id);
		if(delMenu == null) {
			return false;
		}
		menuRepo.delete(delMenu);
		return true;
	}

	@Override
	public List<Menu> getAllByRestaurantId(int restaurantId) {
		return menuRepo.findAllByRestaurantId(restaurantId);
	}

	

}
