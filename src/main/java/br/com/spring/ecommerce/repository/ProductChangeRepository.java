package br.com.spring.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.spring.ecommerce.model.ProductChange;
import br.com.spring.ecommerce.model.User;

public interface ProductChangeRepository extends JpaRepository<ProductChange, Integer>{

	@Query("SELECT u from ProductChange u where u.user = ?1")
	List<ProductChange> findByUserId(User user);
	
}
