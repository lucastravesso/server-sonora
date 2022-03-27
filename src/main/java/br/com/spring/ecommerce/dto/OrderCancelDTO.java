package br.com.spring.ecommerce.dto;

import br.com.spring.ecommerce.model.Order;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.util.ChangeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderCancelDTO {

	private Integer id;
	
	private String change_reason;

	private String change_reply;

	private String change_date;

	private User user;

	private Order order;

	private ChangeStatus status;

}
