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

import br.com.spring.ecommerce.dto.CartTotalPriceDTO;
import br.com.spring.ecommerce.dto.OrderDTO;
import br.com.spring.ecommerce.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<?> createOrder()
	{
		return orderService.createOrder();
	}
	
	@GetMapping(value = "/findAll")
	public List<OrderDTO> findAll(){
		return orderService.listAll();
	}
	
	@PutMapping(value = "/updateStatus/{id}")
	public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id, @RequestBody OrderDTO dto)
	{
		return orderService.changeStatus(id, dto);
	}
	
	@GetMapping(value = "/findByUser")
	public List<OrderDTO> findAllByUserId(){
		return orderService.listAllByUserId();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable("id") Integer id){
		return orderService.findOrderById(id);
	}
	
	@GetMapping(value = "/prod/{id}")
	public CartTotalPriceDTO findOrderById(@PathVariable("id") Integer id){
		return orderService.findAllProductsByCartId(id);
	}
	
	
}
