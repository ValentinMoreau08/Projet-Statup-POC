package fr.tse.poc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.tse.poc.domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

}