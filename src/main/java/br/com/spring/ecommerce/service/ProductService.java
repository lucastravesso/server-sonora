package br.com.spring.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.ProductsDTO;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.repository.ProductsRepository;

@Service
public class ProductService {

	
	@Autowired
	private ProductsRepository productsRepository;
	
	private Mapper mapper = new DozerBeanMapper();
	
	public ResponseEntity<Products> insertProduct(ProductsDTO dto)
	{
		Products products = mapper.map(dto, Products.class);

		productsRepository.save(products);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public List<ProductsDTO> listAll()
	{
		List<Products> products = productsRepository.findAll();
		
		return products.stream().map(u ->{
			ProductsDTO dto = mapper.map(u, ProductsDTO.class);

			return dto;
		}).collect(Collectors.toList());
	}
	
	public ResponseEntity<ProductsDTO> listById(Integer id)
	{
		Optional<Products> product = productsRepository.findById(id);
		if(product.isPresent())
		{
			return ResponseEntity.ok(mapper.map(product.get(), ProductsDTO.class));
		}
		return ResponseEntity.notFound().build();
	}
	

	public ResponseEntity<?> deleteProduct(Integer Id) throws AccountNotFoundException
	{
		productsRepository.findById(Id).orElseThrow(() -> new AccountNotFoundException());
		productsRepository.deleteById(Id);
		return ResponseEntity.ok().build();

	}
	
	public ResponseEntity<?> updateProduct(Integer id, ProductsDTO dto)
	{
		Optional<Products> product = productsRepository.findById(id);
		
		if(product.isPresent())
		{
			
			product.get().setProd_name(dto.getProd_name());
			product.get().setProd_builder(dto.getProd_builder());
			product.get().setProd_price(dto.getProd_price());
			product.get().setProd_spec(dto.getProd_spec());
			
			productsRepository.save(product.get());
			
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();	
	}
	
}
