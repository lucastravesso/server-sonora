package br.com.spring.ecommerce.dto;

import java.util.Date;


import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.util.ChangeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductChangeDTO {

	private Integer id;
	
	private Date change_date;
	
	private User user;
	
	private Products product;
	

	private ChangeStatus status;
	
}
