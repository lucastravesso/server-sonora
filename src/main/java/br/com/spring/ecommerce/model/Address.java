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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "enderecos")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_endereco")
	private Integer id;
	
	@Column(name = "end_principal")
	private boolean principal;
	
	@Column(name = "end_pais")
	private String country;
	
	@Column(name = "end_estado")
	private String state;
	
	@Column(name = "end_cidade")
	private String city;
	
	@Column(name = "end_bairro")
	private String district;
	
	@Column(name = "end_rua")
	private String street;
	
	@Column(name = "end_numero")
	private String number;
	
	@Column(name = "end_complemento")
	private String complement;
	
	@OneToOne(targetEntity = User.class)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_usuario_endereco"))
	private User user;
}
