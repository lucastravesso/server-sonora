package com.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findOneById(Long idUser);
	
	Optional<User> findByMail(String email);
}
