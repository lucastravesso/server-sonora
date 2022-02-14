package br.com.spring.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.ecommerce.dto.ProductsDTO;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductService productsService;
	
	@PostMapping(value = "/register")
	public ResponseEntity<Products> insertUser(@RequestBody @Valid ProductsDTO dto)
	{
		return productsService.insertProduct(dto);
	}
	
	@GetMapping(value = "/list")
	public List<ProductsDTO> listAll()
	{
		return productsService.listAll();
	}
	
}
