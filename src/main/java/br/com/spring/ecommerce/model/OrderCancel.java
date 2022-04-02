package br.com.spring.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import br.com.spring.ecommerce.util.ChangeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DynamicUpdate
@Table(name = "cancelamentos")
public class OrderCancel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cancelamento")
	private Integer id;

	@Column(name = "motivo_pedido")
	private String change_reason;

	@Column(name = "resposta_pedido")
	private String change_reply;

	@Column(name = "hora_pedido")
	private Date change_date;

	@OneToOne(targetEntity = User.class)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_usuario"))
	private User user;

	@OneToOne(targetEntity = Order.class)
	@JoinColumn(name = "id_pedido", foreignKey = @ForeignKey(name = "fk_pedido"))
	private Order order;

	@Enumerated(EnumType.STRING)
	@Column(name = "status_pedido")
	private ChangeStatus status;

}