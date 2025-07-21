package com.muratyildirim.spring_security.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muratyildirim.spring_security.user.User;

public class UserDTO {
	
	//id donmeye biliriz
	//@JsonIgnore
	private Integer id;
	
	private String username;
	
	@JsonIgnore // pasword hashli olsada donmeyelim
	private String password;

	public UserDTO() {
	}

	public UserDTO(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
