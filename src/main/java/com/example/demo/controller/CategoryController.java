package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.client.dto.CategoryDTO;
import com.example.demo.client.service.WSLoginService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class CategoryController {
//	@Autowired
//	WSCategoryService categoryService;
	
	@Autowired
	WSLoginService loginService;

//	@GetMapping("/category/search")
//	public String search(Model model, @ModelAttribute("searchDTO") @Valid SearchDTO searchDTO,
//			BindingResult bindingResult) {
//		if (bindingResult.hasErrors()) {
//			return "User.html";// khi có lỗi xảy ra , return view
//		}
//
//		PageDTO<List<UserDTO>> pageUser = userService.searchName(searchDTO);
//		model.addAttribute("userList", pageUser.getData());
//		model.addAttribute("totalPage", pageUser.getTotalPages());
//		model.addAttribute("totalElements", pageUser.getTotalElements());
//		model.addAttribute("searchDTO", searchDTO);
//		return "User.html";
//	}

	@GetMapping("/category/new")
	public String newUser(Model model) {
		model.addAttribute("category" , new CategoryDTO());
		return "new-category.html";
	}

	@PostMapping("/category/new")
	public String newUser(@ModelAttribute("category") @Valid CategoryDTO categoryDTO, BindingResult bindingResult, HttpSession session)
			throws Exception {
		if (bindingResult.hasErrors()) {
			return "new-category.html";
		}
		String token = (String) session.getAttribute("token");
		
//		categoryService.createCategory(categoryDTO, token);
		loginService.addCategory(categoryDTO,"Bearer " + token);
		return "redirect:/category/search";
	}

}
