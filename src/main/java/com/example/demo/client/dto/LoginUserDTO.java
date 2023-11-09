package com.example.demo.client.dto;

import java.util.List;

import lombok.Data;

@Data
public class LoginUserDTO {
	private int id;
	private String name;
	private String username;
	private List<Authority> authorities;
	
	@Data
	public static class Authority {
		private String authority;
	}
	
}
