package com.revature.chrisdavis.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.chrisdavis.model.ReimbursementStatus;
import com.revature.chrisdavis.util.HibernateUtil;

public class ReimbursementStatusDao {
	private HibernateUtil hiUtil;
	
	public ReimbursementStatusDao() { }
	public ReimbursementStatusDao(HibernateUtil hiUtil) { this.hiUtil = hiUtil; }
	
	
	public List<ReimbursementStatus> selectAllReimbursementStatus() {
		Session s = hiUtil.getSession();
		Query<ReimbursementStatus> q = s.createQuery("FROM ReimbursementStatus", ReimbursementStatus.class);
		return q.list();
	}
	
	public void insertReimbursementStatus(ReimbursementStatus status) {
		Session s = hiUtil.getSession();
		Transaction t = s.beginTransaction();
		s.save(status);
		t.commit();
	}
	
	public ReimbursementStatus selectReimbursementStatusByName(String statusName) {
		Session s = hiUtil.getSession();
		Query<ReimbursementStatus> q = s.createQuery("From ReimbursementStatus where status = '"+statusName+"'", ReimbursementStatus.class);
		List<ReimbursementStatus> rStatusList = q.list();
		if(rStatusList.size() == 0) return null;
		return rStatusList.get(0);
	}
}