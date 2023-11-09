package com.example.demo.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.client.dto.CategoryDTO;
import com.example.demo.client.dto.ResponseDTO;

@FeignClient(value = "loginserice", url = "http://localhost:8080")
public interface WSLoginService {

	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password);
	
	@PostMapping("/admin/category/")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseDTO<CategoryDTO> addCategory(@RequestBody  CategoryDTO category , @RequestHeader("Authorization") String bearerToken);
}