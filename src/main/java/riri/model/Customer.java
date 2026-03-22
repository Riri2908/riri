package riri.model;

public class Customer extends BaseModel {
    public String name;
    public String phone;
    public String email;
    public String type;
    public int totalOrders;
    public double totalPrice;

    public Customer(int id, String name, String phone, String email, String type, int totalOrders, double totalPrice) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.type = type;
        this.totalOrders = totalOrders;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toFileString() {
        return id+" "+name+";"+phone+";"+email+";"+type+";"+totalOrders+";"+totalPrice;
    }

}
