package br.com.spring.ecommerce.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(Include.NON_NULL)
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

	private Integer prod_quantity;
	
	@NotNull
	private CategoryDTO categoryDto;

	private Integer prod_active;
}
