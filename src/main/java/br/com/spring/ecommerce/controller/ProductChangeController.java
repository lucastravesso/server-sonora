package br.com.spring.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	private ResponseEntity<?> createChange(@PathVariable("id") Integer id, @RequestBody ProductChangeDTO dto)
	{
		return pChangeService.createChange(id, dto);
	}
	
	@PutMapping("/update/{id}")
	private ResponseEntity<?> changeStatus(@PathVariable("id") Integer id, @RequestBody ProductChangeDTO dto)
	{
		return pChangeService.changeStatus(id, dto);
	}
	
	@GetMapping("/list")
	private List<ProductChangeDTO> listAll(){
		return pChangeService.listAll();
	}
	
	@GetMapping("/list/user")
	private List<ProductChangeDTO> listAllByUserId()
	{
		return pChangeService.listAllByUserId();
	}
	
	@GetMapping("/list/{id}")
	private ResponseEntity<ProductChangeDTO> listByChangeId(@PathVariable("id") Integer id)
	{
		return pChangeService.listByChangeId(id);
	}
}
