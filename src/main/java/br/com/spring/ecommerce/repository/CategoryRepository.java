package br.com.spring.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
