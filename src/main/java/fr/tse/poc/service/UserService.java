package fr.tse.poc.service;

import java.util.Date;

import fr.tse.poc.domain.Manager;
import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;

public interface UserService {
	User createUser(String login, String password, String name, String firstname);
	User createUserAsManager(String login, String password, String name, String firstname, Manager manager);
	public Time createTime(User user, Project project, int time, Date date);
}
