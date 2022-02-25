package br.com.spring.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.model.User;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer>{
	
	@Query("SELECT u from Products u where u.user = ?1")
	List<Products> findByUserId(User user);
	
}
