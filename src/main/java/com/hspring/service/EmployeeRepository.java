package com.hspring.service;

import com.hspring.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by snagis on 8/2/15.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findByFirstName(String firstName);
    List<Employee> findByLastName(String lastName);
    Employee findByEmail(String email);
    List<Employee>findByWorkPhone(String workPhone);
    Employee findById(Long id);
}
