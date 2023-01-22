package com.BScampOTJ.BRG.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BScampOTJ.BRG.entity.Restaurant;
import com.BScampOTJ.BRG.entity.Review;
import com.BScampOTJ.BRG.entity.User;
import com.BScampOTJ.BRG.entity.UserRole;
import com.BScampOTJ.BRG.repository.RestaurantRepo;
import com.BScampOTJ.BRG.repository.ReviewRepo;
import com.BScampOTJ.BRG.repository.UserRepo;

@Service
public class ReviewServiceImp implements ReviewService {
	
	@Autowired
	ReviewRepo reviewRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RestaurantRepo restaurantRepo; 

	@Override
	public List<Review> getAll() {
		return reviewRepo.findAll();
	}

	@Override
	public Review get(int id) {
		return reviewRepo.findById(id).orElse(null);
	}

	@Override
	public Review create(Review review) {
		//Check user to review
		User user= userRepo.findById(review.getUser().getId()).orElse(null);
		if(user == null) {
			return null;
		}
//		//Check restaurant to review
		Restaurant restaurant = restaurantRepo.findById(review.getRestaurant().getId()).orElse(null);
		if( restaurant == null) {
			return null;
		}
//		//Create review for user role is user
		if(user.getRole() == UserRole.user) {
			review.setReview(review.getReview());;
			review.setReview(review.getReview());
			review.setReviewDate(LocalDateTime.now());
			return reviewRepo.save(review);
		}
		return null;
	}

	@Override
	public Review update(int id, Review rev) {
		Review review = this.get(id);
		if (review == null) {
			return null;
		}
		
		review.setUser(rev.getUser());
		review.setReview(rev.getReview());
		review.setReviewDate((LocalDateTime.now()));
		reviewRepo.save(review);
		return review;
		}
		
//		if(reviewRepo.existsById(rev.getId())) return reviewRepo.save(rev);
//		return new Review();
//	}
	
	@Override
	public boolean delete(int id) {
		Review review = this.get(id);
		if(review == null) {
			return false;
		}
		reviewRepo.deleteById(id);
		return true;
	}

	
	
	@Override
	public List<Review> getAllByRestaurantId(int restaurantId) {
		return reviewRepo.findAllByRestaurantId(restaurantId);
	}

	@Override
	public List<Review> getByUserId(int userId) {
		return reviewRepo.findByUserId(userId);
	}

}
