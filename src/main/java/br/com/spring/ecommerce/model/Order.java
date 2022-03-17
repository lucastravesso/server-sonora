package br.com.spring.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import br.com.spring.ecommerce.util.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DynamicUpdate
@Table(name = "Pedidos")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private Integer id;

	@ManyToMany
	@JoinTable(name = "produtos_pedidos", joinColumns = {
			@JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido") }, inverseJoinColumns = {
					@JoinColumn(name = "id_produto", referencedColumnName = "id_produto") })
	private List<Products> products = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(name = "status_pedido")
	private PurchaseStatus status;
	
	@OneToOne(targetEntity = User.class)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_usuario"))
	private User user;

}
