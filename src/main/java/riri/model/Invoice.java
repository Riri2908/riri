package riri.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Invoice extends BaseModel {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int customerId;
    private int employeeId;
    private LocalDate date;
    private double totalAmount;

    private Customer customer;
    private List<InvoiceDetail> details = new ArrayList<>();;

    public Invoice(int id, int customerId, int employeeId, LocalDate date, double totalAmount) {
        super(id);
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<InvoiceDetail> getDetails() {
        return details;
    }

    public void setDetails(List<InvoiceDetail> details) {
        this.details = details;
    }

    public void addDetail(InvoiceDetail detail) {
        if (details == null) {
            details = new ArrayList<>();
        }

        detail.setInvoiceId(this.id);
        details.add(detail);
    }

    @Override
    public String toFileString() {
        return id + ";" + customerId + ";" + employeeId + ";" + date.format(FORMATTER) + ";" + totalAmount;
    }
}