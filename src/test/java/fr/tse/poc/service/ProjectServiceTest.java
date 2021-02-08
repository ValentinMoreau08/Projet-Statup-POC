package fr.tse.poc.service;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Set;

import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class ProjectServiceTest {

	private @Autowired UserService userService;
    private @Autowired ProjectService projectService;
    private @Autowired ProjectRepository projectRepository;

    @Test
    public void findAllProjectsTest() {
        Collection<Project> projectList = projectService.findAllProjects();
        Assertions.assertFalse(projectList.isEmpty());
    }

    @Test
    @Transactional
    public void createProjectTest(){
    	Collection<Project> projects = projectService.findAllProjects();
    	int prevSize = projects.size();
        Project testProject = new Project();
        testProject.setName("test");
        testProject.setClientName("test");
        testProject.setDescription("test");

        projectService.createProject(testProject);
        Assertions.assertEquals(prevSize + 1, projectService.findAllProjects().size());
        projectRepository.delete(testProject);
        //Assertions.assertEquals(prevSize, projectService.findAllProjects().size());
    }
    
    @Test
    public void findProjectByIdTest() {
    	Project project = projectRepository.findAll().iterator().next();
        Project newProject = projectService.findProjectById(project.getId());
        Assertions.assertNotEquals(null, newProject);
        Assertions.assertEquals(project.getName(), newProject.getName());
    }
    
    @Test
    public void getTimesOfProjectTest() {
    	User manager = userService.findAllManagers().iterator().next();
    	Project project = projectRepository.findAll().iterator().next();
    	
    	Set<Time> times = projectService.getTimesOfProject(project, manager);
    	
    	Assertions.assertEquals(project.getTimes().size(), times.size());
    }
    
    
    @Test
    public void findTimesForProjectTest() {
    	Project project = projectRepository.findAll().iterator().next();
    	Set<Time> times = projectService.findTimesForProject(project.getId());
    	
    	Assertions.assertEquals(project.getTimes().size(), times.size());
    }
    
}
