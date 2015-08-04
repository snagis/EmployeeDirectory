package com.hspring.service;

import com.hspring.entity.Employee;
import com.hspring.model.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by snagis on 8/2/15.
 */
@Component
public class EmployeeSearchService {

    @Autowired
    EmployeeRepository repository;

    public void save(Employee employee){
        repository.save(employee);
    }

    public Employee findById(Long id){
        return repository.findById(id);
    }

    public List<Employee> search(SearchCriteria searchCriteria){
        List<Employee> employees = new ArrayList<Employee>();
        if(searchCriteria.searchByEmail()) {
            Employee employee = repository.findByEmail(searchCriteria.getEmail());
            if(employee != null){
                employees.add(employee);
            }
            return filterByAdditionalCriteria(employees, searchCriteria);
        }

        else if(searchCriteria.searchByLastName()){
            List<Employee> employeeSearchResults = repository.findByLastName(searchCriteria.getLastName());
            if(employeeSearchResults != null){
                employees.addAll(employeeSearchResults);
            }
            return filterByAdditionalCriteria(employees, searchCriteria);

        }

        if(searchCriteria.searchByFirstName()) {
            List<Employee> employeeSearchResults = repository.findByFirstName(searchCriteria.getFirstName());
            if(employeeSearchResults != null){
                employees.addAll(employeeSearchResults);
            }
            return filterByAdditionalCriteria(employees,searchCriteria);
        }

        return employees;
    }

    private List<Employee> filterByAdditionalCriteria(List<Employee> employees, SearchCriteria searchCriteria) {
        if(!searchCriteria.isMultipleSearchCriteria()){
            return employees;
        }

        for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();) {
            Employee employee = iterator.next();
            if(searchCriteria.searchByLastName()){
                if(!searchCriteria.getLastName().equals(employee.getLastName())){
                   iterator.remove();
                }
            }
            else if(searchCriteria.searchByFirstName()){
                if(!searchCriteria.getFirstName().equals(employee.getFirstName())){
                    iterator.remove();
                }
            }
            else if(searchCriteria.searchByEmail()){
                if(!searchCriteria.getEmail().equals(employee.getEmail())){
                    iterator.remove();
                }
            }
        }
        return employees;
    }
}
