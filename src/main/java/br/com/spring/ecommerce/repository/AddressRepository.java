package br.com.spring.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.ecommerce.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

	List<Address> findAllByUserId(Integer id);
	
	Address findByUserId(Integer id);
}
