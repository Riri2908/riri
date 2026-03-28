package riri.model;

public class InvoiceDetail extends BaseModel{

    private int bookId;
    private int invoiceId;
    private int quantity;
    private double price;

    public InvoiceDetail(int id, int invoiceId, int bookId, int quantity, double price) {
        super(id);
        this.invoiceId = invoiceId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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
        return id + ";" + invoiceId + ";" + bookId + ";" + quantity + ";" + price;
    }
}
