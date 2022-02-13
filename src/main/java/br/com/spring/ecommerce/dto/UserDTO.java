package br.com.spring.ecommerce.dto;

import java.util.Date;
import java.util.Set;

import br.com.spring.ecommerce.model.Profile;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

	private Integer id;

	private String firstName;

	private String lastName;

	private String cpf;

	private String rg;

	private Date born;
	
	private Date register;

	private String password;

	private String login;

	private String mail;
	
	private AddressDTO addressDto;
	
	private Set<Profile> profile;
	
}
