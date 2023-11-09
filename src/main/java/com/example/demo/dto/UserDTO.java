package com.example.demo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

	private int id;
	@Min(value = 0 , message = "{min.msg}")
	private int age;
	@NotBlank(message = "{not.blank}")
	private String name;
	private String avatarUrl;
	private String username;
	private String password;
	private String homeAddress;
	//many to one
	private DepartmentDTO department;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthdate;
	private MultipartFile file;

}
