package fr.tse.poc.service;

import fr.tse.poc.dao.UserRepository;
import fr.tse.poc.domain.Manager;
import fr.tse.poc.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class AdminServiceTest {

    @Autowired
    AdminService adminService;
    @Autowired
    ManagerService managerService;
    private @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testChangeManagerToUser() {
        Manager manager = managerService.createManager("logintest", "pwdtest", "test", "test");
        int prevSizeManagers = managerService.findAllManagers().size();
        int prevSizeUsers = userService.findAllUsers().size();

        User user = adminService.changeManagerToUser(manager);

        Assert.assertEquals(prevSizeManagers - 1, managerService.findAllManagers().size());
        Assert.assertEquals(prevSizeUsers + 1, userService.findAllUsers().size());

        userRepository.delete(user);
    }
}
