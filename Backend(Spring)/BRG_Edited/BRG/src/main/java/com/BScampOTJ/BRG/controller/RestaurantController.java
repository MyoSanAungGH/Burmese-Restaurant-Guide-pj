package com.BScampOTJ.BRG.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BScampOTJ.BRG.entity.DivisionMState;
import com.BScampOTJ.BRG.entity.Restaurant;
import com.BScampOTJ.BRG.service.DivisionMStateService;
import com.BScampOTJ.BRG.service.RestaurantService;
import com.BScampOTJ.BRG.service.StorageService;

@RestController
public class RestaurantController {
	
	@Autowired
	RestaurantService restService;
	
	@Autowired
	DivisionMStateService divMstateService;
	
	@Autowired
	StorageService storageService;
	
	
	@GetMapping("/restaurant")
	public List<Restaurant> getAllRestaurant(){
		return restService.getAll();
	}
	
	
	@GetMapping("/restaurant/{restaurant_id}")
	public ResponseEntity<Restaurant> getRestautantById(
			@PathVariable("restaurant_id") int restaurantId){
		Restaurant restaurant = restService.get(restaurantId);
		if(restaurant == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(restaurant);
	}
	
	
	
	@PostMapping("/restaurant/add")
	public ResponseEntity<?> createRest(@Valid @RequestBody Restaurant rest){
		Restaurant createRestaurant =restService.create(rest);
		if(createRestaurant == null) {
			return ResponseEntity.badRequest().body("Can't creat Restauratn");
		}
		return ResponseEntity.ok().body(createRestaurant);
	}
	
	
	@PutMapping("/restaurant/update")
	public ResponseEntity<Restaurant> updateRest(@Valid @RequestBody Restaurant rest){
		Restaurant updateRestaurant = restService.update(0, rest);
		if(updateRestaurant == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(updateRestaurant);
	}
	
	@DeleteMapping("/restaurant/delete/{id}")
	public ResponseEntity<Boolean> deleteRest(@PathVariable int id){
		boolean deleteRestaurant = restService.delete(id);
		return ResponseEntity.ok().body(deleteRestaurant);
	}
	
	
	
	@GetMapping("/restaurant/divisionMstate/{divisionMstate_id}")
	public ResponseEntity<?> getRestaurantByDivState(
			@PathVariable("divisionMstate_id") int id
			){
			DivisionMState divMstate = divMstateService.get(id);
			if(divMstate == null) {
				return ResponseEntity.badRequest().body("DivisionMState ID is invalid");
			}
			List<Restaurant> restaurantList = restService.getAllByDivisionMStateId(divMstate);
			return ResponseEntity.ok().body(restaurantList);
	}
	
	
	@GetMapping("/media/{fileType}/{fileName}")
	public ResponseEntity<?> getPic(
			@PathVariable("fileType") String fileType, 
			@PathVariable("fileName") String fileName)throws IOException{
		MediaType contentType = MediaType.IMAGE_PNG;
		switch(fileType) {
		case "mp4" :
			contentType = MediaType.APPLICATION_OCTET_STREAM;
			break;
		case "jpg":
			contentType = MediaType.IMAGE_JPEG;
			break;
		case "png":
			contentType = MediaType.IMAGE_PNG;
			break;
			default: 
				return ResponseEntity.badRequest().body("Unsupported File Type");
		}
		byte[] fileBytes = storageService.load(fileName);
		if(fileBytes == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().contentType(contentType).body(fileBytes);
	}
	
}










