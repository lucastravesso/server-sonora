package br.com.spring.ecommerce.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer>{
	
	@Query("SELECT a FROM Products a WHERE a.prod_name LIKE %?1%")
	List<Products> findByName(String nome);
	
	List<Products> findAllByCategory_Id(Integer id);
	
	@Query(value = "SELECT * FROM produtos ORDER BY prod_cliques DESC LIMIT 5", nativeQuery = true)
	List<Products> findTopProducts();
	
}
