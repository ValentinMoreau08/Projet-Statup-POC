package fr.tse.poc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.tse.poc.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
