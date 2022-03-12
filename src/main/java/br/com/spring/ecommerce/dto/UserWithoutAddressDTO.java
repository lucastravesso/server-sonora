package br.com.spring.ecommerce.dto;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotNull;

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

	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private String cpf;

	@NotNull
	private String rg;

	@NotNull
	private String phone;
	
	@NotNull
	private Date born;
	
	private Date register;
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	private Set<Profile> profile;
	
}