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
@Table(name="ERS_REIMBURSEMENT_TYPE")
public class ReimbursementType {
	@Id
	@Column(name="REIMB_TYPE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="REIMB_TYPE", unique=true)
	private String type;
	@OneToMany(mappedBy="ersType", fetch=FetchType.LAZY)
	private List<Reimbursement> ersList;
	
	public ReimbursementType() { }

	public ReimbursementType(String type) {
		this.type = type;
	}
	
	public ReimbursementType(int id, String type) {
		this.id = id;
		this.type = type;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	@Override
	public String toString() {
		return "ReimbursementType [id=" + id + ", type=" + type + "]";
	}
}
