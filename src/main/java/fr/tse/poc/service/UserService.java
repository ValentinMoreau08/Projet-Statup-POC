package fr.tse.poc.service;

import fr.tse.poc.domain.Manager;
import fr.tse.poc.domain.User;

public interface UserService {
	User createUser(String login, String password, String name, String firstname);
	User createUserAsManager(String login, String password, String name, String firstname, Manager manager);
}
