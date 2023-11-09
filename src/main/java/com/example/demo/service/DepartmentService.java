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
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.User;
import com.example.demo.repository.DepartmentRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

public interface DepartmentService {
	void create(DepartmentDTO departmentDTO);

	void update(DepartmentDTO departmentDTO);

	void delete(int id);

	DepartmentDTO getById(int id);

	PageDTO<List<DepartmentDTO>> search(SearchDTO searchDTO);
}

@Service
class DepartmentImpl implements DepartmentService {
	@Autowired
	private DepartmentRepo departmentRepo;

	@Override
	@Transactional
	public void create(DepartmentDTO departmentDTO) {
		Department department = new ModelMapper().map(departmentDTO, Department.class);
		departmentRepo.save(department);

	}

	@Override
	@Transactional
	public void update(DepartmentDTO departmentDTO) {
		Department department = departmentRepo.findById(departmentDTO.getId()).orElse(null);
		if (department != null) {
			department.setName(departmentDTO.getName());
			departmentRepo.save(department);
		}

	}

	@Override
	@Transactional
	public void delete(int id) {
		departmentRepo.deleteById(id);
	}

	@Override
	public DepartmentDTO getById(int id) {
		Department department = departmentRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(department);
	}

	private DepartmentDTO convert(Department department) {
		return new ModelMapper().map(department, DepartmentDTO.class);
	}

	@Override
	public PageDTO<List<DepartmentDTO>> search(SearchDTO searchDTO) {
		Sort sortBy = Sort.by("name").ascending();
		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}

		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		
		if (searchDTO.getSize() == null) {
			searchDTO.setSize(5);
		}
		if(searchDTO.getKeyword() == null)
			searchDTO.setKeyword("");
		
		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<Department> page = departmentRepo.searchName("%" + searchDTO.getKeyword() + "%", pageRequest);

		PageDTO<List<DepartmentDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		// List<User> users = page.getContent();
		List<DepartmentDTO> departmentDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T : List<UserDTO>
		pageDTO.setData(departmentDTOs);

		return pageDTO;

	}

}