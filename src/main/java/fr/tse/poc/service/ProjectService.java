package fr.tse.poc.service;

import fr.tse.poc.domain.Project;
import fr.tse.poc.domain.Time;
import fr.tse.poc.domain.User;

import java.util.Collection;
import java.util.Set;

public interface ProjectService {

    public Collection<Project> findAllProjects();
    public Project createProject(Project project);
    public Project findProjectById(Long id);
    
	public  Set<Time> getTimesOfProject(Project project, User manager);
	public Set<Time> findTimesForProject(Long id);

}
