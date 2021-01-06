package fr.tse.poc.domain;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fr.tse.poc.dao.RoleRepository;
import fr.tse.poc.dao.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class UserRepositoryTest {

	private @Autowired RoleRepository roleRepository;
	private @Autowired UserRepository userRepository;
	
	@Test
	public void saveAndDeleteUser() {
		Role userRole = roleRepository.findById(1L).orElse(null);
		
		List<User> users = userRepository.findAll();
		int prevSize = users.size();
		
		User user = new User("loginTest", "passwordTest", "nameTest", "firstnameTest", userRole);
		user = userRepository.save(user);
		
		users = userRepository.findAll();
		
		Assertions.assertNotNull(users);
		Assertions.assertEquals(prevSize + 1, users.size());
		
		User newUser = userRepository.findById(user.getId()).orElse(null);
		Assertions.assertEquals(newUser, user);
		
		userRepository.delete(user);
		
		users = userRepository.findAll();
		Assertions.assertNotNull(users);
		Assertions.assertEquals(prevSize, users.size());
	}
	
}
