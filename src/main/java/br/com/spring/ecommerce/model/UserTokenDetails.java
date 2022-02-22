package br.com.spring.ecommerce.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenDetails {

	private Integer id;
	private String email;
	private Set<Profile> profiles;
	private boolean isEnabled;
}
