package com.sharedTrip;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.sharedTrip.entities.AppliedTrip;
import com.sharedTrip.entities.Trip;
import com.sharedTrip.entities.User;
import com.sharedTrip.repositories.AppliedTripRepository;
import com.sharedTrip.repositories.TripRepository;
import com.sharedTrip.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AppliedTripRepositoryTest {

	@Autowired
	private AppliedTripRepository appliedTripRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	public void testCreate() {
		Trip trip = tripRepository.getById((long) 1);
		User user = userRepository.getById((long) 2);

		AppliedTrip appliedTrip = new AppliedTrip();
		appliedTrip.setTrip(trip);
		appliedTrip.setUser(user);
		appliedTrip.setApplied(true);
		appliedTrip.setConfirmed(false);

		AppliedTrip saveTrip = appliedTripRepository.save(appliedTrip);
		AppliedTrip existTrip = entityManager.find(AppliedTrip.class, saveTrip.getId());

		assertThat(existTrip.getId()).isEqualTo(saveTrip.getId());

	}
}
