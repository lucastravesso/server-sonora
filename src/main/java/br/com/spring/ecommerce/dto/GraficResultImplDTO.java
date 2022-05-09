package br.com.spring.ecommerce.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
public class GraficResultImplDTO{
	
	Integer getTotal;
	
	Integer getId_categoria;
	
	String getCat_nome;
	
	LocalDate getTeste;

	public GraficResultImplDTO(Integer getTotal, Integer getId_categoria, String getCat_nome, LocalDate getTeste) {
		super();
		this.getTotal = getTotal;
		this.getId_categoria = getId_categoria;
		this.getCat_nome = getCat_nome;
		this.getTeste = getTeste;
	}

	
}
