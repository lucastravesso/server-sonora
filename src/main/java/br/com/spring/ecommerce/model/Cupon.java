package br.com.spring.ecommerce.model;

import java.util.Date;

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
@Table(name = "Cupons")
public class Cupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cupom")
	private Integer id;

	@Column(name = "cupom_nome")
	private String c_name;

	@Column(name = "cupom_porcentagem")
	private Integer c_percentage;

	@Column(name = "cupom_data_criacao")
	private Date c_register;

	@Column(name = "cupom_data_fechamento")
	private Date c_final;

	@Column(name = "cupom_quantidade")
	private Integer c_quantity;

	@Column(name = "cupom_tipo")
	private Integer c_type;

	@OneToOne(targetEntity = User.class)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_usuario_cupom"))
	private User user;
}
