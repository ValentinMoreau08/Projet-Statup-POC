package fr.tse.poc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
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


	@GetMapping("/users")
	public Collection<User> findAllUsers() {
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
	
	@PatchMapping("/users/{id_admin}/{id_user}/{id_manager}")
	public User addUserToManager(@PathVariable Long id_user,@PathVariable Long id_manager,@PathVariable Long id_admin) {
		User user = userService.findUserById(id_user);
		User manager = userService.findUserById(id_manager);
		User admin = userService.findUserById(id_admin);
		return userService.addUserToManager(admin, user, manager);
	}

	@PatchMapping("/users/{id}")
	public User changeManagerOfUser(User user, User manager) {
		return this.changeManagerOfUser(user, manager);
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
	
	@GetMapping(value = "/users/{id}/exportDoc", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
	public void getTimesForUser(@PathVariable Long id) throws IOException {
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
		FileOutputStream out = new FileOutputStream(new File("./test.docx"));
		document.write(out);
		document.close();
		out.close();
	}
	
	// Export .docx des temps des managed
	@GetMapping("/managers/managed_times/{id}/exportdoc")
	public void exportTimesManaged(@PathVariable Long id) throws IOException {
		User manager = userService.findUserById(id);
		userService.exportTimesManaged(manager);
	}
}
