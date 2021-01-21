package com.revature.chrisdavis.service;

import java.util.List;

import com.revature.chrisdavis.dao.UserDao;
import com.revature.chrisdavis.dao.UserRoleDao;
import com.revature.chrisdavis.model.User;

public class UserService {
	private UserDao userDao;
	private UserRoleDao userRoleDao;
	
	public UserService() { }
	public UserService(UserDao userDao, UserRoleDao userRoleDao) { 
		this.userDao = userDao;
		this.userRoleDao = userRoleDao;
	}
	
	public List<User> getAllUsers(){
		return userDao.selectAllUsers();
	}
	
	public User selectUserByUserName(String username) {
		return userDao.selectByUserName(username);
	}
	
	public boolean verifyUserName(String username) {
		if(userDao.selectByUserName(username) == null) return false;
		return true;
	}
	
	public boolean verifyLogin(String username, String password) {
		if(userDao.selectByUsernameAndPassword(username, password) == null) return false;
		return true;
	}
	
	public String getUserRoleByUsername(String username) {
		User fetchUser = selectUserByUserName(username);
		try {
			return userRoleDao.findUserRoleById(fetchUser.getUserRole().getId());
		}catch(NullPointerException e) {
			return "Unauthorized";
		}
	}
}
