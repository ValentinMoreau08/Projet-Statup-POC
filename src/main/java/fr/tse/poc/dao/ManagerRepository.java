package fr.tse.poc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tse.poc.domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

}
