package com.hspring.service;

import com.hspring.data.entity.Employee;
import com.hspring.data.EmployeeRepository;
import com.hspring.model.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by snagis on 8/2/15.
 */
@Service
public class EmployeeSearchService {

    EmployeeRepository repository;

    @Autowired
    public EmployeeSearchService(EmployeeRepository repository){
        this.repository = repository;
    }

    public void save(Employee employee){
        repository.save(employee);
    }

    public void update(Employee employee){
        Employee emp = findById(employee.getId());
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail());
        emp.setLocation(employee.getLocation());
        emp.setTitle(employee.getTitle());
        emp.setCellPhone(employee.getCellPhone());
        emp.setWorkPhone(employee.getWorkPhone());
        emp.setHomePhone(employee.getHomePhone());
        emp.setRole(employee.getRole());

        String newPasswd = employee.getPassword();
        if(newPasswd != null && !"".equals(newPasswd)){
            emp.setPassword(newPasswd);
        }
        save(emp);
    }

    public Employee findById(Long id){
        return repository.findById(id);
    }

    public List<Employee> search(SearchCriteria searchCriteria){
	String searchTerm = searchCriteria.getSearchTerm();
	return repository.findByFirstNameOrLastNameOrEmail(searchTerm, searchTerm, searchTerm);
    }

    public void delete(Long employeeId) {
        repository.delete(employeeId);
    }
}
