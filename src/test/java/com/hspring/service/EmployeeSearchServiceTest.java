package com.hspring.service;
import static org.mockito.Mockito.*;

import com.hspring.data.entity.Employee;
import com.hspring.data.EmployeeRepository;
import com.hspring.model.SearchCriteria;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by snagis on 8/3/15.
 * There is only one class here that has modicum of custom logic. We write a test for that.
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeSearchServiceTest {

    static EmployeeRepository repository;
    static EmployeeSearchService searchService;

    @BeforeClass
    public static void setup(){
        repository = mock(EmployeeRepository.class);
        Employee uniqueEmployee = new Employee();
        uniqueEmployee.setId(1L);
        uniqueEmployee.setEmail("unique@gmail.com");
        uniqueEmployee.setFirstName("John");
        uniqueEmployee.setLastName("Smith"); // we ignore other fields; irrelavant for tests

        Employee secondEmployee = new Employee();
        secondEmployee.setId(2L);
        secondEmployee.setEmail("second@gmail.com");
        secondEmployee.setFirstName("John");
        secondEmployee.setLastName("Doe"); // we ignore other fields; irrelavant for tests

        Employee thirdEmployee = new Employee();
        thirdEmployee.setId(3L);
        thirdEmployee.setEmail("third@gmail.com");
        thirdEmployee.setFirstName("Jane");
        thirdEmployee.setLastName("Doe"); // we ignore other fields; irrelavant for tests

        when(repository.findByEmail(anyString())).thenReturn(uniqueEmployee);
        when(repository.findByFirstName(anyString())).thenReturn(new ArrayList() {{
            add(uniqueEmployee);add(secondEmployee);}});
        when(repository.findByLastName(anyString())).thenReturn(new ArrayList(){{add(secondEmployee);add(thirdEmployee);}});

        searchService = new EmployeeSearchService(repository);
    }

    @Test
    public void testSearchByUniqueField() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setEmail("unique@gmail.com");

        List<Employee> employees = searchService.search(searchCriteria);

        assertEquals(1, employees.size());
        assertEquals(new Long(1), employees.get(0).getId());
    }

    @Test
    public void testSearchFilterByEmailAndFirstName() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setFirstName("John");
        searchCriteria.setEmail("unique@gmail.com");

        List<Employee> employees = searchService.search(searchCriteria);

        assertEquals(1, employees.size());
        assertEquals(new Long(1), employees.get(0).getId());
    }

    @Test
    public void testSearchFilterByFirstName() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setFirstName("John");

        List<Employee> employees = searchService.search(searchCriteria);

        assertEquals(2, employees.size());
        assertEquals("John", employees.get(0).getFirstName());
        assertEquals("John", employees.get(1).getFirstName());
    }

    @Test
    public void testSearchFilterByFirstAndLastName() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setFirstName("John");
        searchCriteria.setLastName("Doe");

        List<Employee> employees = searchService.search(searchCriteria);

        assertEquals(1, employees.size());
        assertEquals(new Long(2), employees.get(0).getId());
    }
}