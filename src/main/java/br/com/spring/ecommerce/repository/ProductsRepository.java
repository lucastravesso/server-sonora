package br.com.spring.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer>{

}
