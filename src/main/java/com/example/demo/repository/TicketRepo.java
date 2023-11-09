package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {

	@Query("Select u from  Ticket u where u.clientName like : x")
	Page<Ticket> searchByName(@Param("x") String s, Pageable pageable);

	@Query("Select u from Ticket u where u.createdAt >= :start and u.createdAt <= :end")
	Page<Ticket> searchByDate(@Param("start") Date start, @Param("end") Date end, Pageable pageable);
	
	@Query("Select u from Ticket u where u.createdAt >= :start")
	Page<Ticket> searchByDate(@Param("start") Date start, Pageable pageable);

	@Query("Select u from Ticket u Join u.department d  where d.id = :x")
	Page<Ticket> searchByDepartmentId(@Param("x") int dID, Pageable pageable);

	@Query("Select u from Ticket u Join u.department d  where d.name = :x")
	Page<Ticket> searchByDepartmentName(@Param("x") int dName, Pageable pageable);

	Page<Ticket> findByStatus(boolean status, Pageable pageable);

}
	