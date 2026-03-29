package riri.service;

import riri.dao.EmployeeDAO;
import riri.model.Employee;

import java.util.Map;


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
        employee.setId(generateId());
        employees.put(employee.getId(),employee);
        save();
    }

    public void save(){
        employeeDAO.saveAll(employees);
    }

    public void delete(Integer id) {
        employees.remove(id);
        employeeDAO.saveAll(employees);
    }
    private Integer generateId() {
        return employees.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }
}