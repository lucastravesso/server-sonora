package br.com.spring.ecommerce.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	
	@NotNull
	private String prod_name;

	@NotNull
	private Double prod_price;

	@NotNull
	private String prod_spec;

	@NotNull
	private String prod_builder;

	@NotEmpty
	private Integer prod_quantity;
	
	@NotNull
	private CategoryDTO categoryDto;

	
}
