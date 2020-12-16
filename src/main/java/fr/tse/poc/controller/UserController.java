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

//@CrossOrigin car par défaut, il est interdit de faire des requêtes CrossOrigin (ie client qui fait la requête ne vient pas du même serveur qui expose l'api)
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PATCH})
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public Collection<User> findAllUsers(){
		return null;
	}
	
	@PostMapping("/users/{id}/times")
	public Time createTime(@RequestBody Time time, @PathVariable Long id) {
		//User user = this.userService.getUserById(id);
		//User user = userServi
		//Project project = time.getProject();
		//return userService.createTime(user, project, time.getTime(), time.getDate());
		return null;
	}
	
}
