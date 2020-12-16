package fr.tse.poc.utils;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.domain.Project;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import fr.tse.poc.dao.AdminRepository;
import fr.tse.poc.dao.ManagerRepository;
import fr.tse.poc.dao.UserRepository;
import fr.tse.poc.domain.Admin;
import fr.tse.poc.domain.Manager;
import fr.tse.poc.domain.User;

@Configuration // Can create @Bean method
public class LoadDatabase {

	private Admin admin1;
	private Admin admin2;
	private Manager manager1;
	private Manager manager2;
	private User user1;
	private User user2;
	private User user3;
	private Project project1;
	
	@Bean	// Method that will create beam disponible in spring environnement
	@Profile("!test")
	CommandLineRunner initDatabase(UserRepository userRepository, ManagerRepository managerRepository, AdminRepository adminRepository, ProjectRepository projectRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				if(!needInitializer(userRepository, managerRepository, adminRepository)) return;
				initAdmin(adminRepository);
				initManagers(managerRepository);
				initUsers(userRepository);
				initProjects(projectRepository);
				
			}
		};
	}
	
	// Check if database is full empty, so that we need to initialize some data
	public boolean needInitializer(UserRepository userRepository, ManagerRepository managerRepository, AdminRepository adminRepository) {
		if(userRepository.findAll().size() != 0) return false;
		if(managerRepository.findAll().size() != 0) return false;
		if(adminRepository.findAll().size() != 0) return false;
		return true;
	}
	
	public void initAdmin(AdminRepository adminRepository) {
		admin1 = new Admin("adminLogin1", "adminLogin1", "adminName1", "adminFirstname1");
		admin2 = new Admin("adminLogin2", "adminLogin2", "adminName2", "adminFirstname2");
		adminRepository.save(admin1);
		adminRepository.save(admin2);
	}
	
	public void initManagers(ManagerRepository managerRepository) {
		manager1 = new Manager("managerLogin1", "managerLogin1", "managerName1", "managerFirstname1");
		manager2 = new Manager("managerLogin2", "managerLogin2", "managerName2", "managerFirstname2");
		managerRepository.save(manager1);
		managerRepository.save(manager2);
		
		
	}
	
	public void initUsers(UserRepository userRepository) {
		user1 = new User("userLogin1", "userLogin1", "userName1", "userFirstname1");
		user2 = new User("userLogin1", "userLogin1", "userName1", "userFirstname1");
		user3 = new User("userLogin1", "userLogin1", "userName1", "userFirstname1");
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
	}

	public void initProjects(ProjectRepository projectRepository){
		project1 = new Project("project1","client1","description1");
		projectRepository.save(project1);
	}
	
	
	
	
	
}