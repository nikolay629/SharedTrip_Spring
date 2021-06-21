package com.sharedTrip;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.sharedTrip.entities.User;
import com.sharedTrip.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setUsername("test.test");
		user.setFirstName("a");
		user.setLastName("a");
		user.setPassword("a");

		User savedUser = userRepository.save(user);

		User existUser = entityManager.find(User.class, savedUser.getId());

		assertThat(existUser.getUsername()).isEqualTo(user.getUsername());
	}

	@Test
	public void testFindUserByUsername() {
		String username = "test.test";
		User user = userRepository.findByUsername(username);

		assertThat(user).isNotNull();
	}

}
