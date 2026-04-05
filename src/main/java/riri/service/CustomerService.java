package riri.service;

import riri.dao.CustomerDAO;
import riri.model.Customer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final Map<Integer, Customer> customers;

    public CustomerService() {
        customers = customerDAO.findAll();
    }

    public Map<Integer, Customer> getAll() {
        return customers;
    }

    private Stream<Customer> activeCustomers() {
        return customers.values()
                .stream()
                .filter(c -> !c.isDeleted());
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
        Customer c = customers.get(id);
        if (c != null) {
            c.setDeleted(true);
            save();
        }
    }

    public void save() {
        customerDAO.saveAll(customers);
    }

    public int totalCustomers() {
        return (int) activeCustomers().count();
    }

    public int totalOrders() {
        return activeCustomers().mapToInt(Customer::getTotalOrders).sum();
    }

    public int totalOrdersWeek(LocalDate date) {

        LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return activeCustomers()
                .filter(c -> {
                    LocalDate orderDate = c.getRecentDate();
                    return orderDate != null
                            && !orderDate.isBefore(startOfWeek)
                            && !orderDate.isAfter(endOfWeek);
                })
                .mapToInt(Customer::getTotalOrders)
                .sum();
    }

    public double totalPrice() {
        return activeCustomers().mapToDouble(Customer::getTotalPrice).sum();
    }

    public double totalPriceWeek(LocalDate date) {

        LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return activeCustomers()
                .filter(c -> {
                    LocalDate orderDate = c.getRecentDate();
                    return orderDate != null
                            && !orderDate.isBefore(startOfWeek)
                            && !orderDate.isAfter(endOfWeek);
                })
                .mapToDouble(Customer::getTotalPrice)
                .sum();
    }

    public int parseIntSafe(String value){
        try{
            return Integer.parseInt(value);
        }catch(Exception e){
            return 0;
        }
    }

    public double parseDoubleSafe(String value){
        try{
            return Double.parseDouble(value);
        }catch(Exception e){
            return 0;
        }
    }

    private Integer generateId() {
        return customers.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }
}