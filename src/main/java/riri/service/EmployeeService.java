package riri.service;

import riri.dao.EmployeeDAO;
import riri.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EmployeeService {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final Map<Integer,Employee> employees;

    public EmployeeService() {
        employees = employeeDAO.findAll();
    }

    public Map<Integer,Employee> getAll() {
        return employees;
    }

    public Employee findById(Integer id) {
        return employees.get(id);
    }

    public void add(Employee employee) {
        employees.put(employee.getId(),employee);
        employeeDAO.saveAll(employees);
    }

    public void delete(Integer id) {
        employees.remove(id);
        employeeDAO.saveAll(employees);
    }
}