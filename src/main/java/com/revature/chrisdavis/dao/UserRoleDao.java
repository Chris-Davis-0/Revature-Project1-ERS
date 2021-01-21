package com.revature.chrisdavis.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.chrisdavis.model.UserRole;
import com.revature.chrisdavis.util.HibernateUtil;

public class UserRoleDao {
	private HibernateUtil hiUtil;
	
	public UserRoleDao() {}
	
	public UserRoleDao(HibernateUtil hiUtil) { this.hiUtil = hiUtil; }
	
	
	public List<UserRole> selectAllUserRoles() {
		Session s = hiUtil.getSession();
		Query<UserRole> q = s.createQuery("FROM UserRole", UserRole.class);
		return q.list();
	}
	
	public void insertUserRole(UserRole userRole) {
		Session s = hiUtil.getSession();
		Transaction t = s.beginTransaction();
		s.save(userRole);
		t.commit();
	}
	
	public UserRole findUserRoleByName(String userRole) {
		Session s = hiUtil.getSession();
		Query<UserRole> q = s.createQuery("FROM UserRole WHERE role = '"+userRole+"'", UserRole.class);
		List<UserRole> userRoleList =  q.list();
		if(userRoleList.size() == 0) return null;
		return userRoleList.get(0);
	}
	
	public String findUserRoleById(int id) {
		Session s = hiUtil.getSession();
		Query<UserRole> q = s.createQuery("FROM UserRole WHERE id = '"+id+"'", UserRole.class);
		List<UserRole> userRoleList =  q.list();
		if(userRoleList.size() == 0) return "Unauthorized";
		return userRoleList.get(0).getRole();
	}
}