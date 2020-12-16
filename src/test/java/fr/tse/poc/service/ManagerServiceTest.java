package fr.tse.poc.service;

import java.util.Collection;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fr.tse.poc.domain.Manager;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
class ManagerServiceTest {
	
	@Autowired
	ManagerService managerService;
	
    @Test
    public void testFindAllManagers() {
        Collection<Manager> managers = managerService.findAllManagers();
        Assert.assertEquals(2, managers.size());
    }
}
