package br.com.spring.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.OrderCancel;
import br.com.spring.ecommerce.model.User;

@Repository
public interface OrderCancelRepository extends JpaRepository<OrderCancel, Integer>{

	
	@Query("SELECT u from OrderCancel u where u.user = ?1")
	List<OrderCancel> findByUserId(User user);
}
