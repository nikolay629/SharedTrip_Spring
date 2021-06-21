package com.sharedTrip.repositories;

import java.time.LocalTime;
import java.util.List;
import java.util.TreeSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sharedTrip.entities.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {

	@Query(value = "Select * from Trips t where t.id = ?1", nativeQuery = true)
	Trip findByTripId(Long id);

	@Query(value = "Select * from Trips t where t.user_id = ?1", nativeQuery = true)
	List<Trip> findByUserId(Long id);

	@Query(value = "Select * from Trips t where t.user_id <> ?1", nativeQuery = true)
	List<Trip> findAllWithOutThisUser(Long id);

	@Query(value = "Select t.* from Trips t" + " Inner Join Users u On u.id = t.user_Id"
			+ " where u.username <> ?1", nativeQuery = true)
	List<Trip> findByUsename(String username);

	@Query(value = "Select * from Trips t where t.city_departure <> ?1", nativeQuery = true)
	List<Trip> findByCityDeparture(String city);

	@Query(value = "Select * from Trips t where t.city_arrival <> ?1", nativeQuery = true)
	List<Trip> findByCityArrival(String city);

	@Query(value = "Select * from Trips t where t.hour_departure <> ?1", nativeQuery = true)
	List<Trip> findByHourDeparture(LocalTime hour);

	@Query(value = "Select * from Trips t where t.hour_arrival <> ?1", nativeQuery = true)
	List<Trip> findByHourArrival(LocalTime hour);

	@Query("Select t.cityDeparture from Trip t")
	TreeSet<String> findAllCityDeparture();

	@Query("Select t.cityArrival from Trip t")
	TreeSet<String> findAllCityArrival();

	@Query(value = "Delete from Trips t where t.id = ?1", nativeQuery = true)
	void remove(Long id);

}
