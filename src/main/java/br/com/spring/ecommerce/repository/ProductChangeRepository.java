package br.com.spring.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.spring.ecommerce.model.ProductChange;

public interface ProductChangeRepository extends JpaRepository<ProductChange, Integer>{

}
