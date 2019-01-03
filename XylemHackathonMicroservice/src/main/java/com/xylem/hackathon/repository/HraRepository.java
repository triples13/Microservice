package com.xylem.hackathon.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.xylem.hackathon.model.Employee;

public interface HraRepository extends JpaRepository<Employee, Integer> {



}
