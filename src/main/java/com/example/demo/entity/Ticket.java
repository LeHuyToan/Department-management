package com.example.demo.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String clientName;
	private String clientPhone;
	
	private String content;
	private boolean status;
	private Date processDate;
	
	@ManyToOne
	private Department department;
	
	@CreatedDate
	@Column(updatable = false)
	private Date createdAt; 
}
