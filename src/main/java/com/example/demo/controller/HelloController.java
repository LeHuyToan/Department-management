package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HelloController {
	@GetMapping("/hello")
	public String hi(HttpSession httpSession) {
		//map url vao 1 ham , tra ve ten file view
		return "Hi.html";
	}
}
