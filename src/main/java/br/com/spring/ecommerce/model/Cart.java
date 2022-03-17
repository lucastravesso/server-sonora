package br.com.spring.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "Carrinho")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carrinho")
	private Integer id;

	@ManyToMany
	@JoinTable(name = "carrinho_produtos", joinColumns = {
			@JoinColumn(name = "id_carrinho", referencedColumnName = "id_carrinho") }, inverseJoinColumns = {
					@JoinColumn(name = "id_produto", referencedColumnName = "id_produto") })
	private List<Products> product = new ArrayList<>();
	
	@OneToOne(targetEntity = User.class)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_usuario"))
	private User user;
	

}
