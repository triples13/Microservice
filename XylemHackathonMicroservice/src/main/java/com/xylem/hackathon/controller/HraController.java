package com.xylem.hackathon.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xylem.hackathon.model.Employee;
import com.xylem.hackathon.service.HraService;
@RequestMapping("/xylem")
@RestController
public class HraController {

	@Autowired
	HraService hraService;
	@RequestMapping(value="/getAllEmployees",method=RequestMethod.GET)
	public ResponseEntity<List<Employee>> getEmployees()
	{
	
		 return new ResponseEntity<>(hraService.getAllEmployee(),HttpStatus.OK);
	}
}
