package br.com.spring.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.spring.ecommerce.dto.AuditoryDTO;
import br.com.spring.ecommerce.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{

	@Query(value = "select * from audit_table", nativeQuery = true)
	Page<AuditoryDTO> listAll(Pageable page);
	
}
