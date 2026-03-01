package riri.dao;

import riri.model.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends BaseFileDAO {

    public EmployeeDAO() {
        super("employees.txt");
    }

    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");
                list.add(new Employee(d[0], d[1], d[2], d[3]));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read employees", e);
        }

        return list;
    }

    public void saveAll(List<Employee> employees) {
        List<String> lines = new ArrayList<>();

        for (Employee e : employees) {
            lines.add(e.toFileString());
        }

        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save employees", e);
        }
    }

    public void add(Employee employee) {
        List<Employee> employees = findAll();
        employees.add(employee);
        saveAll(employees);
    }

    public Employee findById(String id) {
        return findAll().stream()
                .filter(e -> e.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}