package riri.model;

public class InvoiceDetail {
    private String invoiceId;
    private String bookId;
    private int quantity;
    private double price;

    public InvoiceDetail(String invoiceId, String bookId, int quantity, double price) {
        this.invoiceId = invoiceId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toFileString() {
        return invoiceId + ";" + bookId + ";" + quantity + ";" + price;
    }
}
