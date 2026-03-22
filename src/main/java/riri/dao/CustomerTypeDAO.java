package riri.dao;

import riri.model.CustomerType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class CustomerTypeDAO extends BaseFileDAO {

    public CustomerTypeDAO() {
        super("customer_types.txt");
    }

    public Map<Integer, CustomerType> findAll() {
        Map<Integer, CustomerType> map = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");

                map.put(Integer.parseInt(d[0]), new CustomerType(Integer.parseInt(d[0]), d[1], Float.parseFloat(d[2])));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read customer types", e);
        }

        return map;
    }

    public void saveAll(Map<Integer, CustomerType> types) {
        backup();

        List<String> lines = new ArrayList<>();

        for (CustomerType t : types.values()) {
            lines.add(t.toFileString());
        }

        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save customer types", e);
        }
    }

    public void add(CustomerType type) {
        try {
            Files.write(
                    file,
                    List.of(type.toFileString()),
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to add customer type", e);
        }
    }
}