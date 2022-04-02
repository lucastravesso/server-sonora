package br.com.spring.ecommerce.dto;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.spring.ecommerce.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class UserDTO {

	private Integer id;

	private String firstName;

	private String lastName;

	private String cpf;

	private String rg; 
	
	private String phone;

	private String born;
	
	private Date register;

	private String email;
	
	private String password;
	
	private AddressDTO addressDto;
		
	private Set<Profile> profile;
}
