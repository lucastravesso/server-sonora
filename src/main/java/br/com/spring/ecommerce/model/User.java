package br.com.spring.ecommerce.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class User implements UserDetails{

	private static final long serialVersionUID = -4523629298819464374L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id_usuario")
	private Integer id;
	
	@Column(name = "usu_email")
	protected String email;
	
	@Column(name = "usu_senha")
	protected String password;
	
	@Column(name = "primeiro_nome")
	private String firstName;
	
	@Column(name = "ultimo_nome")
	private String lastName;
	
	@Column(name = "usu_cpf")
	private String cpf;
	
	@Column(name = "usu_rg")
	private String rg;
	
	@Column(name = "usu_dt_nasc")
	private Date born;
	
	@Column(name = "usu_dt_registro")
	private Date register;
	
	@OneToOne(targetEntity = Address.class, cascade = CascadeType.REMOVE, optional = true)
	@JoinColumn(name = "id_endereco", foreignKey = @ForeignKey(name = "fk_endereco"))
	private Address address;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Profile> profiles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.profiles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
