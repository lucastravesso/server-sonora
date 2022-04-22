package br.com.spring.ecommerce.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query("SELECT u from Order u where u.id = ?1")
	Order findOneById(Integer id);
	
	@Query("SELECT u FROM Order u where u.user = ?1")
	List<Order> findAllByUserId(Integer user);
	
	@Query("SELECT u FROM Order u where u.address = ?1")
	List<Order> findByAddressId(Integer address);
}
