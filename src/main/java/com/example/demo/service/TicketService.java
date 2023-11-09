package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.SearchTicketDTO;
import com.example.demo.dto.TicketDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.Ticket;
import com.example.demo.repository.DepartmentRepo;
import com.example.demo.repository.TicketRepo;

import jakarta.persistence.NoResultException;

public interface TicketService {
	void create(TicketDTO ticketDTO);
	
	void update(TicketDTO ticketDTO);
	
	void delete(int id);
	
	TicketDTO getById(int id);
	
	PageDTO<List<TicketDTO>> search(SearchTicketDTO searchDTO);
}

@Service
class TicketServiceImpl implements  TicketService
{
	@Autowired
	private TicketRepo ticketRepo;
	
	@Override
	public void create(TicketDTO ticketDTO) {
		Ticket ticket = new ModelMapper().map(ticketDTO, Ticket.class);
		ticketRepo.save(ticket);
	}

	@Override
	public void update(TicketDTO ticketDTO) {
		Ticket ticket = ticketRepo.findById(ticketDTO.getId()).orElse(null);
		if (ticket != null) {
			ticket.setClientName(ticketDTO.getClientName());
			ticketRepo.save(ticket);
		}
	}

	@Override
	public void delete(int id) {
		ticketRepo.deleteById(id);
		
	}

	@Override
	public TicketDTO getById(int id) {
		Ticket ticket = ticketRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(ticket);
	}
	
	private TicketDTO convert(Ticket ticket) {
		return new ModelMapper().map(ticket, TicketDTO.class);
	}

	@Override
	public PageDTO<List<TicketDTO>> search(SearchTicketDTO searchDTO) {
		Sort sortBy = Sort.by("createdAt").ascending();
		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}

		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		
		if (searchDTO.getSize() == null) {
			searchDTO.setSize(5);
		}
		
		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<Ticket> page = ticketRepo.findAll(pageRequest);
		if(searchDTO.getStart() != null && searchDTO.getEnd() != null) {
			page = ticketRepo.searchByDate( searchDTO.getStart(),searchDTO.getEnd(),pageRequest);
		}
		if(searchDTO.getDepartmentID() != null){
			page = ticketRepo.searchByDepartmentId(searchDTO.getDepartmentID(), pageRequest);
		}
		
		if(StringUtils.hasText(searchDTO.getKeyword())) {
			page = ticketRepo.searchByName(searchDTO.getKeyword(), pageRequest);
		}

		PageDTO<List<TicketDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		// List<User> users = page.getContent();
		List<TicketDTO> ticketDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T : List<UserDTO>
		pageDTO.setData(ticketDTOs);

		return pageDTO;
	}
	
}