package br.com.spring.ecommerce.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.CategoryDTO;
import br.com.spring.ecommerce.dto.ProductsDTO;
import br.com.spring.ecommerce.model.Category;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.repository.CategoryRepository;
import br.com.spring.ecommerce.repository.ProductsRepository;

@Service
public class ProductService {

	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	private Mapper mapper = new DozerBeanMapper();
	
	public ResponseEntity<Products> insertProduct(ProductsDTO dto)
	{
		Products products = mapper.map(dto, Products.class);
		Category category = mapper.map(dto.getCategoryDto(), Category.class);
		
		products.setCategory(category);
		
		productsRepository.save(products);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public List<ProductsDTO> listAll()
	{
		List<Products> products = productsRepository.findAll();

		return products.stream().map(u ->{
			ProductsDTO dto = mapper.map(u, ProductsDTO.class);
			CategoryDTO cDto = mapper.map(u.getCategory(), CategoryDTO.class);
			dto.setCategoryDto(cDto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	public List<ProductsDTO> listAllByName(String nome){
		List<Products> products = productsRepository.findByName(nome);
		
		return products.stream().map(u ->{
			ProductsDTO dto = mapper.map(u, ProductsDTO.class);
			CategoryDTO cDto = mapper.map(u.getCategory(), CategoryDTO.class);
			dto.setCategoryDto(cDto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	public List<ProductsDTO> listAllByCategory(Integer id){
		List<Products> products = productsRepository.findAllByCategory_Id(id);
		
		return products.stream().map(u ->{
			ProductsDTO dto = mapper.map(u, ProductsDTO.class);
			CategoryDTO cDto = mapper.map(u.getCategory(), CategoryDTO.class);
			dto.setCategoryDto(cDto);
			return dto;
		}).collect(Collectors.toList());
	}
	
	public ResponseEntity<ProductsDTO> listById(Integer id) 
	{
		Optional<Products> product = productsRepository.findById(id);
		CategoryDTO cDto = new CategoryDTO();
		ProductsDTO pDto = new ProductsDTO();
		
		if(product.isPresent())
		{
			
			BeanUtils.copyProperties(product.get(), pDto);
			
			if(Objects.nonNull(product.get().getCategory()))
			{
				
				BeanUtils.copyProperties(product.get().getCategory(), cDto);
				pDto.setCategoryDto(cDto);
				
				return ResponseEntity.ok(pDto);
			}
		}
		return ResponseEntity.notFound().build();
	}
	

	public ResponseEntity<?> deleteProduct(Integer Id) throws AccountNotFoundException
	{
		productsRepository.findById(Id).orElseThrow(() -> new AccountNotFoundException());
		productsRepository.deleteById(Id);
		return ResponseEntity.ok().build();

	}
	
	@Transactional
	public ResponseEntity<?> updateProduct(Integer id, ProductsDTO dto)
	{
		
		Optional<Products> product = productsRepository.findById(id);
		
		if(product.isPresent())
		{
			BeanUtils.copyProperties(dto, product.get(), "categoryDto");
			
			if(Objects.nonNull(dto.getCategoryDto()))
			{	
				Category category = categoryRepository.findOneById(dto.getCategoryDto().getId());
				
				product.get().setCategory(category);
				
				productsRepository.save(product.get());
				
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
		
	}
	
}
