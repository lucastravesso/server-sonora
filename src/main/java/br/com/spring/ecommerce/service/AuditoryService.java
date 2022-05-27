package br.com.spring.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.AuditoryDTO;
import br.com.spring.ecommerce.repository.ProfileRepository;

@Service
public class AuditoryService {

	@Autowired
	private ProfileRepository profileRepository;
	
	
	public Page<AuditoryDTO> listAll(Pageable page){
		Page<AuditoryDTO> dto = profileRepository.listAll(page);
		return dto;
	}
	
}
