package br.com.spring.ecommerce.repository;

import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.dto.GraficResultDTO;
import br.com.spring.ecommerce.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query("SELECT u from Order u where u.id = ?1")
	Order findOneById(Integer id);
	
	@Query("SELECT u FROM Order u where u.user = ?1")
	List<Order> findAllByUserId(Integer user);
	
	@Query("SELECT u FROM Order u where u.address = ?1")
	List<Order> findByAddressId(Integer address);
	
	@Query(value = "SELECT new br.com.spring.ecommerce.dto.GraficResultDTO \r\n"
			+ "	(id_categoria, id_pedido, data_pedidos, count(id_categoria) as volume) as qntd\r\n"
			+ " FROM GRAFICS\r\n"
			+ " where id_categoria = ?1 and hora_pedido BETWEEN '?2' and '?3';\r\n"
			+ " ", nativeQuery = true)
	GraficResultDTO findByIdAndDateBetween(Integer id, String ini, String end);
}
