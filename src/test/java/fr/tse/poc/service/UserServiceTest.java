package fr.tse.poc.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.dao.RoleRepository;
import fr.tse.poc.dao.TimeRepository;
import fr.tse.poc.dao.UserRepository;
import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;

/*@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")*/
public class UserServiceTest {

    /*private @Autowired UserService userService;
    private @Autowired UserRepository userRepository;
    private @Autowired ProjectRepository projectRepository;
    private @Autowired TimeRepository timeRepository;
    private @Autowired RoleRepository roleRepository;

    @Test
    public void testCreateUser(){
    	int prevSize = userRepository.findAll().size();
        User testUser = userService.createUser("loginTest", "passwordTest", "nameTest", "firstnameTest", roleRepository.findById(3L).orElse(null));
        Assert.assertEquals(prevSize+1, userRepository.findAll().size());
        userRepository.delete(testUser);
        Assert.assertEquals(prevSize, userRepository.findAll().size());
    }
    
    @Test
    public void testCreateUserAsManager(){
		User manager = userService.createUser("loginTest", "passwordTest", "nameTest", "firstnameTest", roleRepository.findById(2L).orElse(null));
        userRepository.save(manager);
    	int prevSizeUserRepo = userService.findAllUsers().size();
		int prevSizeUsers = manager.getManaged().size();
        User testUser = userService.createUserAsManager("loginTest", "passwordTest", "nameTest", "firstnameTest",manager, roleRepository.findById(3L).orElse(null));
        Assert.assertEquals(prevSizeUserRepo+1, userService.findAllUsers().size());
        Assert.assertEquals(prevSizeUsers+1, manager.getManaged().size());
        Assert.assertEquals(manager, testUser.getManager());
        manager.getManaged().remove(testUser);
        userRepository.save(manager);
        userRepository.delete(testUser);
        Assert.assertEquals(prevSizeUserRepo, userService.findAllUsers().size());
        Assert.assertEquals(prevSizeUsers, manager.getManaged().size());
        userRepository.delete(manager);
    }
    
    @Test
    @Transactional
    public void testCreateTime(){
    	User user = userRepository.findAll().get(0);
    	Project project = projectRepository.findAll().get(0);
    	int tim = 3;
    	Date date = new Date();
    	
    	List<Time> times = timeRepository.findAll();
    	int prevSize = times.size();
    	
    	Time ti = userService.createTime(user, project, tim, date);
    	
    	times = timeRepository.findAll();
    	Assertions.assertEquals(prevSize + 1, times.size());
    	Assertions.assertTrue(times.contains(ti));
    	
    	User updatedUser = userRepository.findById(user.getId()).orElse(null);
    	Project updatedProject = projectRepository.findById(project.getId()).orElse(null);
    	
    	Assertions.assertTrue(updatedUser.getTimes().contains(ti));
    	Assertions.assertTrue(updatedProject.getTimes().contains(ti));
    	
    	timeRepository.delete(ti);
    	user.deleteTime(ti);
    	project.deleteTime(ti);
    }*/
}
