package riri.model;

public abstract class BaseModel {
    public int id;
    public BaseModel(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public abstract String toFileString();
}
