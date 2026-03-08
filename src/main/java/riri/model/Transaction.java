package riri.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction extends BaseModel{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int bookId;
    private int employeeId;
    private int quantity;
    private String type;
    private LocalDate date;
    private String note;

    public Transaction(int id, int bookId, int employeeId, int quantity, String type, LocalDate date, String note) {
        super(id);
        this.bookId = bookId;
        this.employeeId = employeeId;
        this.quantity = quantity;
        this.type = type;
        this.date = date;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
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

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    @Override
    public String toFileString() {
        return id + ";" + bookId + ";" + employeeId + ";" + quantity + ";" + type + ";" + date.format(formatter) + ";" + note;
    }
}