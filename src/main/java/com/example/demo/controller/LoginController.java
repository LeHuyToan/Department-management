package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.demo.client.dto.LoginUserDTO;
import com.example.demo.client.service.WSLoginService;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	@Autowired 
	WSLoginService wsLoginService;
	
	@GetMapping("/login")
	public String login() {
		return "Login.html";
	}
	
	@PostMapping("/login")
	public String login(HttpSession session, @RequestParam("username") String username,
			@RequestParam("password") String password) throws IOException {

//		OkHttpClient client = new OkHttpClient().newBuilder().build();
//		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//		RequestBody body = RequestBody.create(mediaType, "username=" + username + "&password=" + password);
//		Request request = new Request.Builder().url("http://localhost:8080/login").method("POST", body)
//				.addHeader("Content-Type", "application/x-www-form-urlencoded").build();
//		Response response = client.newCall(request).execute();
//		
//		log.info(response.body().string());
		
		String token1 = wsLoginService.login(username, password);
		log.info(token1);
		
		ResponseEntity<String> response = remoteLogin (username , password);

		if (response.getStatusCode() == HttpStatus.OK) {
			// Success
//			session.setAttribute("username", username);
			String token = response.getBody();
			session.setAttribute("token", token);
			
			LoginUserDTO loginUserDTO = getMe(token);
			session.setAttribute("user", loginUserDTO);
			
			return "redirect:/user/list";
		} else {
//			resp.sendRedirect(req.getContextPath()+"/login");
			return "redirect:/login";
		}
	}
	
	private LoginUserDTO getMe(String token) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		LinkedMultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

		HttpEntity<Void> entity = new HttpEntity<>(headers);

		ResponseEntity<LoginUserDTO> response = restTemplate.exchange("http://localhost:8080/me", HttpMethod.GET, entity,
				LoginUserDTO.class);
		if(response.getStatusCode()==HttpStatus.OK)
			return response.getBody();
		else
			throw new NoResultException();
	}

	private ResponseEntity<String> remoteLogin(String username, String password) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		LinkedMultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("username", username);
		requestBody.add("password", password);

		HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> respone = restTemplate.exchange("http://localhost:8080/login", HttpMethod.POST, entity,
				String.class);
		return respone;
	}
}
