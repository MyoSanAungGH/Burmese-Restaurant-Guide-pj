package com.BScampOTJ.BRG.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BScampOTJ.BRG.entity.LoginRequest;
import com.BScampOTJ.BRG.entity.Menu;
import com.BScampOTJ.BRG.entity.User;
import com.BScampOTJ.BRG.service.MenuService;
import com.BScampOTJ.BRG.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder psEncoder;
	
//	@Autowired
//	MenuService menuService;
	
	@GetMapping("/list")
	public List<User> userList(){
		return userService.getAll();
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@Valid @RequestBody LoginRequest loginReq){
		User user= userService.checkLoginUser(loginReq.getGmail(), loginReq.getPassword());
		if(user == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(user);
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user){
		User createdUser = userService.create(user);
		if(createdUser == null) {
			return ResponseEntity.badRequest().body("User with same gamil is already exists...");
		}
		return ResponseEntity.ok().body(createdUser);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User> getProfile(@RequestParam int id){
		User user = userService.get(id);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/profile/update")
	public ResponseEntity<User> updateProfile(@Valid @RequestBody User user){
		if (user.getId() <= 0) {
			return ResponseEntity.badRequest().build();
		}
		User updatedUser = userService.update(user.getId(), user);
		if (updatedUser == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedUser);
	}
	
	
//	@GetMapping("/menu/restaurant/{rest_id}")
//	public List<Menu> viewByRestaurantId(){
//		return menuService.getAllByRestaurantId(0);
//	}
}
 













