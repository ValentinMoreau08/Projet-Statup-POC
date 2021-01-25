package fr.tse.poc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.dao.TimeRepository;
import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;
import fr.tse.poc.service.UserService;

import fr.tse.poc.domain.User;
import fr.tse.poc.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class UserControllerTest extends ControllerTest{

	@Autowired
	private UserService userService;
	private @Autowired UserService userService;
	private @Autowired TimeRepository timeRepository;
	private @Autowired ProjectRepository projectRepository;
	
	@Test
	public void findAllUsersTest() throws Exception {
		mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void testGetSimpleUsers() throws Exception {
		mvc.perform(get("/simple_users")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	@Test
	public void testGetManagers() throws Exception {
		mvc.perform(get("/managers")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	@Test
	public void testGetAdmins() throws Exception {
		mvc.perform(get("/admins")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	@Test
	public void testAddUserToManager() throws Exception {
		Long user_id = this.userService.findAllSimpleUsers().iterator().next().getId();
		Long manager_id = this.userService.findAllManagers().iterator().next().getId();
		Long admin_id = this.userService.findAllAdmins().iterator().next().getId();		
		mvc.perform(patch("/users/" + user_id + "/" + manager_id + "/" + admin_id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
  @Test
	@Transactional
	public void createTimeAsUserTest() throws Exception {
		User user = ((List<User>) userService.findAllUsers()).get(0);
		Project project = projectRepository.findAll().get(0);
		
		int userTimeSize = user.getTimes().size();
		int projectTimeSize = project.getTimes().size();
		
		String content = "{\"time\": 666,\"projectId\":"+project.getId()+ ",\"date\":\"2021-01-25T09:37:01.740+00:00\"" +"}";
		
		MvcResult mvcResult = mvc.perform(post("/users/"+user.getId()+"/times").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String response = mvcResult.getResponse().getContentAsString();
		
		Long userId = Long.valueOf((Integer) JsonPath.parse(response).read("$.user.id"));
		Long projectId = Long.valueOf((Integer) JsonPath.parse(response).read("$.project.id"));
		
		Assertions.assertEquals(userId, user.getId());
		Assertions.assertEquals(projectId, project.getId());
		
		Integer timeId = JsonPath.parse(response).read("$.id");
		Time time = timeRepository.findById(Long.valueOf(timeId)).orElse(null);
		Assertions.assertEquals(time.getTime(), 666);
		
		Assertions.assertEquals(userTimeSize + 1, user.getTimes().size());
		Assertions.assertEquals(projectTimeSize + 1, project.getTimes().size());
		Assertions.assertTrue(project.getTimes().contains(time));
		Assertions.assertTrue(user.getTimes().contains(time));
		
		user.deleteTime(time);
		project.deleteTime(time);
		timeRepository.delete(time);
		
		Time newTime = timeRepository.findById(Long.valueOf(timeId)).orElse(null);
		Assertions.assertEquals(newTime, null);
		Assertions.assertEquals(userTimeSize, user.getTimes().size());
		Assertions.assertEquals(projectTimeSize, project.getTimes().size());
		
		
		String wrongContent = "{\"time\": 777,\"projectId\":"+project.getId()+"}";
		
		mvc.perform(post("/users/"+user.getId()+"/times").contentType(MediaType.APPLICATION_JSON).content(wrongContent))
				.andExpect(status().isBadRequest());
		
	}
}
