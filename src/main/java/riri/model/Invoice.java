package riri.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Invoice extends BaseModel {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int employeeId;
    private LocalDate date;
    private double totalAmount;

    public Invoice(int id, int employeeId, LocalDate date, double totalAmount) {
        super(id);
        this.employeeId = employeeId;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
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