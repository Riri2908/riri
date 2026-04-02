package riri.dao;

import riri.model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CustomerDAO extends BaseFileDAO {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CustomerDAO() {
        super("customers.txt");
    }

    public Map<Integer, Customer> findAll() {
        Map<Integer, Customer> customerMap = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");

                Customer customer = new Customer(
                        Integer.parseInt(d[0]), d[1], d[2], d[3],
                        Integer.parseInt(d[4]),Integer.parseInt(d[5]) ,
                        Double.parseDouble(d[6]), LocalDate.parse(d[7],FORMATTER),d[8]);

                customerMap.put(Integer.parseInt(d[0]), customer);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read customers", e);
        }

        return customerMap;
    }

    public void saveAll(Map<Integer, Customer> customers) {
        backup();

        List<String> lines = new ArrayList<>();

        for (Customer c : customers.values()) {
            lines.add(c.toFileString());
        }

        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save customers", e);
        }
    }

    public void add(Customer customer) {
        try {
            Files.write(
                    file,
                    List.of(customer.toFileString()),
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to add customer", e);
        }
    }
}