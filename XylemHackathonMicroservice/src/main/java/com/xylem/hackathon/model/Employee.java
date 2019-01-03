package com.xylem.hackathon.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee {

	@Id
	@GeneratedValue
	private int id;
	@Column(name="employeename")
	private String employeeName;
	@Column(name="employeeage")
	private int employeeAge;
	@Column(name="employeephoneno")
	private long employeePhoneNo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getEmployeeAge() {
		return employeeAge;
	}
	public void setEmployeeAge(int employeeAge) {
		this.employeeAge = employeeAge;
	}
	public long getEmployeePhoneNo() {
		return employeePhoneNo;
	}
	public void setEmployeePhoneNo(long employeePhoneNo) {
		this.employeePhoneNo = employeePhoneNo;
	}
	
	
}
