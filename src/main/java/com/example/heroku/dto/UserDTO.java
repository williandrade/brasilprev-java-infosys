package com.example.heroku.dto;

import java.util.Date;

public class UserDTO {

	private Integer id;
	private String email;
	private String password;
	private String token;
	private Date tokenDuoDate;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the tokenDuoDate
	 */
	public Date getTokenDuoDate() {
		return tokenDuoDate;
	}

	/**
	 * @param tokenDuoDate the tokenDuoDate to set
	 */
	public void setTokenDuoDate(Date tokenDuoDate) {
		this.tokenDuoDate = tokenDuoDate;
	}

}
