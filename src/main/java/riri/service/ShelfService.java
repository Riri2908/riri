package riri.service;

import riri.dao.ShelfDAO;
import riri.model.Shelf;

import java.util.*;

public class ShelfService {

    private final ShelfDAO shelfDAO = new ShelfDAO();
    private final Map<Integer, Shelf> shelves;

    public ShelfService() {
        shelves = shelfDAO.findAll();
    }

    public Map<Integer, Shelf> getAll() {
        return shelves;
    }

    public Shelf findById(Integer id) {
        return shelves.get(id);
    }

    public Shelf findByName(String name) {
        return shelves.values()
                .stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Shelf> findByAreaId(int areaId) {
        return shelves.values()
                .stream()
                .filter(s -> s.getAreaId() == areaId)
                .toList();
    }

    public void add(Shelf shelf) {
        shelf.setId(generateId());
        shelves.put(shelf.getId(), shelf);
        save();
    }

    public void update(Shelf shelf) {
        if (!shelves.containsKey(shelf.getId())) {
            throw new RuntimeException("Shelf không tồn tại");
        }
        shelves.put(shelf.getId(), shelf);
        save();
    }

    public void delete(Integer id) {
        shelves.remove(id);
        save();
    }

    public void save() {
        shelfDAO.saveAll(shelves);
    }

    private Integer generateId() {
        return shelves.size() + 1;
    }
}