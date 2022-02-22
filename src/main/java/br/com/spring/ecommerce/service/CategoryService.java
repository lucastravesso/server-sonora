package br.com.spring.ecommerce.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.CategoryDTO;
import br.com.spring.ecommerce.model.Category;
import br.com.spring.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	private Mapper mapper = new DozerBeanMapper();
	
	public List<CategoryDTO> findAll()
	{
		List<Category> category = categoryRepository.findAll();
		
		return category.stream().map(c -> {
			
			CategoryDTO dto = mapper.map(c, CategoryDTO.class);
			return dto;
		}).collect(Collectors.toList());
		
	}
	
}
