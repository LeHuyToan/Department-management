package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;

import jakarta.transaction.Transactional;

//@Component
@Service // tạo bean : new Uservice , ql String Container
public class UserService {

	@Autowired
	UserRepo userRepo;

	@Transactional
	public void create(UserDTO userDTO) {
		// convert userDTO -> user
//		User user = new User();
//		user.setName(userDTO.getName());
//		user.setAge(userDTO.getAge());
//		user.setUsername(userDTO.getUsername());
//		user.setPassword(userDTO.getPassword());
//		user.setAvatarUrl(userDTO.getAvatarUrl());
//		user.setHomeAdderss(userDTO.getHomeAdderss());
		User user = new ModelMapper().map(userDTO, User.class);
		// save entity
		userRepo.save(user);
	}

	@Transactional
	public void delete(int id) {
		userRepo.deleteById(id);
	}

	@Transactional
	public void update(UserDTO userDTO) {

		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
		if (currentUser != null) {
			currentUser.setName(userDTO.getName());
			currentUser.setAge(userDTO.getAge());
			currentUser.setUsername(userDTO.getUsername());
			currentUser.setHomeAddress(userDTO.getHomeAddress());
			userRepo.save(currentUser);
		}
	}

	@Transactional
	public void updatePassword(UserDTO userDTO) {

		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
		if (currentUser != null) {
			currentUser.setPassword(userDTO.getPassword());
			userRepo.save(currentUser);
		}
	}

	public UserDTO getById(int id) {
		User user = userRepo.findById(id).orElse(null);
		if (user != null) {
			return convert(user);
		}
		return null;
	}

	private UserDTO convert(User user) {
//		UserDTO userDTO = new UserDTO();
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setAge(user.getAge());
//		userDTO.setUsername(user.getUsername());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAvatarUrl(user.getAvatarUrl());
//		userDTO.setHomeAdderss(user.getHomeAdderss());
//		return userDTO;
		return new ModelMapper().map(user,UserDTO.class);
	}

	public List<UserDTO> getAll() {
		List<User> userList = userRepo.findAll();

//		List<UserDTO> userDTOs = new ArrayList<>();
//		for(User user : userList) {
//			userDTOs.add(convert(user));
//		}
//		return userDTOs;
		//java8
		return userList.stream().map(u->convert(u)).collect(Collectors.toList());
	}

	public PageDTO<List<UserDTO>> searchName(SearchDTO searchDTO) {
		Sort sortBy = Sort.by("name").ascending().and(Sort.by("age").descending());

		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}

		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}

		if (searchDTO.getSize() == null) {
			searchDTO.setSize(2);
		}

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<User> page = userRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		
		PageDTO<List<UserDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		//List<User> users = page.getContent();
		List<UserDTO> userDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		//T : List<UserDTO>
		pageDTO.setData(userDTOs);
		
		return pageDTO;
	}
}
