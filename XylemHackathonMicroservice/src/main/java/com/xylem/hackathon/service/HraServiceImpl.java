package com.xylem.hackathon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xylem.hackathon.model.Employee;
import com.xylem.hackathon.repository.HraRepositoryImpl;

@Service
public class HraServiceImpl implements HraService{

	@Autowired
	HraRepositoryImpl hraRepo;
	@Override
	public List<Employee> getAllEmployee() {
		
		return hraRepo.getAllEmployee();
	}

}
