package fr.tse.poc.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.ConstantCallSite;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.tse.poc.utils.Constants;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.dao.RoleRepository;
import fr.tse.poc.dao.TimeRepository;
import fr.tse.poc.dao.UserRepository;
import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Role;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;
import fr.tse.poc.dto.CreateTimeDTO;
import fr.tse.poc.service.UserService;
import fr.tse.poc.utils.Constants;

@Service
public class UserServiceImpl implements UserService{
	
	private @Autowired RoleRepository roleRepository;
	private @Autowired UserRepository userRepository;
	private @Autowired TimeRepository timeRepository;
	private @Autowired ProjectRepository projectRepository;
	
	@Override
	public User createUser(String login, String password, String name, String firstname, Role role) {
		roleRepository.save(role);
		User user = new User(login,password,name,firstname, role);
		userRepository.save(user);
		return user;
	}

	@Override
	@Transactional
	public User createUserAsManager(String login, String password, String name, String firstname, User manager, Role role) {
		User user  = createUser(login, password, name, firstname, role);
		user.setManager(manager);
		manager.addManaged(user);
		userRepository.save(manager);
		userRepository.save(user);
		return user;
	}

	@Override
	@Transactional
	public Time createTimeAsUser(CreateTimeDTO createTimeDTO, Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		Project project = projectRepository.findById(createTimeDTO.getProjectId()).orElse(null);
		
		Time ti = new Time(createTimeDTO.getTime(), createTimeDTO.getDate());
		ti.setUser(user);
		ti.setProject(project);
		
		timeRepository.save(ti);
		
		user.addTime(ti);
		project.addTime(ti);
		return ti;
	}

	@Override
	public void changeRoleAsAdmin(User admin, User user, Role role){
		if (admin.getRole().getId() == Constants.ROLE_ADMIN_ID) {
			user.setRole(role);
			userRepository.save(user);
		}
		else {
			return;
		}
	};

	@Override
	public Collection<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUserById(Long id) {
		return this.userRepository.findById(id).orElse(null);
	}

	@Override
	public Time findTimeById(Long id) {
		return this.timeRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public User changeManagerOfUser(User admin, User user, User manager) {
		if(admin.getRole().getId() == Constants.ROLE_ADMIN_ID)
		{
			if(manager.getRole().getId() == Constants.ROLE_MANAGER_ID)
			{
				Set<User> managed = manager.getManaged();
				managed.add(user);
				manager.setManaged(managed);
				user.setManager(manager);
				userRepository.save(user);
				userRepository.save(manager);
			}
		}
		return user;
	}

	@Override
	public Collection<User> findAllManagers() {
		Collection<User> allUsers = userRepository.findAll();
		Collection<User> managers = new HashSet<User>();
		allUsers.forEach(user -> {
			if(user.getRole().getId().equals(Constants.ROLE_MANAGER_ID))
				managers.add(user);
		});
		return managers;
	}

	@Override
	public Collection<User> findAllAdmins() {
		Collection<User> allUsers = userRepository.findAll();
		Collection<User> admins = new HashSet<User>();
		allUsers.forEach(user -> {
			if(user.getRole().getId().equals(Constants.ROLE_ADMIN_ID))
				admins.add(user);
		});
		return admins;
	}

	@Override
	public Collection<User> findAllSimpleUsers() {
		Collection<User> allUsers = userRepository.findAll();
		Collection<User> users = new HashSet<User>();
		allUsers.forEach(user -> {
			if(user.getRole().getId().equals(Constants.ROLE_USER_ID))
				users.add(user);
		});
		return users;
	}

	@Override
	public Set<Time> getTimesOfUser(User user, User manager) {
		if(manager.getRole().getId() == Constants.ROLE_MANAGER_ID)
			return user.getTimes();
		else
			return null;
	}

	@Override
	public  Set<Time> getTimesOfUserInProject(User user,User manager, Project project) {
		if(manager.getRole().getId() == Constants.ROLE_MANAGER_ID)
		{
		Collection<Time> allTimes = timeRepository.findAll();
		Set<Time> timesOfProjectOfUser = new HashSet<Time>();
		allTimes.forEach(time -> {
			if(time.getProject().equals(project) & time.getUser().equals(user))
				timesOfProjectOfUser.add(time);
		});
		return timesOfProjectOfUser;
		}
		else
			return null;
	}

	@Override
	@Transactional
	public User addUserToManager(User admin, User user, User manager) {
		if(admin.getRole().getId() == Constants.ROLE_ADMIN_ID)
		{
			if(manager.getRole().getId() == Constants.ROLE_MANAGER_ID)
			{
				Set<User> managed = manager.getManaged();
				managed.add(user);
				manager.setManaged(managed);
				user.setManager(manager);
				userRepository.save(user);
				userRepository.save(manager);
			}
		}
		return user;
	}

	@Override
	public Map<Long, Integer > getTimeOfMyUsers(User manager) {
		if(manager.getRole().getId() == Constants.ROLE_MANAGER_ID)
			{Collection<User> managed = manager.getManaged();
			Map<Long, Integer> timesOfUsers = new HashMap<Long, Integer>();
			managed.forEach(user -> {
				Integer userTime = 0 ;
				for(Time i : user.getTimes() ) {
					userTime += i.getTime();
				}
				timesOfUsers.put(user.getId(), userTime);
			});
			return timesOfUsers;
			}
		else {
			return null;
		}
	}

	@Override
	public Collection<Time> findAllTimes() {
		return timeRepository.findAll();
	}

	@Override
	public Collection<User> findManagedByManager(User manager) {
		if(manager.getRole().getId() == Constants.ROLE_MANAGER_ID) {
			Collection<User> managed = manager.getManaged();
			return managed;
		}else {
			return null;
		}
	}

	@Override
	public ResponseEntity<Resource> exportTimesManaged(User manager) {
		if(manager.getRole().getId() == Constants.ROLE_MANAGER_ID) {
			Collection<User> managed = manager.getManaged();
			XWPFDocument document = new XWPFDocument();
			XWPFParagraph tmpParagraph = document.createParagraph();
			XWPFRun tmpRun = tmpParagraph.createRun();
			tmpRun.setText("Manager : " + manager.getName());
			tmpRun.addBreak();
			tmpRun.addBreak();
			for (User user : managed) {
				Set<Time> times = user.getTimes();
				tmpRun.setText("Nom : " + user.getName());
				tmpRun.setFontSize(18);
				tmpRun.addBreak();
				tmpRun.addBreak();
				tmpRun.setText("Temps");
				tmpRun.addBreak();
				for (Time time : times) {
					tmpRun.setText(time.getDate() + " : "
							+ String.valueOf(time.getTime() + "h - Project : " + time.getProject().getName()));
					tmpRun.addBreak();
				}

				tmpRun.addBreak();
				tmpRun.addBreak();
				tmpRun.addBreak();
			}
			FileOutputStream out;
			try {
				out = new FileOutputStream(new File("./export_"+manager.getId()+".docx"));
				document.write(out);
				document.close();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 FileSystemResource resource = new FileSystemResource("./export_"+manager.getId()+".docx");
		        // 2.
		        MediaType mediaType = MediaTypeFactory
		                .getMediaType(resource)
		                .orElse(MediaType.APPLICATION_OCTET_STREAM);
		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(mediaType);
		        // 3
		        ContentDisposition disposition = ContentDisposition
		                // 3.2
		        		.builder("attachment")// or .attachment()
		                // 3.1
		                .filename(resource.getFilename())
		                .build();
		        headers.setContentDisposition(disposition);
		        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		}
		return null;	

	    
	}

	public Collection<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	public Role findRoleById(Long id) {
		return this.roleRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public User createUser2(User user) {
		user.setRole(this.findRoleById(Constants.ROLE_USER_ID));
		userRepository.save(user);
		return user;
  }
  	@Override
    public Set<Time> findTimesAsUser(Long id) {
		User user = this.findUserById(id);
		return user.getTimes();
	}

	@SuppressWarnings("deprecation")
	@Override
	public ResponseEntity<Resource> exportMonthlyTimes(User user, int month, int year) {
		//System.out.println("ExportMonthly|month="+month+"|year="+year);
		Set<Time> times = user.getTimes();
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph tmpParagraph = document.createParagraph();
		XWPFRun tmpRun = tmpParagraph.createRun();
		tmpRun.setText("Nom et prénom : " + user.getName()+" "+user.getFirstname());
		tmpRun.setFontSize(18);
		tmpRun.addBreak();
		tmpRun.addBreak();
		tmpRun.setText("Temps saisis du mois: "+(month+1)+" "+ year);
		tmpRun.addBreak();
		tmpRun.addBreak();
		for (Time time : times) {
			//System.out.println("month="+time.getDate().getMonth());
			//System.out.println("year="+time.getDate().getYear());
			if(time.getDate().getMonth() == month && time.getDate().getYear() == (year - 1900))
			{tmpRun.setText(time.getDate() + " : "
					+ String.valueOf(time.getTime() + "h - Project : " + time.getProject().getName()));
			tmpRun.addBreak();}
		}
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File("./exportMonth_"+month+"_"+user.getId()+".docx"));
			document.write(out);
			document.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 FileSystemResource resource = new FileSystemResource("./exportMonth_"+month+"_"+user.getId()+".docx");
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

}
