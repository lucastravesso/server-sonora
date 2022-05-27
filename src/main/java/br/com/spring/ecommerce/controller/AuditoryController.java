package br.com.spring.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.ecommerce.dto.AuditoryDTO;
import br.com.spring.ecommerce.service.AuditoryService;

@RestController
@RequestMapping("audit")
public class AuditoryController {

	@Autowired
	private AuditoryService auditService;
	
	@GetMapping("list")
	private Page<AuditoryDTO> listAll(@PageableDefault(page = 0, size = 3) Pageable page){
		return auditService.listAll(page);
	}
	
}
