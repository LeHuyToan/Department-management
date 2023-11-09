package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionController {
//	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler({NoResultException.class})
	public String notFound(NoResultException e) {
//		e.printStackTrace();
//		logger.info("INFO" , e);
		log.info("INFO", e);
		return "no-data.html";
		
	}
}
