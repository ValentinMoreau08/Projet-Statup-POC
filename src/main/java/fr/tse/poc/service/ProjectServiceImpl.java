package fr.tse.poc.service;

import fr.tse.poc.dao.ProjectRepository;
import fr.tse.poc.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Collection<Project> findAllProjects() {
        return this.projectRepository.findAll();
    }

    @Override
    public Project createProject(String name, String clientName, String description) {
        Project project = new Project(name, clientName, description);
        projectRepository.save(project);
        return project;
    }
}
