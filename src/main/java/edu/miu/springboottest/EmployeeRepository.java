package edu.miu.springboottest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Custom query to find employees by department
    @Query("SELECT e FROM Employee e WHERE e.department = :department")
    List<Employee> findByDepartment(@Param("department") String department);

    // Custom query to find employees with a salary greater than the specified amount
    @Query("SELECT e FROM Employee e WHERE e.salary > :minSalary")
    List<Employee> findBySalaryGreaterThan(@Param("minSalary") double minSalary);

    // Derived query method to find employees by name
    List<Employee> findByName(String name);
}

