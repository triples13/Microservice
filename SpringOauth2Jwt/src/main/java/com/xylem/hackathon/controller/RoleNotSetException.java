package com.xylem.hackathon.controller;

public class RoleNotSetException extends RuntimeException {

	String message;
	
	RoleNotSetException(String message){
		this.message=message;
	}
	RoleNotSetException(){
		
	}
}
