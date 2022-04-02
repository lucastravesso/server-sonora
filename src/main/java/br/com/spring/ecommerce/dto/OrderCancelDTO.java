package br.com.spring.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.spring.ecommerce.util.ChangeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class OrderCancelDTO {

	private Integer id;
	
	private String change_reason;

	private String change_reply;

	private String change_date;

	private UserDTO user;

	private OrderDTO order;

	private ChangeStatus status;

}
