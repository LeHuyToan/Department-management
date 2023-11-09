package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@CreatedDate// auto gen new date
	@Column(updatable = false)
	private Date createdAt;//java.util
	
	@LastModifiedDate
	private Date updateAt;
	
	//không bắt buộc
	//one department to many users
	//mappedBy là tên thuộc tính ManyToOne bên entity user
	@OneToMany(mappedBy = "department")
	private List<User> users;
}
