package fr.tse.poc.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import fr.tse.poc.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.dao.RoleRepository;
import fr.tse.poc.dao.TimeRepository;
import fr.tse.poc.dao.UserRepository;
import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Role;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;
import fr.tse.poc.service.UserService;
import fr.tse.poc.utils.Constants;

@Service
public class UserServiceImpl implements UserService{
	
	private @Autowired RoleRepository roleRepository;
	private @Autowired UserRepository userRepository;
	private @Autowired TimeRepository timeRepository;
	private @Autowired ProjectRepository projectRepository;
	
	@Override
	public User createUser(String login, String password, String name, String firstname, Role role) {
		roleRepository.save(role);
		User user = new User(login,password,name,firstname, role);
		userRepository.save(user);
		return user;
	}

	@Override
	@Transactional
	public User createUserAsManager(String login, String password, String name, String firstname, User manager, Role role) {
		User user  = createUser(login, password, name, firstname, role);
		user.setManager(manager);
		manager.addManaged(user);
		userRepository.save(manager);
		userRepository.save(user);
		return user;
	}

	@Override
	@Transactional
	public Time createTime(User user, Project project, int time, Date date) {
		user = userRepository.save(user);
		project = projectRepository.save(project);
		
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
	public void changeRoleAsAdmin(User admin, User user,Role role){
		if (admin.getRole().getId() == Constants.ROLE_ADMIN_ID) {
			user.setRole(role);
			userRepository.save(user);
		}
		else {

		}
	};

	@Override
	public Collection<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUserById(Long id) {
		return this.userRepository.findById(id).orElse(null);
	}

	@Override
	public Time findTimeById(Long id) {
		return this.timeRepository.findById(id).orElse(null);
	}

	@Override
	public void changeManagerOfUser(User user, User manager) {
		user.setManager(manager);
		userRepository.save(user);
	}

	@Override
	public Collection<User> findAllManagers() {
		Collection<User> allUsers = userRepository.findAll();
		Collection<User> managers = new HashSet<User>();
		allUsers.forEach(user -> {
			if(user.getRole().getId().equals(Constants.ROLE_MANAGER_ID))
				managers.add(user);
		});
		return managers;
	}

	@Override
	public Collection<User> findAllAdmins() {
		Collection<User> allUsers = userRepository.findAll();
		Collection<User> admins = new HashSet<User>();
		allUsers.forEach(user -> {
			if(user.getRole().getId().equals(Constants.ROLE_ADMIN_ID))
				admins.add(user);
		});
		return admins;
	}

	@Override
	public Collection<User> findAllSimpleUsers() {
		Collection<User> allUsers = userRepository.findAll();
		Collection<User> users = new HashSet<User>();
		allUsers.forEach(user -> {
			if(user.getRole().getId().equals(Constants.ROLE_USER_ID))
				users.add(user);
		});
		return users;
	}

}
