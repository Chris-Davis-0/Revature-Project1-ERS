package com.revature.chrisdavis.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.chrisdavis.model.Reimbursement;
import com.revature.chrisdavis.model.ReimbursementStatus;
import com.revature.chrisdavis.model.ReimbursementType;
import com.revature.chrisdavis.model.User;
import com.revature.chrisdavis.util.HibernateUtil;

public class ReimbursementDao {
	private HibernateUtil hiUtil;
	
	public ReimbursementDao() { }
	public ReimbursementDao(HibernateUtil hiUtil) { this.hiUtil = hiUtil; }

	public List<Reimbursement> selectAll(){
		Session s = hiUtil.getSession();
		return s.createQuery("FROM Reimbursement", Reimbursement.class).list();
	}
	
	public List<Reimbursement> selectByAuthorUserName(String userName) {
		Session s = hiUtil.getSession();
		Query<Reimbursement> q = s.createQuery("FROM Reimbursement where userAuthor.username = '"+userName+"'", Reimbursement.class);
		return q.list();
	}
	
	public List<Reimbursement> selectByAuthorUserName(String userName, String filter) {
		Session s = hiUtil.getSession();
		Query<Reimbursement> q = s.createQuery("FROM Reimbursement where userAuthor.username = '"+userName+
				"' and ersStatus.status = '"+filter+"'", Reimbursement.class);
		return q.list();
	}
	
	public List<Reimbursement> selectByReimbType(String type) {
		Session s = hiUtil.getSession();
		Query<Reimbursement> q = s.createQuery("FROM Reimbursement where ersType.type = '"+type+"'", Reimbursement.class);
		return  q.list();
	}
	
	public List<Reimbursement> selectByReimbStatus(String status) {
		Session s = hiUtil.getSession();
		Query<Reimbursement> q = s.createQuery("FROM Reimbursement where ersStatus.status = '"+status+"'", Reimbursement.class);
		return q.list();
	}
	
	public void insertReimbursement(Reimbursement reimb) {
		Session s = hiUtil.getSession();
		Transaction t = s.beginTransaction();
		s.save(reimb);
		t.commit();
	}
	
	public List<Reimbursement> selectReimbToProcess(User sessionUser){
		Session s = hiUtil.getSession();
		int userRoleId;
		try {
			userRoleId = sessionUser.getUserRole().getId();
		}catch(NullPointerException e) {
			userRoleId = -1;
		}
		Query<Reimbursement> q = s.createQuery("FROM Reimbursement where userAuthor.userRole.id < '"+userRoleId+"' "
				+ "and userAuthor.id <> '"+sessionUser.getID()+"'", Reimbursement.class);
		return q.list();
	}
	
	public List<Reimbursement> selectReimbToProcess(User sessionUser, String filter){
		Session s = hiUtil.getSession();
		int userRoleId;
		try {
			userRoleId = sessionUser.getUserRole().getId();
		}catch(NullPointerException e) {
			userRoleId = -1;
		}
		Query<Reimbursement> q = s.createQuery("FROM Reimbursement where userAuthor.userRole.id < '"+userRoleId+"' "
				+ "and userAuthor.id <> '"+sessionUser.getID()+"' and ersStatus.status = '"+filter+"'", Reimbursement.class);
		return q.list();
	}
		
	public ReimbursementType getReimbursementTypeByName(String typeName) {
		Session s = hiUtil.getSession();
		Query<ReimbursementType> q = s.createQuery("From ReimbursementType where type = '"+typeName+"'", ReimbursementType.class);
		List<ReimbursementType> rTypeList = q.list();
		if(rTypeList.size() == 0) return null;
		return rTypeList.get(0);
	}
	
	public ReimbursementStatus getReimbursementStatusByName(String statusName) {
		Session s = hiUtil.getSession();
		Query<ReimbursementStatus> q = s.createQuery("From ReimbursementStatus where status = '"+statusName+"'", ReimbursementStatus.class);
		List<ReimbursementStatus> rStatusList = q.list();
		if(rStatusList.size() == 0) return null;
		return rStatusList.get(0);
	}
	
	public void updateReimbursement(int requestInt, User resolveUser, Timestamp resolveTime, ReimbursementStatus resolveStatus, String requestComments) {
		Session s = hiUtil.getSession();
		Transaction t = s.beginTransaction();
		Reimbursement updateReimb = (Reimbursement)s.get(Reimbursement.class, requestInt);
		updateReimb.setUserResolver(resolveUser);
		updateReimb.setResolveTime(resolveTime);
		updateReimb.setErsStatus(resolveStatus);
		updateReimb.setResolverComment(requestComments);
		t.commit();		
	}
}
