package com.BScampOTJ.BRG.service;

import java.util.List;

import com.BScampOTJ.BRG.entity.User;

public interface UserService {
	
public List<User> getAll();
	
	public User get(int id);

	public User create(User user);

	public User update(int id, User user);

	public boolean delete(int id);

	public User checkLoginUser(String gmail, String password);
	
	public User updateStatus(int id, String status);
	
	public List<String> getAllStatus();
	
	public void updatePwd(int id,String newPwd);

}
