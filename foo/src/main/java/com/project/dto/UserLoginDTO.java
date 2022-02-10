package com.project.dto;

import lombok.Builder;

@Builder
public class UserLoginDTO {
	
	private String password;

	private String mail;

	
	//----- get set
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	

}
