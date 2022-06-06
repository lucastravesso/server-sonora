package br.com.spring.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Card;
import br.com.spring.ecommerce.model.User;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer>{

	@Query("SELECT u from Card u where u.user = ?1")
	List<Card> findByUserId(User user);
	
	@Query(value = "select * from cartao where id_usuario = ?1", nativeQuery = true)
	List<Card> findAllByUserId(Integer id);
	
}
