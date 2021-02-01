package fr.tse.poc.service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Role;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;
import fr.tse.poc.dto.CreateTimeDTO;

public interface UserService {
	public User createUser(String login, String password, String name, String firstname, Role role);
	public User createUserAsManager(String login, String password, String name, String firstname, User manager, Role role);
	
	public Collection<User> findAllUsers();
	public Collection<User> findAllManagers();
	public Collection<User> findAllAdmins();
	public Collection<User> findAllSimpleUsers();
	public Collection<Time> findAllTimes();
	public Time createTimeAsUser(CreateTimeDTO createTimeDTO, Long userId);
	public User findUserById(Long id);
	public Time findTimeById(Long id);
  
	public void changeManagerOfUser(User user, User manager);
	public void changeRoleAsAdmin(User admin, User user,Role role);
	
	public Set<Time> getTimesOfUser(User user, User manager);
	public Map<Long, Integer> getTimeOfMyUsers(User manager);
	public Set<Time> getTimesOfUserInProject(User user, User manager,Project project);
	
	public User addUserToManager(User admin, User user, User manager);
	public Collection<Role> findAllRoles();
	public Role findRoleById(Long id);
	
}
