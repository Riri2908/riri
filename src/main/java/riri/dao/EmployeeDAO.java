package riri.dao;

import riri.model.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class EmployeeDAO extends BaseFileDAO {

    public EmployeeDAO() {
        super("employees.txt");
    }

    public Map<Integer,Employee> findAll() {
        Map<Integer,Employee> employeeMap = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");
                employeeMap.put(Integer.parseInt(d[0]),new Employee(Integer.parseInt(d[0]), d[1], d[2], d[3]));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read employees", e);
        }

        return employeeMap;
    }

    public void saveAll(Map<Integer,Employee>  employeeMap) {
        List<String> lines = new ArrayList<>();
        Collection<Employee> employees = employeeMap.values();

        for (Employee e : employees) {
            lines.add(e.toFileString());
        }

        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save employees", e);
        }
    }
}