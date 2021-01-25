package fr.tse.poc.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;
import fr.tse.poc.dto.CreateTimeDTO;
import fr.tse.poc.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PATCH})
public class UserController {

	private @Autowired UserService userService;
	
	@GetMapping("/users")
	public Collection<User> findAllUsers(){
		return userService.findAllUsers();
	}
	
	@GetMapping("/simple_users")
	public Collection<User> findAllSimpleUsers(){
		return userService.findAllSimpleUsers();
	}
	
	@GetMapping("/managers")
	public Collection<User> findAllManagers(){
		return userService.findAllManagers();
	}
	
	@GetMapping("/admins")
	public Collection<User> findAllAdmins(){
		return this.userService.findAllAdmins();
	}
	
	
	// On va plus utiliser un dto (data transfert object) pour éviter de mettre in Time, et faire passer cet objet directement dans la couche service comme vu en cours
	// Car le contrôler n'a pas d'intelligence il ne sert qu'à faire le lien !

	@PostMapping("/users/{id}/times")
	public Time createTimeAsUser(@Valid @RequestBody CreateTimeDTO createTimeDTO, @PathVariable Long id) {
		return userService.createTimeAsUser(createTimeDTO, id);
	}
	
	@PatchMapping("/users/{id_manager}/{id_user}/{id_admin}")
	public User addUserToManager(@PathVariable Long id_user,@PathVariable Long id_manager,@PathVariable Long id_admin) {
		User user = userService.findUserById(id_user);
		User manager = userService.findUserById(id_manager);
		User admin = userService.findUserById(id_admin);
		return userService.addUserToManager(user, manager, admin);
	}
	

	@PatchMapping("/users/{id}")
	public User changeManagerOfUser(User user, User manager) {
		return this.changeManagerOfUser(user, manager);
	}
	
}
