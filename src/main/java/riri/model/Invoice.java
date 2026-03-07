package riri.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Invoice implements BaseModel {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Integer id;
    private Integer employeeId;
    private LocalDate date;
    private double totalAmount;

    public Invoice(Integer id, Integer employeeId, LocalDate date, double totalAmount) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
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

    @Override
    public String toFileString() {
        return id + ";" + employeeId + ";" + date.format(formatter) + ";" + totalAmount;
    }
}