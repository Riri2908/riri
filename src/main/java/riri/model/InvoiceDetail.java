package riri.model;

public class InvoiceDetail implements BaseModel{
    private Integer id;
    private Integer bookId;
    private int quantity;
    private double price;

    public InvoiceDetail(Integer id, Integer bookId, int quantity, double price) {
        this.id = id;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer invoiceId) {
        this.id = invoiceId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
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

    @Override
    public String toFileString() {
        return id + ";" + bookId + ";" + quantity + ";" + price;
    }
}
