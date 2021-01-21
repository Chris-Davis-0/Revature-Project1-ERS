package com.revature.chrisdavis.model;

import java.util.ArrayList;
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
@Table(name="ERS_USER_ROLES")
public class UserRole {
	@Id
	@Column(name="ERS_USER_ROLE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="ERS_USER_ROLE", unique=true)
	private String role;
	@OneToMany(mappedBy="userRole", fetch=FetchType.LAZY)
	private List<User> userList = new ArrayList<>();

	
	public UserRole() { }
	
	public UserRole(int id, String role) {
		this.id = id;
		this.role = role;
	}

	public UserRole(String role) {
		this.role = role;
	}

	public int getId() { return id; }

	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", role=" + role + "]";
	}
}
