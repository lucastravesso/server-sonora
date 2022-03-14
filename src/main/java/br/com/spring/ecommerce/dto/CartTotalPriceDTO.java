package br.com.spring.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CartTotalPriceDTO {
	
	List<CartProductsDTO> cartProducts;
	
	Double totalPrice;
	
	Integer total;
}