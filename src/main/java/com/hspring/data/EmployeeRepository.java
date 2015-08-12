package com.hspring.data;

import com.hspring.data.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by snagis on 8/2/15.
 * Don't need to implement this. Sprint magically implements and injects this bean as needed.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByFirstNameOrLastNameOrEmail(String firstName, String lastName, String email);

    List<Employee> findByLastName(String lastName);

    Employee findByEmail(String email);

    List<Employee> findByWorkPhone(String workPhone);

    Employee findById(Long id);
}
