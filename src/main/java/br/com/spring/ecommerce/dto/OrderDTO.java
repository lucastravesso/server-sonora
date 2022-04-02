package br.com.spring.ecommerce.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.spring.ecommerce.model.Cupon;
import br.com.spring.ecommerce.util.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class OrderDTO {


	private Integer id;
	
	private String orderDate;

	private PurchaseStatus status;
	
	private List<ProductsDTO> products;

	private Cupon cupon;
}
