package riri.dao;

import riri.model.Area;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class AreaDAO extends BaseFileDAO {

    public AreaDAO() {
        super("areas.txt");
    }

    public Map<Integer, Area> findAll() {
        Map<Integer, Area> areaMap = new LinkedHashMap<>();
        try {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                if (line.isBlank()) continue;
                String[] d = line.split(";");
                areaMap.put(
                        Integer.parseInt(d[0]),
                        new Area(Integer.parseInt(d[0]), d[1])
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read areas", e);
        }
        return areaMap;
    }

    public void saveAll(Map<Integer, Area> areas) {
        backup();
        List<String> lines = new ArrayList<>();
        for (Area a : areas.values()) {
            lines.add(a.toFileString());
        }
        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save areas", e);
        }
    }

    public void add(Area area) {
        try {
            Files.write(file, List.of(area.toFileString()), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to add area", e);
        }
    }
}
