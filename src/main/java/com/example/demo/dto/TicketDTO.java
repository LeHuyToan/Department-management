package com.example.demo.dto;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class TicketDTO {
	private Integer id;
	
	private String clientName;
	private String clientPhone;
	private String content;
	private boolean status;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date processDate;
	private DepartmentDTO department;
	private Date createdAt; 
}
