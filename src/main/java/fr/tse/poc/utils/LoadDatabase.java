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
	private Project project2;
	private Project project3;

	
	@Bean	// Method that will create beam disponible in spring environnement
	@Profile("!test")
	CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, ProjectRepository projectRepository, TimeRepository timeRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				if(!needInitializer(userRepository, roleRepository, projectRepository, timeRepository)) return;

				initRole(roleRepository);
				initUsers(userRepository);
				initProjects(projectRepository);
				initTimes(timeRepository, userRepository, projectRepository);
			}
		};
	}
	
	// Check if database is full empty, so that we need to initialize some data
	public boolean needInitializer(UserRepository userRepository, RoleRepository roleRepository, ProjectRepository projectRepository, TimeRepository timeRepository) {
		if(roleRepository.count() != 0) return false;
		if(userRepository.count() != 0) return false;
		if(projectRepository.count() != 0) return false;
		if(timeRepository.count() != 0) return false;
		return true;
	}
	
	public void initRole(RoleRepository roleRepository) {
		admin = new Role(1L, "Admin");
		manager = new Role(2L, "Manager");
		user = new Role(3L, "User");
		roleRepository.save(admin);
		roleRepository.save(manager);
		roleRepository.save(user);
	}
	
	public void initUsers(UserRepository userRepository) {
		userAdmin = new User("admin@gmail.com", "admin", "Stewart", "Stephen", admin);
		userManager = new User("manager@gmail.com", "manager", "Lambert", "Dan", manager);
		userUser = new User("user@gmail.com", "user", "Ferguson", "Oliver", user);
		userManager.addManaged(userUser);
		userUser.setManager(userManager);
		userRepository.save(userAdmin);
		userRepository.save(userManager);
		userRepository.save(userUser);
	}

	public void initProjects(ProjectRepository projectRepository){
		project1 = new Project("Kanban","Rémy Girodon","Développer le back-end d'une application web avec spring");
		project2 = new Project("Metadev","Pierre-Yves Fraisse","Répondre à l'appel d'offre du client");
		project3 = new Project("PRI","Sylvain dernat","Application Mobile");

		projectRepository.save(project1);
		projectRepository.save(project2);
		projectRepository.save(project3);

	}
	
	public void initTimes(TimeRepository timeRepository, UserRepository userRepository, ProjectRepository projectRepository) {
		if(timeRepository.findAll().size()!=0) return;
		Project project = projectRepository.findAll().iterator().next();
		Time time1 = new Time(3, new Date(System.currentTimeMillis()));
		time1.setProject(project);
		time1.setUser(userUser);
		userUser.addTime(time1);
		userRepository.save(userUser);
		timeRepository.save(time1);
	}
	
	
	
	
	
}
