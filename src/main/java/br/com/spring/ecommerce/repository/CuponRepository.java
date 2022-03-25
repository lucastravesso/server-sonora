package br.com.spring.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Cupon;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer>{

	@Query("SELECT a FROM Cupon a WHERE a.c_name = ?1")
	Cupon findByName(String nome);
	
}
