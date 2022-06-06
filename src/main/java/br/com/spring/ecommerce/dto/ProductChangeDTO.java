package br.com.spring.ecommerce.dto;

import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.util.ChangeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductChangeDTO {

	private Integer id;
	
	private String change_date;
	
	private String change_reason;
	
	private String change_reply;
	
	private Integer change_notification;

	private Products product;
	
	private Integer notificationsQntd;
	
	private boolean have_notifications;
	
	private ChangeStatus status;
	
}
