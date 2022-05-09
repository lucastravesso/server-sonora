package br.com.spring.ecommerce.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class GraficResultPlannedDTO {

	LocalDate cat_sale_date;
	
	Integer getCat_corda;
	
	Integer getCat_sopro;
	
	Integer getCat_percussao;
	
	Integer getCat_eletronicos;
	
	Integer getCat_outros;
	
}
