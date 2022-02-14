package br.com.spring.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductsDTO {


	private Integer id;

	private String prod_name;

	private Double prod_price;

	private String prod_spec;

	private String prod_builder;

	//private CategoryDTO categoryDto;
	
	//private UserDTO userDto;
	
}
