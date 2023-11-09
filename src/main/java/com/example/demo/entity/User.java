package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Table(name ="db_user")//map to table SQL
@Entity	//
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne //bắt buộc kiểu dữ liệu Entity
	//Many users to one department
//	@JoinColumn(name = "department_id");
	private Department department;
	
	private int age;
	private String name;
	//lưu tên file path
	private String avatarUrl;
	@Column(unique = true)
	private String username;
	private String password;
	private String homeAddress;
	@Temporal(TemporalType.DATE)
	private Date birthdate;

}
