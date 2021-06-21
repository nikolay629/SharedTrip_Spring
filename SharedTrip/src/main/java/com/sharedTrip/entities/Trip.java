package com.sharedTrip.entities;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "trips")
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@JsonIgnore
	@OneToMany(mappedBy = "trip")
	private Set<AppliedTrip> appliedTrip = new HashSet<AppliedTrip>();

	@Column(nullable = false, length = 65)
	private String cityDeparture;

	@Column(nullable = false, length = 65)
	private String addressDeparture;

	@Column(nullable = false, length = 15)
	private LocalTime hourDeparture;

	@Column(nullable = false, length = 65)
	private String cityArrival;

	@Column(nullable = false, length = 65)
	private String addressArrival;

	@Column(nullable = false, length = 15)
	private LocalTime hourArrival;

	@Column(nullable = false, length = 2)
	private int seats;

	@Column(nullable = false, length = 5)
	private int price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCityDeparture() {
		return cityDeparture;
	}

	public void setCityDeparture(String cityDeparture) {
		this.cityDeparture = cityDeparture;
	}

	public String getAddressDeparture() {
		return addressDeparture;
	}

	public void setAddressDeparture(String addressDeparture) {
		this.addressDeparture = addressDeparture;
	}

	public LocalTime getHourDeparture() {
		return hourDeparture;
	}

	public void setHourDeparture(LocalTime hourDeparture) {
		this.hourDeparture = hourDeparture;
	}

	public String getCityArrival() {
		return cityArrival;
	}

	public void setCityArrival(String cityArrival) {
		this.cityArrival = cityArrival;
	}

	public String getAddressArrival() {
		return addressArrival;
	}

	public void setAddressArrival(String addressArrival) {
		this.addressArrival = addressArrival;
	}

	public LocalTime getHourArrival() {
		return hourArrival;
	}

	public void setHourArrival(LocalTime hourArrival) {
		this.hourArrival = hourArrival;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
