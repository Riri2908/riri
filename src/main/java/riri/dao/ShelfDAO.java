package riri.dao;

import riri.model.Shelf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ShelfDAO extends BaseFileDAO {

    public ShelfDAO() {
        super("shelves.txt");
    }

    public Map<Integer, Shelf> findAll() {
        Map<Integer, Shelf> shelfMap = new LinkedHashMap<>();
        try {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                if (line.isBlank()) continue;
                String[] d = line.split(";");
                shelfMap.put(Integer.parseInt(d[0]), new Shelf(Integer.parseInt(d[0]), d[1], Integer.parseInt(d[2]))
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read shelves", e);
        }
        return shelfMap;
    }

    public void saveAll(Map<Integer, Shelf> shelves) {
        backup();
        List<String> lines = new ArrayList<>();
        for (Shelf s : shelves.values()) {
            lines.add(s.toFileString());
        }
        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save shelves", e);
        }
    }

    public void add(Shelf shelf) {
        try {
            Files.write(file, List.of(shelf.toFileString()), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to add shelf", e);
        }
    }
}
