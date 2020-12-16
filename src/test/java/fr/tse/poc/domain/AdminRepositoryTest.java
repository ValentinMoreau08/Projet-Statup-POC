package fr.tse.poc.domain;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fr.tse.poc.dao.AdminRepository;
import fr.tse.poc.dao.ManagerRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class AdminRepositoryTest {

	@Autowired
	private AdminRepository adminRepository;
	
	@Test
	public void saveAndDeleteManager() {
		List<Admin> admins = adminRepository.findAll();
		int prevSize = admins.size();
		
		Admin admin = new Admin("loginTest", "passwordTest", "nameTest", "firstnameTest");
		admin = adminRepository.save(admin);
		
		admins = adminRepository.findAll();
		
		Assertions.assertNotNull(admins);
		Assertions.assertEquals(prevSize + 1, admins.size());
		
		Admin newAdmin = adminRepository.findById(admin.getId()).orElse(null);
		Assertions.assertEquals(newAdmin, admin);
		
		adminRepository.delete(admin);
		
		admins = adminRepository.findAll();
		Assertions.assertNotNull(admins);
		Assertions.assertEquals(prevSize, admins.size());
	}
	
}
