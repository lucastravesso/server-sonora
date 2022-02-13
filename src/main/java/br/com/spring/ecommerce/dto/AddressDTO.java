package br.com.spring.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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
