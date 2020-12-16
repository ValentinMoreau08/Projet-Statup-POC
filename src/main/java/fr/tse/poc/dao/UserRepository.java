package fr.tse.poc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.tse.poc.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
