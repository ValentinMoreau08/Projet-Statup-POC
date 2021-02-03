package fr.tse.poc.controller;

import java.util.Collection;
import java.util.Set;

import fr.tse.poc.domain.Project;
import fr.tse.poc.service.ProjectService;
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
public class ProjectController {

    private @Autowired ProjectService projectService;

    @GetMapping("/projects")
    public Collection<Project> findAllProjects(){
        return projectService.findAllProjects();
    }

    // On va plus utiliser un dto (data transfert object) pour éviter de mettre in Time, et faire passer cet objet directement dans la couche service comme vu en cours
    // Car le contrôler n'a pas d'intelligence il ne sert qu'à faire le lien !

    @PostMapping("/projects")
    public Project createProject(@RequestBody Project project){
        return this.projectService.createProject(project);
    }
    
	@GetMapping("/projects/{id}/times")
	public Set<Time> findTimesAsUser(@PathVariable Long id) {
		return this.projectService.findTimesForProject(id);
	}
}
