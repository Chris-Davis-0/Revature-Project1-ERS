package com.revature.chrisdavis.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.chrisdavis.model.User;
import com.revature.chrisdavis.util.HibernateUtil;

public class UserDao {
	private HibernateUtil hiUtil;
	
	public UserDao() {}
	
	public UserDao(HibernateUtil hibernateUtil) { this.hiUtil = hibernateUtil; }
	
	
	public User selectByUserName(String username) {
		Session s = hiUtil.getSession();
		Query<User> q = s.createQuery("FROM User WHERE username = '"+username+"'", User.class);
		//List<User> userList = s.createQuery("FROM User WHERE username = '"+username+"'", User.class).list(); did not play nice with 
		List<User> userList = q.list();
		if(userList.size() == 0) return null;
		return userList.get(0);
	}
	
	public User selectByUserPassword(String password) {
		Session s = hiUtil.getSession();
		Query<User> q = s.createQuery("FROM User WHERE password = '"+password+"'", User.class);
		List<User> userList = q.list();
		if(userList.size() == 0) return null;
		return userList.get(0);
	}
	
	public List<User> selectAllUsers(){
		Session s = hiUtil.getSession();
		Query<User> myQ = s.createQuery("FROM User", User.class);
		return myQ.list();
	}
	
	public void insertUser(User user) {
		Session s = hiUtil.getSession();
		Transaction t = s.beginTransaction();
		s.save(user);
		t.commit();
	}
	
	public User selectByUsernameAndPassword(String username, String password) {
		Session s = hiUtil.getSession();
		Query<User> q = s.createQuery("FROM User WHERE username = '"+username+"'"+" AND password = '"+password+"'", User.class);
		List<User> users = q.list();
		if(users.size() == 0) return null;
		return users.get(0);
	}
}
