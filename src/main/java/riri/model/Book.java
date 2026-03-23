package riri.model;

public class Book extends BaseModel {

    private String name;
    private String author;
    private String category;
    private String publisher;
    private double price;
    private int quantity;
    private String area;
    private String shelf;

    public Book(int id, String name, String author, String category, String publisher, double price, int quantity, String area, String shelf) {
        super(id);
        this.name = name;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.quantity = quantity;
        this.area = area;
        this.shelf = shelf;
    }

    public void setName(String name) { this.name = name; }
    public void setAuthor(String author) { this.author = author; }
    public void setPrice(int price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setArea(String area) { this.area = area; }
    public void setShelf(String shelf) { this.shelf = shelf; }

    public String getName() { return name; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public String getPublisher() { return publisher; }
    public String getArea() { return area; }
    public String getShelf() { return shelf; }

    @Override
    public String toFileString() {
        return id + ";" + name + ";" + author + ";" + category + ";" + publisher + ";" + price + ";" + quantity + ";" + area + ";" + shelf;
    }
}