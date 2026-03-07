package riri.model;

public class Book implements BaseModel{

    private Integer id;
    private String name;
    private String author;
    private String category;
    private double price;
    private int quantity;

    public Book(Integer id, String name, String author, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setId(Integer id) {
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

    @Override
    public String toFileString() {
        return id + ";" + name + ";" + author + ";" + category + ";" + price + ";" + quantity;
    }
    @Override
    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getAuthor() { return author; }

    public double getPrice() { return price; }

    public String getCategory() {
        return category;
    }

    public int getQuantity() { return quantity; }


}
