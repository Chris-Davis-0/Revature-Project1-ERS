package com.revature.chrisdavis.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.chrisdavis.model.ReimbursementType;
import com.revature.chrisdavis.util.HibernateUtil;

public class ReimbursementTypeDao {
	private HibernateUtil hibernateUtil;
	
	public ReimbursementTypeDao() { }
	
	public ReimbursementTypeDao(HibernateUtil hibernateUtil) { this.hibernateUtil = hibernateUtil; }

	
	public List<ReimbursementType> selectAllReimbursementTypes(){
		Session s = hibernateUtil.getSession();
		Query<ReimbursementType> q = s.createQuery("FROM ReimbursementType", ReimbursementType.class);
		List<ReimbursementType> rTypeList = q.list();
		if(rTypeList.size() == 0) return null;
		return rTypeList;
	}
	
	public void insertReimbursementType(ReimbursementType type) {
		Session s = hibernateUtil.getSession();
		Transaction t = s.beginTransaction();
		s.save(type);
		t.commit();
	}
}
