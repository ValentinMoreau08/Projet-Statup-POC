package fr.tse.poc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import fr.tse.poc.dao.RoleRepository;
import fr.tse.poc.domain.Role;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;
import fr.tse.poc.dto.CreateTimeDTO;
import fr.tse.poc.service.ProjectService;
import fr.tse.poc.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PATCH})
public class UserController {

	private @Autowired UserService userService;
	private @Autowired ProjectService projectService;
	RoleRepository roleRepository;


	@GetMapping("/users")
	public Collection<User> findAllUsers() {
		return userService.findAllUsers();
	}
	
	@GetMapping("/roles")
	public Collection<Role> findAllRoles() {
		return userService.findAllRoles();
	}
	
	
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		return userService.createUser2(user);
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
	
	@GetMapping("/managers/{id}/managed")
	public Collection<User> findManagedByManager(@PathVariable Long id){
		User manager = userService.findUserById(id);
		return userService.findManagedByManager(manager);
	}
	
	@GetMapping("/times")
	public Collection<Time> findAllTimess(){
		return this.userService.findAllTimes();
	}
	
	
	// On va plus utiliser un dto (data transfert object) pour éviter de mettre in Time, et faire passer cet objet directement dans la couche service comme vu en cours


	// On va plus utiliser un dto (data transfert object) pour éviter de mettre in
	// Time, et faire passer cet objet directement dans la couche service comme vu
	// en cours
	// Car le contrôler n'a pas d'intelligence il ne sert qu'à faire le lien !

	@PostMapping("/users/{id}/times")
	public Time createTimeAsUser(@Valid @RequestBody CreateTimeDTO createTimeDTO, @PathVariable Long id) {
		return userService.createTimeAsUser(createTimeDTO, id);
	}
	
	@GetMapping("/users/{id}/times")
	public Set<Time> findTimesAsUser(@PathVariable Long id) {
		return userService.findTimesAsUser(id);
	}
	
	@PatchMapping("/users/{id_admin}/{id_user}/{id_manager}")
	public User addUserToManager(@PathVariable Long id_user,@PathVariable Long id_manager,@PathVariable Long id_admin) {
		User user = userService.findUserById(id_user);
		User manager = userService.findUserById(id_manager);
		User admin = userService.findUserById(id_admin);
		return userService.addUserToManager(admin, user, manager);
	}

	@PatchMapping("/users/manager/{id_admin}/{id_user}/{id_manager}")
	public User changeManagerOfUser(@PathVariable Long id_user,@PathVariable Long id_manager,@PathVariable Long id_admin) {
		User user = userService.findUserById(id_user);
		User manager = userService.findUserById(id_manager);
		User admin = userService.findUserById(id_admin);
		return userService.changeManagerOfUser(admin, user, manager);
	}
	
	@GetMapping("/managers/managed_times/{id}")
	public Map<Long, Integer> getTimesOfMyUsers(@PathVariable Long id) {
		User manager = userService.findUserById(id);
		return  userService.getTimeOfMyUsers(manager);
	}
	
	@GetMapping("/users/times/{id_manager}/{id_user}")
	public Set<Time> getTimeOfUser(@PathVariable Long id_manager, @PathVariable Long id_user) {
		User manager = userService.findUserById(id_manager);
		User user = userService.findUserById(id_manager);
		return  userService.getTimesOfUser(user, manager);
	}
	
	@GetMapping("/users/times/{id_manager}/{id_user}/{id_project}")
	public Set<Time> getTimeOfUserInProject(@PathVariable Long id_manager, @PathVariable Long id_user, @PathVariable Long id_project) {
		User manager = userService.findUserById(id_manager);
		User user = userService.findUserById(id_manager);
		Project project = projectService.findProjectById(id_project);
		return  userService.getTimesOfUserInProject(user, manager, project);
	}
	
	@GetMapping(value = "/users/month/{id_user}/{id_month}/{year}/exportDoc", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
	public ResponseEntity<Resource> ExportMonthly(@PathVariable Long id_user, @PathVariable int id_month, @PathVariable int year) throws IOException {
		User user = userService.findUserById(id_user);
		return userService.exportMonthlyTimes(user, id_month,year);
	}
	
	@GetMapping(value = "/users/{id}/exportDoc", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
	public ResponseEntity<Resource> getTimesForUser(@PathVariable Long id) throws IOException {
		User user = userService.findUserById(id);
		Set<Time> times = user.getTimes();
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph tmpParagraph = document.createParagraph();
		XWPFRun tmpRun = tmpParagraph.createRun();
		tmpRun.setText("Nom : " + user.getName());
		tmpRun.setFontSize(18);
		tmpRun.addBreak();
		tmpRun.addBreak();
		tmpRun.setText("Temps");
		tmpRun.addBreak();
		tmpRun.addBreak();
		for (Time time : times) {
			tmpRun.setText(time.getDate() + " : "
					+ String.valueOf(time.getTime() + "h - Project : " + time.getProject().getName()));
			tmpRun.addBreak();
		}
		FileOutputStream out = new FileOutputStream(new File("./export_"+user.getId()+".docx"));
		document.write(out);
		document.close();
		out.close();
		 FileSystemResource resource = new FileSystemResource("./export_"+user.getId()+".docx");
	        // 2.
	        MediaType mediaType = MediaTypeFactory
	                .getMediaType(resource)
	                .orElse(MediaType.APPLICATION_OCTET_STREAM);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(mediaType);
	        // 3
	        ContentDisposition disposition = ContentDisposition
	                // 3.2
	        		.builder("inline")// or .attachment()
	                // 3.1
	                .filename(resource.getFilename())
	                .build();
	        headers.setContentDisposition(disposition);
	        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	@PatchMapping("/users/roles/{id_admin}/{id_user}/{id_role}")
	public User changeRoleAsAdmin(@PathVariable Long id_admin, @PathVariable Long id_user, @PathVariable Long id_role){
		User user = userService.findUserById(id_user);
		User admin = userService.findUserById(id_admin);
		Role role = userService.findRoleById(id_role);
		userService.changeRoleAsAdmin(admin, user, role);
		return user;
  }
	
	// Export .docx des temps des managed
	@GetMapping("/managers/managed_times/{id}/exportdoc")
	public ResponseEntity<Resource> exportTimesManaged(@PathVariable Long id) throws IOException {
		User manager = userService.findUserById(id);
		return userService.exportTimesManaged(manager);
		
	}
}
