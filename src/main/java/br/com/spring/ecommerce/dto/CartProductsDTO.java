package br.com.spring.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CartProductsDTO {
	
	private ProductsDTO productDTO;
	
	private Integer quantity;
	
	private Double price;
}
