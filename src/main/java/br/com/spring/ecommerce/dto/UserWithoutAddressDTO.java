package br.com.spring.ecommerce.dto;

import java.util.Date;
import java.util.Set;

import br.com.spring.ecommerce.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserWithoutAddressDTO {

	private Integer id;

	private String firstName;

	private String lastName;

	private String cpf;

	private String rg;

	private Date born;
	
	private Date register;

	private String email;
	
	private String password;
	
	private Set<Profile> profile;
	
}