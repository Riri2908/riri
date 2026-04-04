package riri.dao;

import riri.model.Category;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class CategoryDAO extends BaseFileDAO {

    public CategoryDAO() {
        super("categories.txt");
    }

    public Map<Integer, Category> findAll() {
        Map<Integer, Category> categoryMap = new LinkedHashMap<>();
        try {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                if (line.isBlank()) continue;
                String[] d = line.split(";");
                categoryMap.put(
                        Integer.parseInt(d[0]),
                        new Category(Integer.parseInt(d[0]), d[1])
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read categories", e);
        }
        return categoryMap;
    }

    public void saveAll(Map<Integer, Category> categories) {
        backup();
        List<String> lines = new ArrayList<>();
        for (Category c : categories.values()) {
            lines.add(c.toFileString());
        }
        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save categories", e);
        }
    }

    public void add(Category category) {
        try {
            Files.write(file, List.of(category.toFileString()), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to add category", e);
        }
    }
}