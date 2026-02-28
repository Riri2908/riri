package riri.model;

public class Book {

    private String id;
    private String name;
    private String author;
    private String category;
    private double price;
    private int quantity;

    public Book(String id, String name, String author, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toFileString() {
        return id + ";" + name + ";" + author + ";" + price + ";" + quantity;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getAuthor() { return author; }

    public double getPrice() { return price; }

    public String getCategory() {
        return category;
    }

    public int getQuantity() { return quantity; }


}
