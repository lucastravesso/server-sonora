package br.com.spring.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Cupon;
import br.com.spring.ecommerce.model.User;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer>{

	@Query("SELECT a FROM Cupon a WHERE a.c_name = ?1")
	Cupon findByName(String nome);
	
	@Query("SELECT a FROM Cupon a WHERE a.c_type = 0")
	List<Cupon> findByTypePromo();
	
	@Query("SELECT a FROM Cupon a WHERE a.c_type = 1")
	List<Cupon> findByTypeChange();
	
	@Query("SELECT a FROM Cupon a WHERE a.user = ?1")
	List<Cupon> findByUser(User user);
}
