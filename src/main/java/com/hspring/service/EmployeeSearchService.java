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
        //if user selects multiple fields to search on, we retrieve all
        // the records with on a single field and filter out the
        // records locally. Keeps db queries simple.

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
                    continue;
                }
            }

            if(searchCriteria.searchByFirstName()){
                if(!searchCriteria.getFirstName().equals(employee.getFirstName())){
                    iterator.remove();
                    continue;
                }
            }

            if(searchCriteria.searchByEmail()){
                if(!searchCriteria.getEmail().equals(employee.getEmail())){
                    iterator.remove();
                    continue;
                }
            }
        }
        return employees;
    }

    public void delete(Long employeeId) {
        repository.delete(employeeId);
    }
}
