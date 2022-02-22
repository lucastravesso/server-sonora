package br.com.spring.ecommerce.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	@Query("SELECT a from categorias a where a.id = ?1")
	Category findOneById(Integer id);
	
}
