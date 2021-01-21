package com.revature.chrisdavis.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ERS_REIMBURSEMENT_STATUS")
public class ReimbursementStatus {
	@Id
	@Column(name="REIMB_STATUS_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="REIM_STATUS", unique=true)
	private String status;
	@OneToMany(mappedBy="ersStatus", fetch=FetchType.LAZY)
	private List<Reimbursement> ersList;
	
	
	public ReimbursementStatus () {}
	
	public ReimbursementStatus (String status) {
		this.status = status;
	}
	
	public ReimbursementStatus (int id, String status) {
		this.id = id;
		this.status = status;
	}
	
	public int getID() { return id; }
	
	public String getStatus() { return status; }
	public void setStatusID(String status) { this.status = status; }

	@Override
	public String toString() {
		return "ReimbursementStatus [id=" + id + ", status=" + status + "]\n";
	}
	
}
