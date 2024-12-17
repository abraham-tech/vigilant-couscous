package edu.miu.springboottest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployeesByDepartment(String department) {
        
        return employeeRepository.findByDepartment(department);
    }

    public List<Employee> getEmployeesBySalaryGreaterThan(double minSalary) {
        return employeeRepository.findBySalaryGreaterThan(minSalary);
    }

    public List<Employee> getEmployeesByName(String name) {
        return employeeRepository.findByName(name);
    }
}

