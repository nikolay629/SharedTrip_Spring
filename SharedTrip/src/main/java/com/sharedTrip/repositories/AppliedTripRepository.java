package com.sharedTrip.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sharedTrip.entities.AppliedTrip;

public interface AppliedTripRepository extends JpaRepository<AppliedTrip, Long> {

	@Query(value = "Select * from applied_confirmed_trips a where a.id = ?1", nativeQuery = true)
	AppliedTrip findWithId(Long id);

	@Query(value = "Select * from applied_confirmed_trips a where a.user_id = ?1", nativeQuery = true)
	List<AppliedTrip> findAllByUserId(Long id);

	@Query(value = "Select * from applied_confirmed_trips a where a.user_id = ?1 AND a.applied = true", nativeQuery = true)
	List<AppliedTrip> findAppliedByUserId(Long id);

	@Query(value = "Select * from applied_confirmed_trips a where a.user_id = ?1 AND a.applied = false", nativeQuery = true)
	List<AppliedTrip> findConfirmedByUserId(Long id);

	@Query(value = "Select * from applied_confirmed_trips a where a.trip_id = ?1 AND a.applied = true", nativeQuery = true)
	List<AppliedTrip> findAppliedByTripId(Long id);

	@Query(value = "Select * from applied_confirmed_trips a where a.trip_id = ?1 AND a.applied = false", nativeQuery = true)
	List<AppliedTrip> findConfirmedByTripId(Long id);

	@Query(value = "Select t.id from Trips t "
			+ " inner join applied_confirmed_trips a ON a.trip_id = t.id", nativeQuery = true)
	Set<Long> findByTrip();

}
