package br.com.spring.ecommerce.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PasswordChangeDTO {
	
	private String oldPass;

	private String password;
}
