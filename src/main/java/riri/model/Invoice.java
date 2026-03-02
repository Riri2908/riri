package riri.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String id;
    private String employeeId;
    private LocalDate date;
    private double totalAmount;

    public Invoice(String id, String employeeId, LocalDate date, double totalAmount) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toFileString() {
        return id + ";" + employeeId + ";" + date.format(formatter) + ";" + totalAmount;
    }
}