package edu.miu.springboottest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        // Initialize test data
        employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John Doe");
        employee1.setDepartment("HR");
        employee1.setSalary(50000);

        employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane Smith");
        employee2.setDepartment("IT");
        employee2.setSalary(60000);
    }

    @Test
    void testGetEmployeesByDepartment() {
        // Mock the repository method
        when(employeeRepository.findByDepartment("HR")).thenReturn(Arrays.asList(employee1));

        // Call the service method
        List<Employee> result = employeeService.getEmployeesByDepartment("HR");

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());

        // Verify repository interaction
        verify(employeeRepository, times(1)).findByDepartment("HR");
    }

    @Test
    void testGetEmployeesBySalaryGreaterThan() {
        // Mock the repository method
        when(employeeRepository.findBySalaryGreaterThan(55000)).thenReturn(Arrays.asList(employee2));

        // Call the service method
        List<Employee> result = employeeService.getEmployeesBySalaryGreaterThan(55000);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Jane Smith", result.get(0).getName());

        // Verify repository interaction
        verify(employeeRepository, times(1)).findBySalaryGreaterThan(55000);
    }

    @Test
    void testGetEmployeesByName() {
        // Mock the repository method
        when(employeeRepository.findByName("John Doe")).thenReturn(Arrays.asList(employee1));

        // Call the service method
        List<Employee> result = employeeService.getEmployeesByName("John Doe");

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());

        // Verify repository interaction
        verify(employeeRepository, times(1)).findByName("John Doe");
    }
}

