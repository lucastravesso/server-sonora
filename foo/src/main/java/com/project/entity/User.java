package com.project.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "usuarios")
public class User {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id_usuario")
	private Integer id;
	
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
	
	@Column(name = "usu_senha")
	private String password;
	
	@Column(name = "usu_login")
	private String login;

	@Email
	@Column(name = "usu_email")
	private String mail;
	
	
	//-------get e set
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getBorn() {
		return born;
	}

	public void setBorn(Date born) {
		this.born = born;
	}

	public Date getRegister() {
		return register;
	}

	public void setRegister(Date register) {
		this.register = register;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	
	
	
}
