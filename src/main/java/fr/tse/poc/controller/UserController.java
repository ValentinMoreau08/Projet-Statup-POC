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
	
	// On va plus utiliser un dto (data transfert object) pour éviter de mettre in Time, et faire passer cet objet directement dans la couche service comme vu en cours
	// Car le contrôler n'a pas d'intelligence il ne sert qu'à faire le lien !
	@PostMapping("/users/{id}/times")
	public Time createTime(@RequestBody Time time, @PathVariable Long id) {
		/*User user = this.userService.findUserById(id);
		System.out.println("Id is = "+id);
		Project project = time.getProject();
		return userService.createTime(user, project, time.getTime(), time.getDate());*/
		return null;
	}
	
}
