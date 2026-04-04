package riri.service;

import riri.dao.CategoryDAO;
import riri.model.Category;

import java.util.*;

public class CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final Map<Integer, Category> categories;

    public CategoryService() {
        categories = categoryDAO.findAll();
    }

    public Map<Integer, Category> getAll() {
        return categories;
    }

    public Category findById(Integer id) {
        return categories.get(id);
    }

    public Category findByName(String name) {
        return categories.values()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void add(Category category) {
        category.setId(generateId());
        categories.put(category.getId(), category);
        save();
    }

    public void update(Category category) {
        if (!categories.containsKey(category.getId())) {
            throw new RuntimeException("Thể loại không tồn tại");
        }
        categories.put(category.getId(), category);
        save();
    }

    public void delete(Integer id) {
        if (!categories.containsKey(id)) {
            throw new RuntimeException("Thể loại không tồn tại");
        }
        categories.remove(id);
        save();
    }

    public void save() {
        categoryDAO.saveAll(categories);
    }

    private Integer generateId() {
        return categories.size() + 1;
    }
}