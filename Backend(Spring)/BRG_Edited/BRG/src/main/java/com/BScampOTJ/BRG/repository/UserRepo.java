package com.BScampOTJ.BRG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.BScampOTJ.BRG.entity.User;

@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByGmail(String gmail);
	
	public User findByName(String name);
}
