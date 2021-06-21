package com.sharedTrip.entities;

import java.time.LocalTime;

public class Search {

	private String username;
	private String cityDeparture;
	private String cityArrival;
	private LocalTime hourDeparture;
	private LocalTime hourArrival;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCityDeparture() {
		return cityDeparture;
	}

	public void setCityDeparture(String cityDeparture) {
		this.cityDeparture = cityDeparture;
	}

	public String getCityArrival() {
		return cityArrival;
	}

	public void setCityArrival(String cityArrival) {
		this.cityArrival = cityArrival;
	}

	public LocalTime getHourDeparture() {
		return hourDeparture;
	}

	public void setHourDeparture(LocalTime hourDeparture) {
		this.hourDeparture = hourDeparture;
	}

	public LocalTime getHourArrival() {
		return hourArrival;
	}

	public void setHourArrival(LocalTime hourArrival) {
		this.hourArrival = hourArrival;
	}

}
