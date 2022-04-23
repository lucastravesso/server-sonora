package br.com.spring.ecommerce.controller;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public Page<Products> listAll(@PageableDefault(page = 0, size = 3) Pageable page)
	{
		return productsService.listAll(page);
	}
		
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id) throws AccountNotFoundException
	{
		return productsService.deleteProduct(id);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id, @RequestBody @Valid ProductsDTO dto)
	{
		return productsService.updateProduct(id, dto);
	}
	
	@PutMapping(value = "/update-act/{id}")
	public ResponseEntity<?> updateActive(@PathVariable("id") Integer id, @RequestParam(name="motivo") String motivo)
	{
		return productsService.activateProduct(id, motivo);
	}
	
	@GetMapping(value ="/list/{id}")
	public ResponseEntity<ProductsDTO> listById(@PathVariable("id") Integer id)
	{
		return productsService.listById(id);
	}

	@GetMapping(value = "/listall/{nome}")
	public List<ProductsDTO> listAllByName(@PathVariable("nome") String nome){
		return productsService.listAllByName(nome);
	}
	
	@GetMapping(value = "/listtop")
	public List<ProductsDTO> listAllByName(){
		return productsService.listTopProducts();
	}
	
	@GetMapping(value = "/listallcategory/{id}")
	public List<ProductsDTO> listAllByCategory(@PathVariable("id") Integer id){
		return productsService.listAllByCategory(id);
	}
}
