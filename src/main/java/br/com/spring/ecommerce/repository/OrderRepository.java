package br.com.spring.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.dto.GraficResultDTO;
import br.com.spring.ecommerce.model.Address;
import br.com.spring.ecommerce.model.Order;
import br.com.spring.ecommerce.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query("SELECT u from Order u where u.id = ?1")
	Order findOneById(Integer id);
	
	@Query("SELECT u FROM Order u where u.user = ?1")
	List<Order> findAllByUserId(User user);
	
	@Query("SELECT u FROM Order u where u.address = ?1")
	List<Order> findByAddressId(Address address);
	
	@Query(value = "SELECT total, id_categoria, cat_nome, teste FROM grafics where teste between ?1 AND ?2", nativeQuery = true)
	List<GraficResultDTO> findByIdAndDateBetween(String dtIni, String dtFim);
}
