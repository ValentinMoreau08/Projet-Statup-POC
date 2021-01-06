package fr.tse.poc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.tse.poc.domain.Role;
import fr.tse.poc.domain.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
