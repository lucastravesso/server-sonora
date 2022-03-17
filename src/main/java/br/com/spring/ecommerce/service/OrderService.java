package br.com.spring.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Autowired
	private OrderService orderService;
	
	public ResponseEntity<?> createOrder()
	{
		
		return null;
		
	}
	
	
}
