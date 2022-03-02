package br.com.spring.ecommerce.dto;

import javax.validation.constraints.NotNull;

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
	
	@NotNull
	private String country;
	
	@NotNull
	private String state;
	
	@NotNull
	private String city;
	
	@NotNull
	private String district;
	
	@NotNull
	private String street;

	@NotNull
	private String number;
	
	private String complement;
	
}
