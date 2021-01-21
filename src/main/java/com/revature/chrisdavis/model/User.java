 package com.revature.chrisdavis.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ERS_USERS")
public class User {
	@Id
	@Column(name="ERS_USERS_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="ERS_USERNAME", unique=true)
	private String username;
	@Column(name="ERS_PASSWORD")
	private String password;
	@Column(name="USER_FIRST_NAME")
	private String firstName;
	@Column(name="USER_LAST_NAME")
	private String lastName;
	@Column(name="USER_EMAIL", unique=true)
	private String email;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ROLE_ID_FK")
	private UserRole userRole;
	@OneToMany(mappedBy="userAuthor", fetch=FetchType.LAZY)
	private List<Reimbursement> authorList = new ArrayList<>();
	@OneToMany(mappedBy="userResolver", fetch=FetchType.LAZY)
	private List<Reimbursement> resolverList = new ArrayList<>();
	
	public User() {}
	
	public User(int id, String username, String password, String firstName, String lastName, String email,
			UserRole userRole) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRole = userRole;
	}

	public User(String username, String password, String firstName, String lastName, String email, UserRole userRole) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRole = userRole;
	}

	public int getID() { return id; }
	
	public String getUsername() { return username; }
	public void setUserName(String username) { this.username = username; }
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public UserRole getUserRole() { return userRole; }
	public void setUserRole(UserRole userRole) { this.userRole = userRole; }

	@Override
	public String toString() {
		String uRole;
		try {
			uRole = userRole.getRole();
		}catch (NullPointerException e){
			uRole = null;
		}
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", userRole=" + uRole + "]\n";
	}
}
