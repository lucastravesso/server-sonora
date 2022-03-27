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

import br.com.spring.ecommerce.dto.OrderCancelDTO;
import br.com.spring.ecommerce.service.OrderCancelService;

@RestController
@RequestMapping("/cancel")
public class OrderCancelController {

	@Autowired
	private OrderCancelService oCancelService;
	
	@PostMapping("/create/{id}")
	private ResponseEntity<?> createChange(@PathVariable("id") Integer id, @RequestBody OrderCancelDTO dto)
	{
		return oCancelService.createCancel(id, dto);
	}
	
	@PutMapping("/update/{id}")
	private ResponseEntity<?> changeStatus(@PathVariable("id") Integer id, @RequestBody OrderCancelDTO dto)
	{
		return oCancelService.changeStatus(id, dto);
	}
	
	@GetMapping("/list")
	private List<OrderCancelDTO> listAll(){
		return oCancelService.listAll();
	}
	
	@GetMapping("/list/user")
	private List<OrderCancelDTO> listAllByUserId()
	{
		return oCancelService.listAllByUserId();
	}
	
	@GetMapping("/list/{id}")
	private ResponseEntity<OrderCancelDTO> listByChangeId(@PathVariable("id") Integer id)
	{
		return oCancelService.listByChangeId(id);
	}
}
