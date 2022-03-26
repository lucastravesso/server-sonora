package br.com.spring.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.ecommerce.dto.ProductChangeDTO;
import br.com.spring.ecommerce.service.ProductChangeService;

@RestController
@RequestMapping("/change")
public class ProductChangeController {

	@Autowired
	private ProductChangeService pChangeService;
	
	@PostMapping("/create/{id}")
	private ResponseEntity<?> createChange(@PathVariable("id") Integer id)
	{
		return pChangeService.createChange(id);
	}
	
	@PutMapping("/update/{id}")
	private ResponseEntity<?> changeStatus(@PathVariable("id") Integer id, @RequestBody ProductChangeDTO dto)
	{
		return pChangeService.changeStatus(id, dto);
	}
}
