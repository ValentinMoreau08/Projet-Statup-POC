package fr.tse.poc.utils;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.dao.RoleRepository;
import fr.tse.poc.dao.TimeRepository;
import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Role;
import fr.tse.poc.domain.Time;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import fr.tse.poc.dao.UserRepository;
import fr.tse.poc.domain.User;

@Configuration // Can create @Bean method
public class LoadDatabase {

	private Role admin;
	private Role manager;
	private Role user;
	private User userAdmin;
	private User userManager;
	private User userUser;
	private Project project1;
	
	@Bean	// Method that will create beam disponible in spring environnement
	@Profile("!test")
	CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, ProjectRepository projectRepository, TimeRepository timeRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				//if(!needInitializer(userRepository, roleRepository)) return;
				initRole(roleRepository);
				initUsers(userRepository);
				initProjects(projectRepository);
				initTimes(timeRepository, userRepository, projectRepository);
			}
		};
	}
	
	// Check if database is full empty, so that we need to initialize some data
	public boolean needInitializer(UserRepository userRepository, RoleRepository roleRepository) {
		if(roleRepository.findAll().size() != 0) return false;
		if(userRepository.findAll().size() != 0) return false;
		return true;
	}
	
	public void initRole(RoleRepository roleRepository) {
		admin = new Role(1L, "Admin");
		manager = new Role(2L, "Manager");
		user = new Role(2L, "User");
		roleRepository.save(admin);
		roleRepository.save(manager);
		roleRepository.save(user);
	}
	
	public void initUsers(UserRepository userRepository) {
		userAdmin = new User("userLogin1", "userLogin1", "admin", "admin", admin);
		userManager = new User("userLogin1", "userLogin1", "userName1", "userFirstname1", manager);
		userUser = new User("userLogin1", "userLogin1", "userName1", "userFirstname1", user);
		userRepository.save(userAdmin);
		userRepository.save(userManager);
		userRepository.save(userUser);
	}

	public void initProjects(ProjectRepository projectRepository){
		project1 = new Project("project1","client1","description1");
		projectRepository.save(project1);
	}
	
	public void initTimes(TimeRepository timeRepository, UserRepository userRepository, ProjectRepository projectRepository) {
		if(timeRepository.findAll().size()!=0) return;
		Project project = projectRepository.findAll().iterator().next();
		User user = userRepository.findAll().iterator().next();
		Time time1 = new Time(3, new Date(System.currentTimeMillis()));
		time1.setProject(project);
		time1.setUser(user);
		user.addTime(time1);
		userRepository.save(user);
		timeRepository.save(time1);
	}
	
	
	
	
	
}
