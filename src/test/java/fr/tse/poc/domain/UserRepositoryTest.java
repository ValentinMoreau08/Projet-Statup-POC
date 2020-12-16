package fr.tse.poc.domain;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fr.tse.poc.dao.ManagerRepository;
import fr.tse.poc.dao.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class UserRepositoryTest {

	
	private @Autowired UserRepository userRepository;
	private @Autowired ManagerRepository managerRepository;
	
	@Test
	public void saveAndDeleteManager() {
		List<User> users = userRepository.findAll();
		int prevSize = users.size();
		
		Manager manager = new Manager("loginTest", "passwordTest", "nameTest", "firstnameTest");
		manager = managerRepository.save(manager);
		
		User user = new User("loginTest", "passwordTest", "nameTest", "firstnameTest");
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
		
		managerRepository.delete(manager);
	}
	
}
