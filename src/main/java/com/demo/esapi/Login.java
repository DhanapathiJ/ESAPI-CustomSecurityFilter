package com.demo.esapi;

import java.io.Serializable;

public class Login implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8405944325474534786L;

	private String email;
	
	private String password;
	
	public Login() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
