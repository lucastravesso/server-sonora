package br.com.spring.ecommerce.dto;

import java.util.Date;

public interface AuditoryDTO {

	Integer getAud_id();
	
	Date getAud_dt_hora();
	
	char getAud_evento();
	
	String getAud_tabela();
	
	String getAud_owner();
	
	String getAud_coluna();
	
	String getAud_old();
	
	String getAud_new();
	
	Integer getAud_identificador();
	
	String getAud_bduser();
}
