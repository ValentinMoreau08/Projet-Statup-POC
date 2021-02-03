package fr.tse.poc.service.impl;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;
import fr.tse.poc.service.ProjectService;
import fr.tse.poc.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Collection<Project> findAllProjects() {
        return this.projectRepository.findAll();
    }

    @Override
    public Project createProject(Project project) {
        Project newProject = new Project();
        newProject.setClientName(project.getClientName());
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        projectRepository.save(newProject);
        return project;
    }

	@Override
	public Project findProjectById(Long id) {
		return this.projectRepository.findById(id).orElse(null);
	}

	@Override
	public  Set<Time> getTimesOfProject(Project project, User manager) {
		if(manager.getRole().getId() == Constants.ROLE_MANAGER_ID)
			return project.getTimes();
		else
			return null;
	}

	@Override
	public Set<Time> findTimesForProject(Long id) {
		Project project = this.findProjectById(id);
		return project.getTimes();
	}
}
