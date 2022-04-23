package br.com.spring.ecommerce.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "produtos")
@Entity
public class Products {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id_produto")
	private Integer id;
	
	@Column(name ="prod_nome")
	private String prod_name;
	
	@Column(name = "prod_preco")
	private Double prod_price;
	
	@Column(name = "prod_spec")
	private String prod_spec;
	
	@Column(name = "prod_produtor")
	private String prod_builder;
	
	@Column(name = "prod_quantidade")
	private Integer prod_quantity;
	
	@OneToOne(targetEntity = Category.class)
	@JoinColumn(name = "id_categoria", foreignKey = @ForeignKey(name = "fk_categoria"))
	private Category category;
	
	@Column(name = "prod_cliques")
	private Integer prod_clicks;
	
	@Column(name = "prod_ativo")
	private Integer prod_active;
	
	@Column(name = "prod_act_rasao")
	private String prod_act_reason;


}