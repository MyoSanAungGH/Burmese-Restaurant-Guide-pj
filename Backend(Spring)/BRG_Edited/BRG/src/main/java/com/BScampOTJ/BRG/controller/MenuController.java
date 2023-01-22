package com.BScampOTJ.BRG.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BScampOTJ.BRG.entity.Menu;
import com.BScampOTJ.BRG.service.MenuService;
import com.BScampOTJ.BRG.service.RestaurantService;

@RestController
@RequestMapping("/api")
public class MenuController {
	
	@Autowired
	MenuService menuServiece;
	
	@Autowired
	RestaurantService restService;
	
	@GetMapping("/menu/{id}")
	public Menu getMenuByid(@PathVariable int id) {
		return menuServiece.get(id);
	}
	
	
	@GetMapping("/menu/restaurant/{restaurant_id}")
	public List<Menu> getAllMenuByRestaurantId(@PathVariable("restaurant_id") int restaurantId){
		return menuServiece.getAllByRestaurantId(restaurantId);
	}
	
	@PostMapping("/menu/add")
	public ResponseEntity<?> createMenu(@Valid @RequestBody Menu menu){
		Menu newMenu= menuServiece.create(menu);
		if(newMenu == null) {
			return ResponseEntity.badRequest().body("Can not create new menu");
		}
		return ResponseEntity.ok().body(newMenu);
	}
	
	@DeleteMapping("/menu/delete/{id}")
	public ResponseEntity<Boolean> deleteMenu(@PathVariable int id){
		boolean delMenu = menuServiece.delete(id);
		return ResponseEntity.ok().body(delMenu);
	}
	
}
