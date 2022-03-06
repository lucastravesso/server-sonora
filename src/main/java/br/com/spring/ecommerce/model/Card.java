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

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DynamicUpdate
@Table(name = "cartao")
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id_cartao")
	private Integer id;
	
	@Column(name = "c_nome")
	private String card_name;
	
	@Column(name = "c_bandeira")
	private String card_flag;
	
	@Column(name = "c_numero")
	private String card_number;
	
	@Column(name = "c_validade")
	private String card_valid;
	
	@OneToOne(targetEntity = User.class)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_usuario"))
	private User user;
	
	
}
