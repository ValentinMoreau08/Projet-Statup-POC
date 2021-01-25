package fr.tse.poc.service.impl;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.domain.Project;
import fr.tse.poc.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
}
