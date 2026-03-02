package riri.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Transaction {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String id;
    private String bookId;
    private String employeeId;
    private int quantity;
    private String type;
    private LocalDate date;
    private String note;

    public Transaction(String id, String bookId, String employeeId, int quantity, String type, LocalDate date, String note) {
        this.id = id;
        this.bookId = bookId;
        this.employeeId = employeeId;
        this.quantity = quantity;
        this.type = type;
        this.date = date;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String toFileString() {
        return id + ";" + bookId + ";" + employeeId + ";" + quantity + ";" + type + ";" + date.format(formatter) + ";" + note;
    }
}