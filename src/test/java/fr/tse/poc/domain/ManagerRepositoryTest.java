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

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class ManagerRepositoryTest {

	@Autowired
	private ManagerRepository managerRepository;
	
	@Test
	public void saveAndDeleteManager() {
		List<Manager> managers = managerRepository.findAll();
		int prevSize = managers.size();
		
		Manager manager = new Manager("loginTest", "passwordTest", "nameTest", "firstnameTest");
		manager = managerRepository.save(manager);
		
		managers = managerRepository.findAll();
		
		Assertions.assertNotNull(managers);
		Assertions.assertEquals(prevSize + 1, managers.size());
		
		Manager newManager = managerRepository.findById(manager.getId()).orElse(null);
		Assertions.assertEquals(newManager, manager);
		
		managerRepository.delete(manager);
		
		managers = managerRepository.findAll();
		Assertions.assertNotNull(managers);
		Assertions.assertEquals(prevSize, managers.size());
	}
	
}
