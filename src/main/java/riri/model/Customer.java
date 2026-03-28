package riri.model;

public class Customer extends BaseModel {
    public String name;
    public String phone;
    public String email;
    public int idType;
    public int totalOrders;


    public double totalPrice;

    public Customer(int id, String name, String phone, String email, int idType, int totalOrders, double totalPrice) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.idType = idType;
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

    public int getIdType() {
        return idType;
    }

    public void setIdType(int type) {
        this.idType = type;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public String toFileString() {
        return id+";"+name+";"+phone+";"+email+";"+ idType +";"+totalOrders+";"+totalPrice;
    }

}
