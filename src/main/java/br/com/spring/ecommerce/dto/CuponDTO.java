package br.com.spring.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CuponDTO {

	private Integer id;
	
	private String c_name;
	
	private Integer c_percentage;
	
	private String c_register;
	
	private String c_final;
	
}
