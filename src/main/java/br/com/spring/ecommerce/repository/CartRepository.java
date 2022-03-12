package br.com.spring.ecommerce.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

	@Query("select a from Cart a where a.user = ?1")
	Cart findByUserId(Integer integer);

	
}
