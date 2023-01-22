package com.BScampOTJ.BRG.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.BScampOTJ.BRG.entity.DivisionMState;
import com.BScampOTJ.BRG.entity.Menu;
import com.BScampOTJ.BRG.entity.Restaurant;
import com.BScampOTJ.BRG.entity.Review;
import com.BScampOTJ.BRG.entity.User;
import com.BScampOTJ.BRG.service.DivisionMStateService;
import com.BScampOTJ.BRG.service.MenuService;
import com.BScampOTJ.BRG.service.RestaurantService;
import com.BScampOTJ.BRG.service.ReviewService;
import com.BScampOTJ.BRG.service.StorageService;
import com.BScampOTJ.BRG.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	RestaurantService restService;
	
	@Autowired
	UserService userService;
	
	@Autowired 
	ReviewService revService;
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	StorageService storageService;
	  
	@Autowired
	DivisionMStateService divMstateService;
	
	//-----------------For Restaurant-----------------
	@GetMapping("/restaurant")
	public List<Restaurant> getAllList(){
		return restService.getAll();
	}
	
	@PostMapping("/restaurant/create")
	public ResponseEntity<?> createRestaurant(@Valid @RequestBody Restaurant rest){
		if(!storageService.check(rest.getPicPath())) {
			return ResponseEntity.badRequest().body("Restaurant Picture is invalid" );
		}
		return ResponseEntity.ok(restService.create(rest));
	}
	
	@PutMapping("/restaurant/update/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(@PathVariable int id, @Valid @RequestBody Restaurant rest){
		Restaurant updateRest =  restService.update(id, rest);
		if(updateRest == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updateRest); 
	}
	
	
	@DeleteMapping("/restaurant/delete/{id}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable int id){
		Restaurant delRest = restService.get(id);
		if(delRest == null) {
			return ResponseEntity.notFound().build();
		}
		
		//Delete Restaurant
		boolean isDeleted = restService.delete(id);
		if(!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		
		// Delete Rest Picture
		String restPicPath = delRest.getPicPath();
		storageService.delete(restPicPath);			
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/file/create")
	public ResponseEntity<String> createFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType){
		String filePath = storageService.create(file, fileType);
		if(filePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(filePath);
	 }
	
	
	@PutMapping("/file/update")
	public ResponseEntity<String> updateFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam ("fileType") String fileType,
			@RequestParam ("filePath") String filePath
			){
		String newFilePath = storageService.update(file, fileType, filePath);
		if(newFilePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(newFilePath);
	}
	
//	@GetMapping("/restaurant/name/{name}")
//	public ResponseEntity<Boolean> findRestaurantByName(@PathVariable("name") String name){
//		Restaurant restaurant = restService.getByName(name);
//		if(restaurant == null) {
//			return ResponseEntity.ok().body(false);
//		}
//		return ResponseEntity.ok().body(true);
//	}
	
	//-----------------For User-----------------
	
	@GetMapping("/user")
	public List<User> userList(){
		return userService.getAll();
	}
	
	@GetMapping("/user_status")
	public List<String> listUserStatus(){
		return userService.getAllStatus();
	}
	
	@PutMapping("/user/update_status")
	public ResponseEntity<?> updateUserStatus(@RequestParam int id, @RequestParam String status){
		User user = userService.updateStatus(id, status);
		if(user == null) {
			return ResponseEntity.badRequest().body("User's Status is invalid");
		}
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id){
		User delUser = userService.get(id);
		if(delUser == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDelUser = userService.delete(id);
		if(!isDelUser) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return ResponseEntity.ok().build();
	}
	
//	@DeleteMapping("/menu/delete/{id}")
//	public ResponseEntity<?> deleteMenu(@PathVariable int id){
//		Menu delMenu = menuService.get(id);
//		if(delMenu == null) {
//			return ResponseEntity.notFound().build();
//		}
//		boolean isDelMenu = menuService.delete(id);
//		if(!isDelMenu) {
//			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//		}
//		return ResponseEntity.ok().build();
//	}
	
	//-----------------For Review-----------------
	@GetMapping("/review")
	public List<Review> listReview(){
		return revService.getAll();
	}
//	
////	@GetMapping("/review/restaurant/{restaurant_id}")
////	public List<Review> lisRestReview(){
////		return revService.getAllByRestaurantId(0);
//	}
	
	//-----------------For Menu-----------------
	
	@GetMapping("/menu")
	public List<Menu> menuLIst(){
		return menuService.getAll();
	}
	
	@PostMapping("/menu/create")
	public ResponseEntity<Menu> createMenu(@Valid @RequestBody Menu menu){
		Menu addMenu= menuService.create(menu);
		if(addMenu == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(addMenu);
	}   
	
	@PutMapping("/menu/update/{id}")
	public ResponseEntity<?> updateMenu(@PathVariable int id, @Valid @RequestBody Menu menu){
		Menu updateMenu = menuService.update(id, menu);
		if(updateMenu == null ) {
			return ResponseEntity.badRequest().body("Invalid Menu");
		}
		return ResponseEntity.ok(updateMenu);
	}
	
	@DeleteMapping("/menu/delete/{id}")
	public ResponseEntity<?> deleteMenu(@PathVariable int id){
		Menu delMenu = menuService.get(id);
		if(delMenu == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDelMenu = menuService.delete(id);
		if(!isDelMenu) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return ResponseEntity.ok().build();
	}
	
	
	
	//-----------------For Division-----------------
	
	@GetMapping("/divisionMstate")
	public List<DivisionMState> divMstateList(){
		return divMstateService.getAll();
	}
	
	@PutMapping("/divisionMstate/{id}")
	public ResponseEntity<?> updateDivMState(
			@PathVariable int id, @Valid @RequestBody DivisionMState divMstate){
		DivisionMState updatDivMstate = divMstateService.update(id, divMstate);
		if(updatDivMstate == null) {
			return ResponseEntity.badRequest().body("Division is invalid");
		}
		return ResponseEntity.ok(updatDivMstate);
	}
	
	@DeleteMapping("/divisionMstate/delete/{id}")
	public ResponseEntity<?> delDivMstate(@PathVariable int id){
		DivisionMState delDivMstate = divMstateService.get(id);
		if(delDivMstate == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDelDiv = divMstateService.delete(id);
		if(!isDelDiv) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return ResponseEntity.ok().build();
	}
	
}
