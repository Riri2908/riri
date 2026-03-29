package riri.service;

import riri.dao.AreaDAO;
import riri.model.Area;

import java.util.*;

public class AreaService {

    private final AreaDAO areaDAO = new AreaDAO();
    private final Map<Integer, Area> areas;

    private final ShelfService shelfService = new ShelfService();

    public AreaService() {
        areas = areaDAO.findAll();
    }

    public Map<Integer, Area> getAll() {
        return areas;
    }

    public Area findById(Integer id) {
        return areas.get(id);
    }

    public Area findByName(String name) {
        return areas.values()
                .stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Area> getAreasHaveShelf() {
        return areas.values()
                .stream()
                .filter(a -> !shelfService.findByAreaId(a.getId()).isEmpty())
                .toList();
    }

    public void add(Area area) {
        area.setId(generateId());
        areas.put(area.getId(), area);
        save();
    }

    public void update(Area area) {
        if (!areas.containsKey(area.getId())) {
            throw new RuntimeException("Khu vực không tồn tại");
        }
        areas.put(area.getId(), area);
        save();
    }

    public void delete(Integer id) {

        if (!areas.containsKey(id)) {
            throw new RuntimeException("Khu vực không tồn tại");
        }

        if (!shelfService.findByAreaId(id).isEmpty()) {
            throw new RuntimeException("Không thể xóa! Khu vực đang chứa kệ");
        }

        areas.remove(id);
        save();
    }

    public void save() {
        areaDAO.saveAll(areas);
    }

    private Integer generateId() {
        return areas.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }
}