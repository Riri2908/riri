package riri.service;

import riri.dao.CustomerTypeDAO;
import riri.model.CustomerType;

import java.util.Map;

public class CustomerTypeService {

    private final CustomerTypeDAO dao = new CustomerTypeDAO();
    private final Map<Integer, CustomerType> customerTypes;

    public CustomerTypeService() {
        customerTypes = dao.findAll();
    }

    public Map<Integer, CustomerType> getAll() {
        return customerTypes;
    }

    public CustomerType findById(Integer id) {
        return customerTypes.get(id);
    }

    public CustomerType findByName(String name) {
        return customerTypes.values()
                .stream()
                .filter(t -> t.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void add(CustomerType type) {
        type.setId(generateId());
        customerTypes.put(type.getId(), type);
        save();
    }

    public void update(CustomerType type) {

        if (!customerTypes.containsKey(type.getId())) {
            throw new RuntimeException("CustomerType không tồn tại");
        }

        customerTypes.put(type.getId(), type);
        save();
    }

    public void delete(Integer id) {
        customerTypes.remove(id);
        save();
    }

    public void save() {
        dao.saveAll(customerTypes);
    }

    private Integer generateId() {
        return customerTypes.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }
}