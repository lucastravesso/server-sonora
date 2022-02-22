package br.com.spring.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.ecommerce.dto.CategoryDTO;
import br.com.spring.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	public CategoryService categoryService;
	
	@GetMapping(value = "/list")
	private List<CategoryDTO> findAll()
	{
		return categoryService.findAll();
	}
}
