package br.com.spring.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {

	private Integer id;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String district;
	
	private String street;
	
	private String number;
	
	private String complement;
	
}
