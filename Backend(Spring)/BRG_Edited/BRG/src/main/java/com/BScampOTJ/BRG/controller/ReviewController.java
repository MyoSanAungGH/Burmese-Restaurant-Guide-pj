package com.BScampOTJ.BRG.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BScampOTJ.BRG.entity.Review;
import com.BScampOTJ.BRG.repository.ReviewRepo;
import com.BScampOTJ.BRG.service.ReviewService;

import lombok.Delegate;

@RestController
@RequestMapping("/api")
public class ReviewController {
	
	@Autowired
	ReviewService revService;
	
	@GetMapping("/review")
	public List<Review>  getAllReview(){
		return revService.getAll();
	}
	
	@PostMapping("/review/add")
	public ResponseEntity<?> createReview(@RequestBody Review rev){
		Review newReview =  revService.create(rev);
		if (newReview == null) {
			return ResponseEntity.badRequest().body(
					"User not found, Restaurant not found. User role not user"
			);
		}
		return ResponseEntity.ok().body(newReview);
	}
	
	@PutMapping("/review/update")
	public ResponseEntity<Review> updateReview(@RequestBody Review rev){
		Review editedReview = revService.update(0, rev);
		return ResponseEntity.ok().body(editedReview);
	}
	
	@DeleteMapping("/review/delete/{id}")
	public ResponseEntity<Boolean> deleteReview(@PathVariable int id){
		boolean deleteReview = revService.delete(id);
		return ResponseEntity.ok().body(deleteReview);
	}
	
	@GetMapping("/review/user/{user_id}")
	public List<Review>  getByUserId(@PathVariable int id){
		return revService.getByUserId(0);
	}

}
