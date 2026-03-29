package riri.service;

import riri.dao.CustomerDAO;
import riri.model.Customer;

import java.util.*;

public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final Map<Integer, Customer> customers;

    public CustomerService() {
        customers = customerDAO.findAll();
    }

    public Map<Integer, Customer> getAll() {
        return customers;
    }

    public Customer findById(Integer id) {
        return customers.get(id);
    }

    public Customer findByPhone(String phone) {
        return customers.values()
                .stream()
                .filter(c -> c.getPhone().equals(phone))
                .findFirst()
                .orElse(null);
    }

    public Customer findByName(String name) {
        return customers.values()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public Customer add(Customer customer) {
        customer.setId(generateId());
        customers.put(customer.getId(), customer);
        save();
        return customer;
    }

    public void update(Customer customer) {

        if (!customers.containsKey(customer.getId())) {
            throw new RuntimeException("Customer không tồn tại");
        }

        customers.put(customer.getId(), customer);
        save();
    }

    public void delete(Integer id) {
        customers.remove(id);
        save();
    }

    public void save() {
        customerDAO.saveAll(customers);
    }

    public int totalCustomers() {
        return customers.size();
    }

    public int totalOrders() {
        return customers.values().stream().mapToInt(Customer::getTotalOrders).sum();
    }

    public double totalPrice(){
        return customers.values().stream().mapToDouble(Customer::getTotalPrice).sum();
    }

    private Integer generateId() {
        return customers.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }
}