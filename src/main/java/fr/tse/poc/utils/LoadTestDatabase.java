package fr.tse.poc.utils;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.dao.RoleRepository;
import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Role;
import fr.tse.poc.utils.Constants;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


import fr.tse.poc.dao.UserRepository;
import fr.tse.poc.domain.User;

@Configuration // Can create @Bean method
public class LoadTestDatabase {

	private Role admin;
	private Role manager;
	private Role user;
	private User user1;
	private User user2;
	private User user3;
	private Project project1;

	
	
	@Bean	// Method that will create beam disponible in spring environnement
	@Profile("test")
	CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, ProjectRepository projectRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				clearDatabase(userRepository, roleRepository, projectRepository);
				initRole(roleRepository);
				initUsers(userRepository);
				initProjects(projectRepository);
			}
		};
	}
	
	public void clearDatabase(UserRepository userRepository, RoleRepository roleRepository, ProjectRepository projectRepository) {
		projectRepository.deleteAll();
		userRepository.deleteAll();
		roleRepository.deleteAll();
	}
	
	public void initRole(RoleRepository roleRepository) {
		admin = new Role(Constants.ROLE_ADMIN_ID, "Admin");
		manager = new Role(Constants.ROLE_MANAGER_ID, "Manager");
		user = new Role(Constants.ROLE_USER_ID, "User");
		roleRepository.save(admin);
		roleRepository.save(manager);
		roleRepository.save(user);
	}
	
	public void initUsers(UserRepository userRepository) {
		user1 = new User("userLogin1", "userLogin1", "userName1", "userFirstname1", admin);
		user2 = new User("userLogin1", "userLogin1", "userName1", "userFirstname1", manager);
		user3 = new User("userLogin1", "userLogin1", "userName1", "userFirstname1", user);
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
	}
	
	public void initProjects(ProjectRepository projectRepository){
		project1 = new Project("project1","client1","description1");
		projectRepository.save(project1);
	}
	
	
	
}
