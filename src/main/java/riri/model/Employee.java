package riri.model;

public class Employee {
    private String id;
    private String name;
    private String phone;
    private String role;     // admin / staff

    public Employee(String id, String name, String phone, String role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toFileString() {
        return id + ";" + name + ";" + phone + ";" + role;
    }
}
