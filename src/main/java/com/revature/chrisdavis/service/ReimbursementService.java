package com.revature.chrisdavis.service;

import java.sql.Timestamp;
import java.util.List;

import com.revature.chrisdavis.dao.ReimbursementDao;
import com.revature.chrisdavis.dao.ReimbursementStatusDao;
import com.revature.chrisdavis.dao.UserDao;
import com.revature.chrisdavis.model.Reimbursement;
import com.revature.chrisdavis.model.ReimbursementStatus;
import com.revature.chrisdavis.model.ReimbursementType;
import com.revature.chrisdavis.model.User;

public class ReimbursementService {
	private ReimbursementDao reimbDao;
	private UserDao uDao;
	private ReimbursementStatusDao rStatusDao;
	
	public ReimbursementService() { }
	public ReimbursementService(ReimbursementDao reimbDao, UserDao uDao, ReimbursementStatusDao rStatusDao) { 
		this.reimbDao = reimbDao;
		this.uDao = uDao;
		this.rStatusDao = rStatusDao;
	}
	
	public List<Reimbursement> selectAllReimb(){
		return reimbDao.selectAll();
	}
	
	public List<Reimbursement> selectAllReimbByUserName(String username){
		return reimbDao.selectByAuthorUserName(username);
	}
	
	public List<Reimbursement> selectAllReimbByUserName(String username, String filter){
		return reimbDao.selectByAuthorUserName(username, filter);
	}
	
	public List<Reimbursement> selectReimbToResolveBySessionUser(String username){
		User sessUser = uDao.selectByUserName(username);
		return reimbDao.selectReimbToProcess(sessUser);
	}
	
	public List<Reimbursement> selectReimbToResolveBySessionUser(String username, String filter){
		User sessUser = uDao.selectByUserName(username);
		return reimbDao.selectReimbToProcess(sessUser, filter);
	}	
	
	public boolean createRequest(String username, String expenseType, String expenseAmount, String expenseDescription) {
		Double expenseDouble;
		try {
			expenseDouble = Double.parseDouble(expenseAmount);
		}catch(NumberFormatException e) { return false; }
		
		ReimbursementType requestType = reimbDao.getReimbursementTypeByName(expenseType);
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		User requestUser = uDao.selectByUserName(username);
		ReimbursementStatus requestStatus = reimbDao.getReimbursementStatusByName("Pending");
		Reimbursement rRequest = new Reimbursement(expenseDouble, createTime, null, expenseDescription, null, requestUser, null, null, requestStatus, requestType);
		reimbDao.insertReimbursement(rRequest); 
		return reimbDao != null;
	}
	
	public boolean updateRequest(String username, String requestId, String requestStatus, String requestComments) {
		Integer requestInt;
		try {
			requestInt = Integer.parseInt(requestId);
		} catch(NumberFormatException e) { return false; }
		
		Timestamp resolveTime = new Timestamp(System.currentTimeMillis());
		User resolveUser = uDao.selectByUserName(username);
		ReimbursementStatus resolveStatus = rStatusDao.selectReimbursementStatusByName(requestStatus);
		reimbDao.updateReimbursement(requestInt, resolveUser, resolveTime, resolveStatus, requestComments);
		return true;
	}
}
