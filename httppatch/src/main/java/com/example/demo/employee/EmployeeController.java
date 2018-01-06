package com.example.demo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/emp")
public class EmployeeController {
	
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
	public Object saveEmployee(@RequestBody Employee employe) {
		
		try {
			Employee em = empRepo.save(employe);
			return em;
		} catch (Exception e) {
			System.out.println("error : " + e.getMessage());
			return e;
		}
		
	}
	

	
	@RequestMapping(value="/{empId}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
	public Object updateEmployee(@PathVariable long empId, @RequestBody Employee employe) {
		
		try {
			employe.id = empId;
			Employee em = empRepo.save(employe);
			return em;
		} catch (Exception e) {
			System.out.println("error : " + e.getMessage());
			return e;
		}
		
	}

	@RequestMapping(value="/{empId}", method = RequestMethod.PATCH, produces = "application/json")
    @ResponseBody
	public Object patchUpdateEmployee(@PathVariable long empId, @RequestBody Employee employe) {
		
		try {
			Employee oldEm = empRepo.findOne(empId);
			
			if(employe.name != null) {
				oldEm.name = employe.name;
			}

			if(employe.address != null) {
				oldEm.address = employe.address;
			}
			
			if(employe.state != null) {
				oldEm.state = employe.state;
			}
			
			if(employe.department != null) {
				oldEm.department = employe.department;
			}
			
			Employee em = empRepo.save( oldEm);
			return em;
		} catch (Exception e) {
			System.out.println("error : " + e.getMessage());
			return e;
		}
		
	}
	
	@Autowired
	EmployeeRepository empRepo;

}
