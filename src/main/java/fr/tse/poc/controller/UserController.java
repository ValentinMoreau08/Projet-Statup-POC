package fr.tse.poc.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;
import fr.tse.poc.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PATCH})
public class UserController {

	private @Autowired UserService userService;
	
	@GetMapping("/users")
	public Collection<User> findAllUsers(){
		return userService.findAllUsers();
	}
	
	@PostMapping("/users/{id}/times")
	public Time createTime(@RequestBody Time time, @PathVariable Long id) {
		//User user = this.userService.getUserById(id);
		User user = userService.findAllUsers().iterator().next(); // A changer, c'est juste pour tester
		Project project = time.getProject();
		return userService.createTime(user, project, time.getTime(), time.getDate());
	}
	
}
