package riri.service;

import riri.dao.EmployeeDAO;
import riri.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final List<Employee> employees;

    public EmployeeService() {
        employees = new ArrayList<>(employeeDAO.findAll());
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee findById(String id) {
        return employees.stream()
                .filter(e -> e.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public void add(Employee employee) {
        employees.add(employee);
        employeeDAO.saveAll(employees);
    }

    public void delete(String id) {
        employees.removeIf(e -> e.getId().equalsIgnoreCase(id));
        employeeDAO.saveAll(employees);
    }
}