package com.revature.chrisdavis.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="ERS_REIMBURSEMENT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reimbursement {
	@Id
	@Column(name="REIMB_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="REIMB_AMOUNT")
	private double amount;
	@Column(name="REIMB_SUBMITTED")
	private Timestamp submitTime;
	@Column(name="REIMB_RESOLVED", nullable=true)
	private Timestamp resolveTime;
	@Column(name="REIMB_DESCRIPTION", nullable=true)
	private String description;
	@Column(name="REIMB_RECIEPT", nullable=true)
	private byte[] reciept;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="ERS_USER_FK_AUTH")
	private User userAuthor;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="ERS_USER_FK_RSLVR")
	private User userResolver;
	@Column(name="REIMB_RESOLVER_COMMENT", nullable=true)
	private String resolverComment;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="ERS_REIMBURSEMENT_STATUS_FK")
	private ReimbursementStatus ersStatus;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="ERS_REIMBURSEMENT_TYPE_FK")
	private ReimbursementType ersType;
	
	
	
	public Reimbursement() {}

	public Reimbursement(int id, double amount, Timestamp submitTime, Timestamp resolveTime, String description,
			byte[] reciept, User userAuthor, User userResolver, String resolverComment, ReimbursementStatus ersStatus,
			ReimbursementType ersType) {
		this.id = id;
		this.amount = amount;
		this.submitTime = submitTime;
		this.resolveTime = resolveTime;
		this.description = description;
		this.reciept = reciept;
		this.userAuthor = userAuthor;
		this.userResolver = userResolver;
		this.resolverComment = resolverComment;
		this.ersStatus = ersStatus;
		this.ersType = ersType;
	}

	public Reimbursement(double amount, Timestamp submitTime, Timestamp resolveTime, String description,
			byte[] reciept, User userAuthor, User userResolver, String resolverComment, ReimbursementStatus ersStatus,
			ReimbursementType ersType) {
		this.amount = amount;
		this.submitTime = submitTime;
		this.resolveTime = resolveTime;
		this.description = description;
		this.reciept = reciept;
		this.userAuthor = userAuthor;
		this.userResolver = userResolver;
		this.resolverComment = resolverComment;
		this.ersStatus = ersStatus;
		this.ersType = ersType;
	}

	public double getAmount() { return amount; }
	public void setAmount(double amount) { this.amount = amount; }

	public Timestamp getSubmitTime() { return submitTime; }
	public void setSubmitTime(Timestamp submitTime) { this.submitTime = submitTime; }

	public Timestamp getResolveTime() { return resolveTime; }
	public void setResolveTime(Timestamp resolveTime) { this.resolveTime = resolveTime; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public byte[] getReciept() { return reciept; }
	public void setReciept(byte[] reciept) { this.reciept = reciept; }

	public User getUserAuthor() { return userAuthor; }
	public void setUserAuthor(User userAuthor) { this.userAuthor = userAuthor; }

	public User getUserResolver() { return userResolver; }
	public void setUserResolver(User userResolver) { this.userResolver = userResolver; }

	public String getResolverComment() { return resolverComment; }
	public void setResolverComment(String resolverComment) { this.resolverComment = resolverComment; }

	public ReimbursementStatus getErsStatus() { return ersStatus; }
	public void setErsStatus(ReimbursementStatus ersStatus) { this.ersStatus = ersStatus; }

	public ReimbursementType getErsType() { return ersType; }
	public void setErsType(ReimbursementType ersType) { this.ersType = ersType; }

	public int getId() {
		return id;
	}

	//Bandaid fix json parsing issue
	@Override
	public String toString() {
		String resolverName = "-";
		String resolveTimeToString = "-";
		String resolverCommentToString = resolverComment;
		String requestType = ersType.getType(); 
		String requestStatus = ersStatus.getStatus();
		try {
			resolverName = userResolver.getUsername();
			resolveTimeToString = resolveTime.toString();
		}catch(NullPointerException e) { 
			resolverCommentToString = "-";
		}
		return "{\"ID\":" + id + ",\"Author\":" + "\""+ userAuthor.getUsername()+"\""+ 
	",\"Requested\":" + amount + ",\"Type\":" + "\""+requestType+"\"" + ",\"Submitted\":" 
	+ "\""+submitTime+"\"" + ",\"Status\":" + "\""+requestStatus+"\"" + ",\"Resolver\":" 
	+ "\""+resolverName+"\"" + ",\"Resolved\":" + "\""+resolveTimeToString+"\""+
	",\"Description\":" + "\""+description+"\""+",\"Comments\":"+"\""+resolverCommentToString+"\""+"}";
	}
}
