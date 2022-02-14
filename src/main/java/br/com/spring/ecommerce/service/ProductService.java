package br.com.spring.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.AddressDTO;
import br.com.spring.ecommerce.dto.ProductsDTO;
import br.com.spring.ecommerce.dto.UserDTO;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.ProductsRepository;
import br.com.spring.ecommerce.repository.UserRepository;

@Service
public class ProductService {

	@Autowired
	private UserRepository userRepository;
	
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
	
}
