package fr.tse.poc.service;

import java.util.Collection;

import fr.tse.poc.domain.Manager;

public interface ManagerService {
	public Collection<Manager> findAllManagers();
	public Manager findManagerById(Long id);
	public Manager createManager(String login, String password, String name, String firstname);
}