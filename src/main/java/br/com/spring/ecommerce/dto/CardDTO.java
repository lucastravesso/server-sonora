package br.com.spring.ecommerce.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import br.com.spring.ecommerce.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CardDTO {

	private Integer id;
	
	@NotNull
	private String card_name;
	
	@NotNull
	private String card_flag;
	
	@NotNull
	private String card_number;
	
	@NotNull
	private Date card_valid;
	
	private User user;
}
