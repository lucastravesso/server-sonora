package br.com.spring.ecommerce.dto;

import java.util.List;


import br.com.spring.ecommerce.util.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDTO {


	private Integer id;
	
	private String orderDate;

	private PurchaseStatus status;
	
	private List<ProductsDTO> products;
	
	private UserDTO user;

	private CuponDTO cupon;
}
