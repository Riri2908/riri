package riri.model;

public class Category extends BaseModel {
    private String name;

    public Category(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toFileString() {
        return id + ";" + name;
    }
}