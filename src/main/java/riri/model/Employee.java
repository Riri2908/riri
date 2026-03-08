package riri.model;

public class Employee extends BaseModel {
    private String name;
    private String phone;
    private String role;

    public Employee(int id, String name, String phone, String role) {
        super(id);
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

    @Override
    public String toFileString() {
        return id + ";" + name + ";" + phone + ";" + role;
    }
}
