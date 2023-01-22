package com.BScampOTJ.BRG.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BScampOTJ.BRG.entity.Gender;
import com.BScampOTJ.BRG.entity.User;
import com.BScampOTJ.BRG.entity.UserRole;
import com.BScampOTJ.BRG.entity.UserStatus;
import com.BScampOTJ.BRG.repository.UserRepo;

@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PasswordEncoder pwEncoder;

	
	@Override
	public List<User> getAll() {
		return userRepo.findAll();
	}

	@Override
	public User get(int id) {
		return userRepo.findById(id).orElse(null);
	}
	
	

	@Override
	public User create(User user) {
		//Check same gmail exitst or not
		User checkUserGmail = userRepo.findByGmail(user.getGmail());
		if(checkUserGmail != null) {
			return null;
		}
		user.setName(user.getName());
		user.setAge(user.getAge());
		user.setAddress(user.getAddress());
		user.setGender(user.getGender());
		user.setRole(user.getRole());
		user.setStatus(user.getStatus());
		user.setGmail(user.getGmail());
		user.setPassword(pwEncoder.encode(user.getPassword()));
		user.setStartJoinDate(LocalDate.now());
		return userRepo.save(user);
	}
	

	@Override
	public User update(int id, User user) {
		User toUpdateUser = this.get(id);
		if (toUpdateUser == null) {
			return null;
		}
		toUpdateUser.setName(user.getName());
		toUpdateUser.setAddress(user.getAddress());
		toUpdateUser.setGmail(user.getGmail());
		toUpdateUser.setUpdatedDate(LocalDateTime.now());
		return userRepo.save(toUpdateUser);
	}

	@Override
	public boolean delete(int id) {
		User toDeleteUser = this.get(id);
		if (toDeleteUser == null) {
			return false;
		}
		userRepo.deleteById(id);
		return true;
	}

	@Override
	public User checkLoginUser(String gmail, String password) {
		User user = userRepo.findByGmail(gmail);
		if (user == null) {
			return null;
		}
		if (!pwEncoder.matches(password, user.getPassword())) {
			return null;
		}
		user.setAccessCount(user.getAccessCount() + 1);
		return userRepo.save(user);
	}
	
	
	@Override
	public void updatePwd(int id, String newPwd) {
		User toUpdateUserPwd = get(id);
		if (toUpdateUserPwd != null) {
			toUpdateUserPwd.setPassword(pwEncoder.encode(toUpdateUserPwd.getPassword()));
			userRepo.save(toUpdateUserPwd);
			System.out.println("pwd updated");
		}
	}

	@Override
	public User updateStatus(int id, String status) {
		User toUpdateUser = this.get(id);
		if (toUpdateUser != null) {
			toUpdateUser.setStatus(UserStatus.valueOf(status));
			toUpdateUser.setUpdatedDate(LocalDateTime.now());
			userRepo.save(toUpdateUser);
		}
		return toUpdateUser;
	}

	@Override
	public List<String> getAllStatus() {
		List<String> userStatusList = new ArrayList<>();
		for (UserStatus status : Arrays.asList(UserStatus.values())) {
			userStatusList.add(status.toString());
		}
		return userStatusList;
	}


}
