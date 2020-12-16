package fr.tse.poc.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.tse.poc.dao.ManagerRepository;
import fr.tse.poc.dao.UserRepository;
import fr.tse.poc.domain.Manager;
import fr.tse.poc.domain.User;
import fr.tse.poc.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ManagerRepository managerRepository;
	
	@Override
	public User createUser(String login, String password, String name, String firstname) {
		User user = new User(login,password,name,firstname);
		userRepository.save(user);
		return user;
	}

	@Override
	public User createUserAsManager(String login, String password, String name, String firstname, Manager manager) {
		User user  = createUser(login, password, name, firstname);
		user.setManager(manager);
		Set<User> users =  manager.getUsers();
		users.add(user);
		managerRepository.save(manager);
		userRepository.save(user);
		return user;
	}

}
