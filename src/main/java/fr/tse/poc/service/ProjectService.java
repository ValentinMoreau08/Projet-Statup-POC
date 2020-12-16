package fr.tse.poc.service;

import fr.tse.poc.domain.Project;

import java.util.Collection;

public interface ProjectService {

    public Collection<Project> findAllProjects();
    public Project createProject(String name, String clientName, String description);
    public Project findProjectById(Long id);
}
