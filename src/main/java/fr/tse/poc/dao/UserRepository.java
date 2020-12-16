package fr.tse.poc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.tse.poc.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
