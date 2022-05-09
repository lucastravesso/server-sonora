package br.com.spring.ecommerce.dto;

import java.time.LocalDate;


public interface GraficResultDTO {

	Integer getTotal();
	
	Integer getId_categoria();
	
	String getCat_nome();
	
	LocalDate getTeste();
	
}
