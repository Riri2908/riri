package riri.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer extends BaseModel {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String name;
    public String phone;
    public String email;
    public int idType;
    public LocalDate recentDate;
    public int totalOrders;
    public double totalPrice;
    public String note;
    public boolean deleted;

    public Customer(int id, String name, String phone, String email, int idType, int totalOrders, double totalPrice, LocalDate recentDate, String note) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.idType = idType;
        this.totalOrders = totalOrders;
        this.totalPrice = totalPrice;
        this.recentDate = recentDate;
        this.note = note;
        this.deleted = false;
    }

    public Customer(int id, String name, String phone, String email, int idType, int totalOrders, double totalPrice, LocalDate recentDate, String note,boolean deleted) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.idType = idType;
        this.totalOrders = totalOrders;
        this.totalPrice = totalPrice;
        this.recentDate = recentDate;
        this.note = note;
        this.deleted = deleted;
    }

    public LocalDate getRecentDate() {
        return recentDate;
    }

    public void setRecentDate(LocalDate recentDate) {
        this.recentDate = recentDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int type) {
        this.idType = type;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    @Override
    public String toFileString() {
        return id+";"+name+";"+phone+";"+email+";"+ idType+";"+totalOrders+";"+totalPrice+";"+ recentDate.format(FORMATTER)+";"+ note+";"+ deleted;
    }

}
