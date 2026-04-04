package riri.model;

public class Book extends BaseModel {
    private String name;
    private String author;
    private String category;
    private String publisher;
    private double price;
    private int quantity;
    private int idArea;
    private int idShelf;

    public Book(int id, String name, String author, String category, String publisher,
                double price, int quantity, int idArea, int idShelf) {
        super(id);
        this.name = name;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.quantity = quantity;
        this.idArea = idArea;
        this.idShelf = idShelf;
    }

    public String getName()      { return name; }
    public String getAuthor()    { return author; }
    public String getCategory()  { return category; }
    public String getPublisher() { return publisher; }
    public double getPrice()     { return price; }
    public int getQuantity()     { return quantity; }
    public int getIdArea()       { return idArea; }
    public int getIdShelf()      { return idShelf; }

    public void setName(String name)           { this.name = name; }
    public void setAuthor(String author)       { this.author = author; }
    public void setCategory(String category)   { this.category = category; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setPrice(double price)         { this.price = price; }
    public void setQuantity(int quantity)      { this.quantity = quantity; }
    public void setIdArea(int idArea)          { this.idArea = idArea; }
    public void setIdShelf(int idShelf)        { this.idShelf = idShelf; }

    @Override
    public String toFileString() {
        return id + ";" + name + ";" + author + ";" + category + ";" + publisher
                + ";" + price + ";" + quantity + ";" + idArea + ";" + idShelf;
    }
}