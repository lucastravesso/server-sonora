package br.com.spring.ecommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.ecommerce.dto.CartTotalPriceDTO;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@PostMapping("/add-cart/{id}")
	public ResponseEntity<Products> addToCart(@PathVariable("id") Integer id){
		return cartService.addProductToCart(id);
	}
	
	@DeleteMapping("/remove-cart/{id}")
	public ResponseEntity<Products> removeFromCart(@PathVariable("id") Integer id){
		return cartService.removeFromCart(id);
	}
	
	@GetMapping("/get-product")
	public CartTotalPriceDTO getAllProductsByCart()
	{
		return cartService.findAllProductsByCartId();
	}
}
