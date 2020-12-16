package fr.tse.poc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.tse.poc.dao.AdminRepository;
import fr.tse.poc.domain.Admin;
import fr.tse.poc.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminRepository adminRepository;
	
	@Override
	public Admin findAdminById(Long id) {
		
		return this.adminRepository.findById(id).orElse(null);
	}

}
