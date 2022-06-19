package br.com.spring.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("SELECT u from User u where u.id = ?1")
	User findOneById(Integer idUsuario);
	
	@Query("SELECT u from User u where u.email = ?1")
	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u where u.firstName LIKE ?1%")
	List<User> findByFirstNameLike(String firstName);
	
	@Query("SELECT u FROM User u where u.email LIKE ?1%")
	List<User> findByEmailLike(String email);
	
	@Query("SELECT u FROM User u where u.active = 0")
	List<User> findByStatus();

}
