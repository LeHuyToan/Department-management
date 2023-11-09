package com.example.demo.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.DepartmentService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;

	@GetMapping("/new")
	public String create(Model model) {
		model.addAttribute("department", new DepartmentDTO());
		return "new-department.html";
	}

	@PostMapping("/new")
	public String create(@ModelAttribute("department") @Valid DepartmentDTO departmentDTO,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "new-department.html";
		}

		departmentService.create(departmentDTO);
		return "redirect:/department/list";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		departmentService.delete(id);
		return "redirect:/department/list";
	}



	@GetMapping("/edit")
	public String edit(@RequestParam("id") int id, Model model) {
		DepartmentDTO departmentDTO = departmentService.getById(id);
		model.addAttribute("department", departmentDTO); // đẩy thông tin user qua view
		return "edit-department.html";
	}

	@PostMapping("/edit")
	public String edit(@ModelAttribute("department") @Valid DepartmentDTO departmentDTO, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return "edit-department.html";// khi có lỗi xảy ra , return view
		}
		departmentService.update(departmentDTO);

		return "redirect:/department/list";

	}
	@GetMapping("/list")
	public String list(Model model) {		
		return "redirect:/department/search";

	}
	@GetMapping("/search")
	public String search(Model model, @ModelAttribute("searchDTO") @Valid SearchDTO searchDTO,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "department.html";
		}

		PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(searchDTO);
		model.addAttribute("departmentList", pageDTO.getData());
		model.addAttribute("totalPage", pageDTO.getTotalPages());
		model.addAttribute("totalElements", pageDTO.getTotalElements());
		model.addAttribute("searchDTO", searchDTO);
		return "department.html";
	}
}
