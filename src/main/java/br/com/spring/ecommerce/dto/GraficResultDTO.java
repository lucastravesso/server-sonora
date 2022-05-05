package br.com.spring.ecommerce.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GraficResultDTO {

	Integer id_pedido;
	
	Integer id_categoria;
	
	Date data_pedidos;
	
	Integer volume;
	
}
