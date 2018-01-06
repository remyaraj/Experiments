package com.example.demo.employee;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;
	
	public String name;
	
	public String address;
	
	public String state;
	
	public String department;
	
	
}
