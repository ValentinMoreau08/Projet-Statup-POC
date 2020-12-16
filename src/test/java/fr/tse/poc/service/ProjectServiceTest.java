package fr.tse.poc.service;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.domain.Project;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class ProjectServiceTest {

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void testFindAllProjects() {
        Collection<Project> projectList = projectService.findAllProjects();
        Assert.assertEquals(1, projectList.size());
    }

    @Test
    public void testCreateProject(){
        Project testProject = projectService.createProject("test","test","test");
        Assert.assertEquals(2, projectService.findAllProjects().size());
        projectRepository.delete(testProject);
        Assert.assertEquals(1, projectService.findAllProjects().size());
    }
}
