package com.sharedTrip.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Trip> trips = new HashSet<Trip>();

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<AppliedTrip> appliedTrip = new HashSet<AppliedTrip>();

	@Column(nullable = false, unique = true, length = 45)
	private String username;

	@Column(nullable = false, length = 65)
	private String password;

	@Column(nullable = false, length = 45)
	private String firstName;

	@Column(nullable = false, length = 45)
	private String lastName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Trip> getTrips() {
		return trips;
	}
}
