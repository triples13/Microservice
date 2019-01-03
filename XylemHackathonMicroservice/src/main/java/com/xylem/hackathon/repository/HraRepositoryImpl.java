package com.xylem.hackathon.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xylem.hackathon.model.Employee;


public class HraRepositoryImpl {

	@Autowired
	private HraRepository hraRepository;
	
	public List<Employee> getAllEmployee(){
		return hraRepository.findAll();
		
	}
}
