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
public class RoleRepositoryTest {

	private @Autowired RoleRepository roleRepository;
	
	@Test
	public void saveAndDeleteRole() {
		List<Role> roles = roleRepository.findAll();
		int prevSize = roles.size();
		
		Role role = new Role(4L, "RoleTest");
		
		roles = roleRepository.findAll();
		
		Assertions.assertNotNull(roles);
		Assertions.assertEquals(prevSize + 1, roles.size());
		
		Role newRole = roleRepository.findById(role.getId()).orElse(null);
		Assertions.assertEquals(newRole, role);
		
		roleRepository.delete(role);
		
		roles = roleRepository.findAll();
		Assertions.assertNotNull(roles);
		Assertions.assertEquals(prevSize, roles.size());
	}
	
}
