package com.sharedTrip;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.sharedTrip.entities.Trip;
import com.sharedTrip.entities.User;
import com.sharedTrip.repositories.TripRepository;
import com.sharedTrip.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class TripRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	public void testCreateTrip() {
		String username = "test.test";
		User user = userRepository.findByUsername(username);

		Trip trip = new Trip();
		trip.setUser(user);
		trip.setCityDeparture("Plovdiv");
		trip.setAddressDeparture("bul. Bulgaria 236");
		trip.setHourDeparture(LocalTime.now());
		trip.setCityArrival("Sofia");
		trip.setAddressArrival("center");
		trip.setHourArrival(LocalTime.of(23, 30));
		trip.setSeats(3);
		trip.setPrice(20);

		Trip savedTrip = tripRepository.save(trip);
		Trip existTrip = entityManager.find(Trip.class, savedTrip.getId());

		assertThat(existTrip.getId()).isEqualTo(savedTrip.getId());
	}

	@Test
	public void deleteAllTest() {
		tripRepository.deleteAll();
		assertThat(tripRepository.count()).isEqualTo(0);
	}

	@Test
	public void deleteTest() {
		tripRepository.deleteById((long) 8);
		assertThat(tripRepository.count()).isEqualTo(0);
	}

}
