package fr.tse.poc.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.tse.poc.dao.ManagerRepository;
import fr.tse.poc.domain.Manager;
import fr.tse.poc.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	ManagerRepository managerRepository;
	
	@Override
	public Collection<Manager> findAllManagers() {
		
		return this.managerRepository.findAll();
	}

}
