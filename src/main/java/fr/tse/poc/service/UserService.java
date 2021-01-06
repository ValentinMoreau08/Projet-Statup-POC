package fr.tse.poc.service;

import java.util.Collection;
import java.util.Date;

import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Role;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;

public interface UserService {
	public User createUser(String login, String password, String name, String firstname, Role role);
	public User createUserAsManager(String login, String password, String name, String firstname, User manager, Role role);
	public Collection<User> findAllUsers();
	public Time createTime(User user, Project project, int time, Date date);
	public User findUserById(Long id);
	public Time findTimeById(Long id);
	public void changeRole(User user,Role role);
}
