package br.com.spring.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "categorias")
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id_categoria")
	private Integer id;
	
	@Column(name = "cat_nome")
	private String categoryName;

}
