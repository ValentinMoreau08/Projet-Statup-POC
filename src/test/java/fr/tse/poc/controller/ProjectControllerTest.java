package fr.tse.poc.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.tse.poc.domain.Project;
import fr.tse.poc.service.ProjectService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class ProjectControllerTest extends ControllerTest {

    
	private @Autowired ProjectService projectService;
    
    @Test
	public void findAllProjectsTest() throws Exception {
		mvc.perform(get("/projects").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

    @Test
    public void createProjectTest() throws Exception{

        Project project = new Project("project2","client2","description2");
        
        ObjectMapper mapper = new ObjectMapper();
        byte[] projectAsBytes = mapper.writeValueAsBytes(project);

        mvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON).content(projectAsBytes))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Collection<Project> projects = this.projectService.findAllProjects();

        Assertions.assertEquals(2, projects.size());
    }
    
    @Test
   	public void findTimesAsUserTest() throws Exception {
    	Long idProject = projectService.findAllProjects().iterator().next().getId();
   		mvc.perform(get("/projects/" + idProject + "/times").contentType(MediaType.APPLICATION_JSON))
   				.andExpect(status().isOk())
   				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
   	}
}
