package fr.tse.poc.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.tse.poc.dao.ManagerRepository;
import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.dao.TimeRepository;
import fr.tse.poc.dao.UserRepository;
import fr.tse.poc.domain.Manager;
import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;
import fr.tse.poc.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private @Autowired UserRepository userRepository;
	private @Autowired ManagerRepository managerRepository;
	private @Autowired TimeRepository timeRepository;
	private @Autowired ProjectRepository projectRepository;
	
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

	@Override
	public Time createTime(User user, Project project, int time, Date date) {
		Time ti = new Time(time, date);
		ti.setUser(user);
		ti.setProject(project);
		
		user.addTime(ti);
		project.addTime(ti);
		
		ti = timeRepository.save(ti);
		userRepository.save(user);
		projectRepository.save(project);
		return ti;
	}

	@Override
	public Collection<User> findAllUsers() {
		return userRepository.findAll();
	}

}
