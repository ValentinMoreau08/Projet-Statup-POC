package fr.tse.poc;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.domain.Project;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void saveAndDeleteProject() {

        List<Project> projects = projectRepository.findAll();
        int prevSize = projects.size();

        Project project = new Project("p1", "c1", "test");
        project = projectRepository.save(project);

        projects = projectRepository.findAll();

        Assertions.assertNotNull(projects);
        Assertions.assertEquals(prevSize + 1, projects.size());

        Project newProject = projectRepository.findById(project.getId()).orElse(null);
        Assertions.assertEquals(newProject, project);

        projectRepository.delete(project);

        projects = projectRepository.findAll();
        Assertions.assertNotNull(projects);
        Assertions.assertEquals(prevSize, projects.size());
    }
}
